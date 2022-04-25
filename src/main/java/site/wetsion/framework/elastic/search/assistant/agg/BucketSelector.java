package site.wetsion.framework.elastic.search.assistant.agg;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-24 18:28
 */
public class BucketSelector extends BaseAggs<BucketSelector> {
    private Map<String, String> path = new HashMap<>();

    private String script;

    public BucketSelector(String name) {
        super(name);
    }

    /**
     * @param param 参数名
     * @param source 来源
     * @return BucketSelector
     */
    public BucketSelector bucketsPath(String param, String source) {
        this.path.put(param, source);
        return this;
    }

    /**
     * 设置计算脚本
     * @param script painless脚本
     * @return BucketSelector
     */
    public BucketSelector script(String script) {
        this.script = script;
        return this;
    }

    @Override
    public String cond() {
        return "bucket_selector";
    }

    @Override
    public Object value() {
        Map<String, Object> value = ImmutableMap.of("buckets_path", this.path, "script", this.script);
        return toBodyWithNested(value);
    }
}
