package site.wetsion.framework.elastic.search.assistant.query;

import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 遍历数据用
 *
 * @author wetsion
 */
public class SearchAfter implements QueryCondition {

    private ArrayList<Comparable> afters;

    public SearchAfter() {
        this.afters = new ArrayList<>();
    }

    /**
     * 设置分段阈值
     * @param values
     * @return
     */
    public SearchAfter after(Comparable... values) {
        this.afters.addAll(Arrays.asList(values));
        return this;
    }

    @Override
    public String cond() {
        return "search_after";
    }

    @Override
    public Object value() {
        return this.afters;
    }
}
