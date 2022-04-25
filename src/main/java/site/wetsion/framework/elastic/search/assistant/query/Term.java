package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

/**
 * @author wetsion
 * @date 2022-04-25 11:06
 */
public class Term implements QueryCondition {

    public String field;
    public Comparable value;

    /**
     * 构造器，设定精确匹配条件
     * @param field 字段名
     * @param value 匹配值
     */
    public Term(String field, Comparable value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public String cond() {
        return "term";
    }

    @Override
    public Object value() {
        return ImmutableMap.of(field, value);
    }

}
