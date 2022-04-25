package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 过滤返回字段，相当于 select foo, bar from table
 *
 * @author wetsion
 */
public class Source implements QueryCondition {

    private static final QueryCondition noSource = new NoSource();

    private List<String> includes;
    private List<String> excludes;

    public Source() {
        this.includes = new LinkedList<>();
        this.excludes = new LinkedList<>();
    }

    /**
     * 指定需要返回的字段
     * @param includes 字段名
     * @return self
     */
    public Source includes(String... includes) {
        this.includes.addAll(Arrays.asList(includes));
        return this;
    }

    /**
     * 指定不需要返回的字段，如果指定了{@link #includes(String...)}，这里就不用指定了
     * @param excludes 字段名
     * @return self
     */
    public Source excludes(String... excludes) {
        this.excludes.addAll(Arrays.asList(excludes));
        return this;
    }

    @Override
    public String cond() {
        return "_source";
    }

    @Override
    public Object value() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        if (!this.includes.isEmpty()) {
            params.put("includes", this.includes);
        }
        if (!this.excludes.isEmpty()) {
            params.put("excludes", this.excludes);
        }
        return params;
    }

    public static QueryCondition noSource() {
        return noSource;
    }

    private static class NoSource implements QueryCondition {
        @Override
        public String cond() {
            return "_source";
        }

        @Override
        public Object value() {
            return false;
        }
    }
}
