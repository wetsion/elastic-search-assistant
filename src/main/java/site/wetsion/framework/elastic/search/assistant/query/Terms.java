package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 相当于sql的where bar in (1, 2, 3)，也是个costly查询，重复两次即会进入缓存
 * <br>
 * @author wetsion
 */
public class Terms<C extends Comparable> implements QueryCondition {

    private String field;
    private List<C> values;

    /**
     * 构造器，指定字段名
     * @param field 字段名
     */
    public Terms(String field) {
        this(field, new LinkedList<>());
    }

    /**
     * 构造器，同时指定字段名和值
     * @param field 字段名
     * @param values 多值
     */
    public Terms(String field, List<C> values) {
        this.field = field;
        this.values = values;
    }

    /**
     * 构造器，同时指定字段名和值
     * @param field 字段名
     * @param values 可变多值
     */
    public Terms(String field, C... values) {
        this.field = field;
        this.values = Arrays.asList(values);
    }

    /**
     * 添加一个多值条件，不会覆盖
     * @param value 设定值
     * @return self
     */
    public Terms value(C value) {
        this.values.add(value);
        return this;
    }

    /**
     * 添加多个多值条件，不会覆盖
     * @param values 设定的多个值
     * @return self
     */
    public Terms values(C... values) {
        this.values.addAll(Arrays.asList(values));
        return this;
    }

    @Override
    public String cond() {
        return "terms";
    }

    @Override
    public Object value() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put(field, values);
        return params;
    }

}
