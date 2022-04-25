package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.Boostable;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.Map;

/**
 * 处理模糊查询，可以设置minimum_should_match用于控制匹配的字符数
 * @author wetsion
 */
public class Match implements QueryCondition, Boostable<Match> {

    private String field;
    private String value;
    private String matchPercentAge;
    private double boost = Double.NEGATIVE_INFINITY;

    /**
     * 构造器，同时指定字段名和查询词
     * @param field 字段名
     * @param value 查询词
     */
    public Match(String field, String value) {
        this.field = field;
        this.value = value;
    }

    /**
     * 控制匹配度，比如minimumShouldMatch(75)表示查询到的doc必须包含75%的查询字符串中的字符才能命中
     * @param percentAge 百分比
     * @return self
     */
    public Match minimumShouldMatch(int percentAge) {
        if (percentAge > 0) {
            this.matchPercentAge = percentAge + "%";
        }
        return this;
    }

    /**
     * 控制条件的boost提权
     * @param boost 提权值
     * @return self
     */
    @Override
    public Match boost(double boost) {
        if (boost > 0) {
            this.boost = boost;
        }
        return this;
    }

    @Override
    public String cond() {
        return "match";
    }

    @Override
    public Object value() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        if (null == this.matchPercentAge && Double.NEGATIVE_INFINITY == this.boost) {
            params.put(field, value);
        } else {
            Map<String, Object> minimum = Maps.newHashMapWithExpectedSize(3);
            minimum.put("query", value);
            if (null != this.matchPercentAge) {
                minimum.put("minimum_should_match", matchPercentAge);
            }
            if (Double.NEGATIVE_INFINITY != this.boost) {
                minimum.put("boost", this.boost);
            }
            params.put(field, minimum);
        }
        return params;
    }

}
