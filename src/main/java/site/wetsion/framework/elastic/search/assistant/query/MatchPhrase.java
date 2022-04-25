package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.Boostable;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.Map;

/**
 * 短语查询，相较于match有更高的匹配要求，记录字段必须全部包含查询字符，且字符顺序／间隔需要保持一致
 * <p>
 * 建议搭配match使用，保证准确率的同时提高召回率
 * </p>
 * @author wetsion
 */
public class MatchPhrase implements QueryCondition, Boostable<MatchPhrase> {

    private String field;
    private Object value;
    private int slop = 0;
    private double boost = Double.NEGATIVE_INFINITY;

    private final static String QUERY = "query";
    private final static String SLOP = "slop";
    private final static String BOOST = "boost";

    /**
     * 构造器，同时指定字段名和查询值
     * @param field 字段名
     * @param value 查询值
     */
    public MatchPhrase(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    /**
     * 指定查询词的误差距离，比如 "我的" 与 "的我" 的误差距离是2，主要用于提高查询结果的召回率，不建议使用，建议通过搭配match提高召回率
     * @param slop 误差距离
     * @return self
     */
    public MatchPhrase slop(int slop) {
        if (slop > 0) {
            this.slop = slop;
        }
        return this;
    }

    @Override
    public String cond() {
        return "match_phrase";
    }

    @Override
    public MatchPhrase boost(double boost) {
        if (boost > 0) {
            this.boost = boost;
        }
        return this;
    }

    @Override
    public Object value() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        if (Double.NEGATIVE_INFINITY == this.boost && this.slop <= 0) {
            params.put(field, value);
        } else {
            Map<String, Object> minimum = Maps.newHashMapWithExpectedSize(3);
            minimum.put(QUERY, value);
            if (this.slop > 0) {
                minimum.put(SLOP, slop);
            }
            if (Double.NEGATIVE_INFINITY != this.boost) {
                minimum.put(BOOST, this.boost);
            }
            params.put(field, minimum);
        }
        return params;
    }

}
