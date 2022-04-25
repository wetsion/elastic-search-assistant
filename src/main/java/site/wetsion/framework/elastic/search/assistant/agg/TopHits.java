package site.wetsion.framework.elastic.search.assistant.agg;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.query.Sort;
import site.wetsion.framework.elastic.search.assistant.query.Source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wetsion
 */
public class TopHits extends BaseAggs<TopHits> {
    private int size;
    private Source source;
    private final List<Object> sorts = new ArrayList<>();

    public TopHits(String name) {
        super(name);
    }

    public TopHits size(int size) {
        this.size = size;
        return this;
    }

    public TopHits source(Source source) {
        this.source = source;
        return this;
    }

    public TopHits sort(Sort sort) {
        if (sort != null) {
            this.sorts.addAll((Collection<?>) sort.value());
        }
        return this;
    }

    @Override
    public String cond() {
        return "top_hits";
    }

    @Override
    public Object value() {
        Map<String, Object> value = ImmutableMap.of("size", this.size, "_source", this.source.value(), "sort", this.sorts);
        return toBodyWithNested(value);
    }
}
