package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.BaseSort;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-25 10:23
 */
public class Sort implements QueryCondition {
    private List<Object> sort;

    public Sort() {
        this.sort = new LinkedList<>();
    }

    /**
     * 按指定字段倒序排序，简化方式
     * @param field 字段名
     * @return self
     */
    public Sort desc(String field) {
        Map<String, Object> desc = Maps.newHashMapWithExpectedSize(1);
        desc.put(field, "desc");
        sort.add(desc);
        return this;
    }

    /**
     * 按指定字段生序排序，简化方式
     * @param field 字段名
     * @return self
     */
    public Sort asc(String field) {
        Map<String, Object> asc = Maps.newHashMapWithExpectedSize(1);
        asc.put(field, "asc");
        sort.add(asc);
        return this;
    }

    /**
     * 使用高级排序条件
     * @param field 排序条件
     * @return self
     */
    public Sort field(BaseSort field) {
        Map<String, Object> so = Maps.newHashMapWithExpectedSize(1);
        so.put(field.name(), field.output());
        sort.add(so);
        return this;
    }

    @Override
    public String cond() {
        return "sort";
    }

    @Override
    public Object value() {
        return sort;
    }
}
