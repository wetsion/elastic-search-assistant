package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

/**
 * 前缀查询
 * <br>
 * @author wetsion
 */
@Deprecated
public class Prefix implements QueryCondition {
    public String field;
    public Comparable value;

    /**
     * 前缀查询，不推荐用
     * @param field 字段名
     * @param value 查询值
     */
    public Prefix(String field, Comparable value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public String cond() {
        return "prefix";
    }

    @Override
    public Object value() {
        return ImmutableMap.of(field, value);
    }
}