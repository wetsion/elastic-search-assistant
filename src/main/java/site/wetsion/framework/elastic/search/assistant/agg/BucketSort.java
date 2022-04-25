package site.wetsion.framework.elastic.search.assistant.agg;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.query.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-25 10:21
 */
public class BucketSort extends BaseAggs<BucketSort> {

    private final List<Object> sorts = new ArrayList<>();
    private int from;
    private int size;

    public BucketSort(String name) {
        super(name);
    }

    public BucketSort from(int from) {
        this.from = from;
        return this;
    }

    public BucketSort size(int size) {
        this.size = size;
        return this;
    }

    public BucketSort sort(Sort sort) {
        this.sorts.addAll((Collection<?>) sort.value());
        return this;
    }


    @Override
    public String cond() {
        return "bucket_sort";
    }

    @Override
    public Object value() {
        Map<String, Object> value = ImmutableMap.of("sort", this.sorts);
        if (from != 0) {
            value.put("from", from);
        }
        if (size != 0) {
            value.put("size", size);
        }
        return toBodyWithNested(value);
    }
}
