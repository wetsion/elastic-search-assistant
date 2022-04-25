package site.wetsion.framework.elastic.search.assistant.agg;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * 统计和
 * @author wetsion
 */
public class Sum extends BaseAggs<Sum> {

    private String field;
    private Integer missing;
    private String script;

    /**
     * 设定统计查询返回的结果名称
     * @param aggName 统计结果名称
     */
    public Sum(String aggName) {
        super(aggName);
    }

    /**
     * 指定需要统计的字段
     * @param field 字段名
     * @return self
     */
    public Sum field(String field) {
        this.field = field;
        return this;
    }

    /**
     * 指定统计字段中不存在值的记录返回的默认值，比如sum(A), 文档1不存在A字段, 设置missing=10, 文档1返回10作为本条记录的统计值
     * @param missing 默认值
     * @return self
     */
    public Sum missing(int missing) {
        this.missing = missing;
        return this;
    }

    /**
     * 聚合脚本，比如可执行多个字段加和等
     * @param script 脚本
     * @return self
     */
    public Sum script(String script) {
        this.script = script;
        return this;
    }

    @Override
    public String cond() {
        return "sum";
    }

    @Override
    public Object value() {
        Map<String, Object> value;
        if (null != script) {
            value = ImmutableMap.of("script", this.script);
        } else {
            value = ImmutableMap.of("field", this.field);
            if (null != missing) {
                value.put("missing", missing);
            }
        }
        return toBodyWithNested(value);
    }
}
