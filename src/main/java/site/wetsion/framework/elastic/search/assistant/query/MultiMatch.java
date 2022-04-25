package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;
import site.wetsion.framework.elastic.search.assistant.constant.MultiMatchTypeEnum;

import java.util.*;

/**
 * 多字段match
 *
 * @author wetsion
 */
public class MultiMatch implements QueryCondition {

    private static final List<String> all = Collections.unmodifiableList(Collections.singletonList("*"));

    private String query;
    private Set<String> fields;
    private String matchPercentage = null;
    private MultiMatchTypeEnum type = null;
    private String operator = null;

    @Override
    public String cond() {
        return "multi_match";
    }

    /**
     * 设置查询内容
     * @param keyword
     * @return
     */
    public MultiMatch query(String keyword) {
        this.query = keyword;
        return this;
    }

    /**
     * 设置需要查询的字段
     * @param fields
     * @return
     */
    public MultiMatch fields(String... fields) {
        if (null != fields && fields.length > 0) {
            if (null == this.fields) {
                this.fields = new HashSet<>(Arrays.asList(fields));
            } else {
                this.fields.addAll(Arrays.asList(fields));
            }
        }
        return this;
    }

    /**
     * 设置多字段的匹配策略
     * @param type
     * @return
     */
    public MultiMatch type(MultiMatchTypeEnum type) {
        if (null != type) {
            this.type = type;
        }
        return this;
    }

    /**
     * 设置最低匹配度
     * @param percentage
     * @return
     */
    public MultiMatch minimumShouldMatch(int percentage) {
        if (percentage > 0) {
            this.matchPercentage = percentage + "%";
        }
        return this;
    }

    public MultiMatch operator(String op){
        this.operator = op;
        return this;
    }

    @Override
    public Object value() {
        List<String> f;
        if (null == this.fields || this.fields.isEmpty()) {
            f = all;
        } else {
            f = new ArrayList<>(this.fields);
        }
        Map<String, Object> params = ImmutableMap.of("query", this.query, "fields", f);
        if (null != this.matchPercentage) {
            params.put("minimum_should_match", this.matchPercentage);
        }
        if (null != this.type) {
            params.put("type", this.type.toString());
        }
        if (null != this.operator){
            params.put("operator", this.operator);
        }
        return params;
    }

}
