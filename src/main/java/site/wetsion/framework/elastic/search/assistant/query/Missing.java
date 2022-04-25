package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

/**
 * es 5x版本已经取消了missing查询，请查看{@link Exists}
 *
 * @author wetsion
 */
@Deprecated
public class Missing implements QueryCondition {

    public String field;

    /**
     * 构造器，指定不存在的字段
     * @param field 字段名
     */
    public Missing(String field) {
        super();
        this.field = field;
    }

    @Override
    public String cond() {
        return "missing";
    }

    @Override
    public Object value() {
        return ImmutableMap.of("field", field);
    }

}
