package site.wetsion.framework.elastic.search.assistant.agg;


import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.exception.SearchException;

import java.util.Map;

/**
 * 用于嵌套字段的统计查询
 *
 * @author wetsion
 */
public class NestedAgg extends BaseAggs<NestedAgg> {

    private String path;

    public NestedAgg(String name) {
        super(name);
    }

    @Override
    public String cond() {
        return "nested";
    }

    @Override
    public Object value() {
        Map<String, Object> value = ImmutableMap.of(cond(), ImmutableMap.of("path", path));
        if (nested == null) {
            throw new SearchException("nested agg body is missing");
        }
        value.put("aggs", nested);
        return value;
    }

    /**
     * 如果是嵌套字段，指定字段路径
     * @param path 父字段名
     * @return self
     */
    public NestedAgg path(String path) {
        this.path = path;
        return this;
    }
}
