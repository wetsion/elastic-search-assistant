package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.Boostable;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.Map;

/**
 * 范围查询，costly查询，重复两次即会进入缓存
 * <br>
 * @author wetsion
 */
public class Range implements QueryCondition, Boostable<Range> {

    private String field;
    private Comparable from;
    private Comparable to;
    private boolean includeUpper;
    private boolean includeLower;
    private double boost = Double.NEGATIVE_INFINITY;

    /**
     * 构造器
     * @param field 字段名
     */
    public Range(String field) {
        this.field = field;
    }

    /**
     * 设置字段名
     * @param field 字段名
     * @return 流式调用
     */
    public Range field(String field) {
        this.field = field;
        return this;
    }

    /**
     * 设置大于条件
     * @param value 大于设定值
     * @return self
     */
    public Range gt(Comparable value) {
        this.from = value;
        this.includeLower = false;
        return this;
    }

    /**
     * 设置大于等于条件
     * @param value 大于等于设定值
     * @return self
     */
    public Range gte(Comparable value) {
        this.from = value;
        this.includeLower = true;
        return this;
    }

    /**
     * 设置小于条件
     * @param value 小于设定值
     * @return self
     */
    public Range lt(Comparable value) {
        this.to = value;
        this.includeUpper = false;
        return this;
    }

    /**
     * 设置小于等于条件
     * @param value 小于等于设定值
     * @return self
     */
    public Range lte(Comparable value) {
        this.to = value;
        this.includeUpper = true;
        return this;
    }

    /**
     * 控制条件的boost提权
     * @param boost 额度
     * @return self
     */
    @Override
    public Range boost(double boost) {
        if (boost > 0) {
            this.boost = boost;
        }
        return this;
    }

    @Override
    public String cond() {
        return "range";
    }

    @Override
    public Object value() {
        Map<String, Object> inner = Maps.newHashMapWithExpectedSize(4);
        if (this.from != null) {
            if (includeLower) {
                inner.put("gte", from);
            } else {
                inner.put("gt", from);
            }
        }
        if (this.to != null) {
            if (includeUpper) {
                inner.put("lte", to);
            } else {
                inner.put("lt", to);
            }
        }
        if (Double.NEGATIVE_INFINITY != this.boost) {
            inner.put("boost", this.boost);
        }
        return ImmutableMap.of(field, inner);
    }

}
