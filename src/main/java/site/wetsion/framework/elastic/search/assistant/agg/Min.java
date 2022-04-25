package site.wetsion.framework.elastic.search.assistant.agg;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * 统计指定字段中的最小值
 *
 * @author wetsion
 */
public class Min extends BaseAggs<Min> {

    private String field;
    private Integer missing = null;

    /**
     * 指定统计结果的名称
     * @param name 结果名称
     */
    public Min(String name) {
        super(name);
    }

    /**
     * 指定需要统计的字段
     * @param field 字段名
     * @return self
     */
    public Min field(String field) {
        this.field = field;
        return this;
    }

    /**
     * 指定字段中不存在值情况的默认值
     * @param missing 默认值
     * @return self
     */
    public Min missing(int missing) {
        this.missing = missing;
        return this;
    }

    @Override
    public String cond() {
        return "min";
    }

    @Override
    public Object value() {
        Map<String, Object> value = ImmutableMap.of("field", this.field);
        if (null != missing) {
            value.put("missing", this.missing);
        }
        return toBodyWithNested(value);
    }
}
