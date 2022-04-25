package site.wetsion.framework.elastic.search.assistant.agg;

import site.wetsion.framework.elastic.search.assistant.query.Term;

import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-25 11:04
 */
public class Filter extends  BaseAggs<Filter> {

    private Term term;

    /**
     * 指定统计结果名称
     * @param name 结果名称
     */
    public Filter(String name) {
        super(name);
    }

    /**
     * 指定某个条件，返回符合条件的记录条数
     * @param term 过滤条件
     * @return self
     */
    public Filter term(Term term) {
        this.term = term;
        return this;
    }

    @Override
    public String cond() {
        return "filter";
    }

    @Override
    public Object value() {
        Map<String, Object> value = term.toMap();
        return toBodyWithNested(value);
    }
}
