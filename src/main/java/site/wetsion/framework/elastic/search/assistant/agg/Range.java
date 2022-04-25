package site.wetsion.framework.elastic.search.assistant.agg;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.exception.SearchException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 范围统计
 *
 * @author wetsion
 */
public class Range extends BaseAggs<Range> {

    private String field;
    private List<Map<String, Object>> ranges = new LinkedList<>();

    /**
     * 指定统计结果的名称
     * @param name 结果名称
     */
    public Range(String name) {
        super(name);
    }

    /**
     * 指定需要统计的字段
     * @param field 字段名
     * @return self
     */
    public Range field(String field) {
        this.field = field;
        return this;
    }

    /**
     * 添加range子条件，可以添加多个
     * @param from 区间起始 inclusive
     * @param to 区间结束 exclusive
     * @return 当前操作的agg
     */
    public Range interval(Comparable from, Comparable to) {
        if (null != from && null != to) {
            if (from.compareTo(to) >= 0) {
                throw new SearchException("from should not be greater than to");
            }
        }

        addRange(from, to);
        return this;
    }

    /**
     * 设置前闭后开区间, ［from, +INFINITE）
     * @param from 区间起始值，包含
     * @return self
     */
    public Range from(Comparable from) {
        addRange(from, null);
        return this;
    }

    /**
     * 设置前开后开区间, (-INFINITE, to)
     * @param to 区间结束值，不包含
     * @return self
     */
    public Range to(Comparable to) {
        addRange(null, to);
        return this;
    }

    @Override
    public String cond() {
        return "range";
    }

    @Override
    public Object value() {
        Map<String, Object> value = ImmutableMap.of("field", this.field);
        if (ranges.isEmpty()) {
            throw new SearchException("range aggregation must contains 1 range sub condition at least");
        }
        value.put("ranges", this.ranges);
        return toBodyWithNested(value);
    }

    /**
     * 添加一个区间条件，前闭后开区间
     * @param from 起始条件，包含
     * @param to 结束条件，不包含
     */
    private void addRange(Comparable from, Comparable to) {
        if (null == from && null == to) {
            throw new SearchException("from & to should not be null both");
        }
        Map<String, Object> range = Maps.newHashMap();
        if (null != to) {
            range.put("to", to);
        }
        if (null != from) {
            range.put("from", from);
        }
        ranges.add(range);
    }

}
