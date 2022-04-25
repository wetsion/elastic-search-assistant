package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

/**
 * 使用方法：
 * "exists": {"field": "user"}，
 * es5取消了missing，替代方法：
 * "bool": {"must_not": {"exists": {"field": "user"}}}
 *
 * @author wetsion
 */
public class Exists implements QueryCondition {

    public String field;

    /**
     * 指定字段必须存在
     * @param field 字段名
     */
    public Exists(String field) {
        super();
        this.field = field;
    }

    @Override
    public String cond() {
        return "exists";
    }

    @Override
    public Object value() {
        return ImmutableMap.of("field", field);
    }

}
