package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 非线程安全,注意使用，
 * 用于拼接多个查询条件："bool": {"must": [{"exists": {"field": "user"}}, {"term":{"name":"foo"}}]},
 * filter context不仅是filter和must_not子句中的内容，也包括不需要score的query。。
 * <br>
 * @author wetsion
 */
public class Bool implements QueryCondition {

    private List<Object> must;
    private List<Object> should;
    private List<Object> mustNot;
    private List<Object> filter;

    private final static String MINIMUM_SHOULD_MATCH = "minimum_should_match";
    private int minimumShouldMatch;

    public Bool() {
        this.must = new LinkedList<>();
        this.should = new LinkedList<>();
        this.mustNot = new LinkedList<>();
        this.filter = new LinkedList<>();
    }

    /**
     * 添加一个must查询条件，不会覆盖
     * <p>
     *     相当于sql中的and
     * </p>
     * @param cond 查询条件
     * @return self
     */
    public Bool must(QueryCondition cond) {
        Map<String, Object> query = ImmutableMap.of(cond.cond(), cond.value());
        this.must.add(query);
        return this;
    }

    /**
     * 添加一个should查询条件，不会覆盖
     * <p>
     * 可以理解为or
     * </p>
     * <p>
     * 如果嵌套在query子句中，最好加上{@link #minimumShouldMatch(int)}条件，指定should条件中至少要命中x个，默认至少命中0个
     * </p>
     * <p>
     * 如果在filter子句中，不需设置{@link #minimumShouldMatch(int)}，默认至少命中1个
     * </p>
     * @param cond 查询条件
     * @return self
     */
    public Bool should(QueryCondition cond) {
        Map<String, Object> query = ImmutableMap.of(cond.cond(), cond.value());
        this.should.add(query);
        return this;
    }

    /**
     * 添加一个must not查询条件，不会覆盖，相当于sql中的not
     * @param cond 查询条件
     * @return self
     */
    public Bool mustNot(QueryCondition cond) {
        Map<String, Object> query = ImmutableMap.of(cond.cond(), cond.value());
        this.mustNot.add(query);
        return this;
    }

    /**
     * 控制should条件最低需要匹配的个数，默认在query中should只是起提权作用的，也就是只影响排序，不影响结果总数，如果需要should参与过滤，则需要设置此参数&gt;0
     * @param minimum 最低需要匹配的should条件个数
     * @return self
     */
    public Bool minimumShouldMatch(int minimum) {
        if (minimum > 0) {
            this.minimumShouldMatch = minimum;
        }
        return this;
    }

    /**
     * filter子句
     * <p>
     * Caution: 只有es5x版本支持!!
     * </p>
     * @param cond 过滤条件
     * @return self
     */
    public Bool filter(QueryCondition cond) {
        Map<String, Object> query = ImmutableMap.of(cond.cond(), cond.value());
        this.filter.add(query);
        return this;
    }

    @Override
    public String cond() {
        return "bool";
    }

    @Override
    public Object value() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        if (!this.must.isEmpty()) {
            params.put("must", this.must);
        }
        if (!this.mustNot.isEmpty()) {
            params.put("must_not", this.mustNot);
        }
        if (!this.should.isEmpty()) {
            params.put("should", this.should);
        }
        if (!this.filter.isEmpty()) {
            params.put("filter", this.filter);
        }
        if (this.minimumShouldMatch > 0) {
            params.put(MINIMUM_SHOULD_MATCH, minimumShouldMatch);
        }
        return params;
    }

}
