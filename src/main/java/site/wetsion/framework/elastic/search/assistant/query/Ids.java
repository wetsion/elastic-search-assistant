package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.*;

/**
 * ids查询
 *
 * @author wetsion
 */
public class Ids implements QueryCondition {

    private List<String> values;

    public Ids() {
        this(Collections.emptyList());
    }

    public Ids(List<String> ids) {
        values(ids);
    }

    /**
     * 设置需要查询的ids
     * @param values
     * @return
     */
    public Ids values(List<String> values) {
        this.values = new ArrayList<>(values);
        return this;
    }

    /**
     * 设置需要查询的ids
     * @param values
     * @return
     */
    public Ids values(String... values) {
        return values(Arrays.asList(values));
    }

    /**
     * 添加一个id
     * @param id
     * @return
     */
    public Ids append(String id) {
        this.values.add(id);
        return this;
    }

    @Override
    public String cond() {
        return "ids";
    }

    @Override
    public Object value() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put("values", values);
        return params;
    }
}
