package site.wetsion.framework.elastic.search.assistant.agg;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-24 18:17
 */
public class BucketScript extends BaseAggs<BucketScript> {

    private Map<String, String> path = new HashMap<>();

    private String script;

    public BucketScript(String name) {
        super(name);
    }

    /**
     * @param param 参数
     * @param source 来源
     * @return bucketScript
     */
    public BucketScript bucketsPath(String param, String source) {
        this.path.put(param, source);
        return this;
    }

    /**
     * 设置计算脚本
     * @param script painless脚本
     * @return bucketScript
     */
    public BucketScript script(String script) {
        this.script = script;
        return this;
    }

    @Override
    public String cond() {
        return "bucket_script";
    }

    @Override
    public Object value() {
        Map<String, Object> value = ImmutableMap.of("buckets_path", this.path, "script", this.script);
        return toBodyWithNested(value);
    }
}
