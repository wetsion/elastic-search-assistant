package site.wetsion.framework.elastic.search.assistant.agg;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.AggCondition;

import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-24 17:52
 */
public abstract class BaseAggs<T extends BaseAggs> implements AggCondition<T> {

    protected String aggName;
    protected Map<String, Object> nested;

    public BaseAggs(String name) {
        this.aggName = name;
    }

    @Override
    public String name() {
        return this.aggName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T sub(AggCondition agg) {
        if (null == this.nested) {
            this.nested = Maps.newHashMap();
        }

        this.nested.put(agg.name(), agg.value());
        return (T) this;
    }

    protected Map<String, Object> toBodyWithNested(Map<String, Object> value) {
        Map<String, Object> body = ImmutableMap.of(this.cond(), value);
        if (null != this.nested) {
            body.put("aggs", this.nested);
        }

        return body;
    }

    @Override
    public String toString() {
        return this.toJsonString();
    }
}
