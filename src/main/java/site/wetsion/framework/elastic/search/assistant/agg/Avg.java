package site.wetsion.framework.elastic.search.assistant.agg;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-24 18:01
 */
public class Avg extends BaseAggs<Avg> {

    private String field;
    private Integer missing = null;

    public Avg(String name) {
        super(name);
    }

    public Avg field(String field) {
        this.field = field;
        return this;
    }

    public Avg missing(int missing) {
        this.missing = missing;
        return this;
    }

    @Override
    public String cond() {
        return "avg";
    }

    @Override
    public Object value() {
        Map<String, Object> value = ImmutableMap.of("field", this.field);
        if (null != this.missing) {
            value.put("missing", this.missing);
        }

        return this.toBodyWithNested(value);
    }
}
