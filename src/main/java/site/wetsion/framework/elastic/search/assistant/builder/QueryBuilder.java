package site.wetsion.framework.elastic.search.assistant.builder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.AggCondition;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;
import site.wetsion.framework.elastic.search.assistant.SuggestCondition;
import site.wetsion.framework.elastic.search.assistant.constant.Constants;
import site.wetsion.framework.elastic.search.assistant.constant.SerialFeaturesEnum;

import java.util.Collections;
import java.util.Map;

/**
 * 适配es2.x之后的版本
 * @author wetsion
 */
public class QueryBuilder {

    private Map<String, Object> query = null;
    private Map<String, Object> aggs = null;
    private Map<String, Object> suggesters = null;
    private int size;
    private int from;
    private QueryCondition sort;
    private QueryCondition source;
    private QueryCondition highlight;
    private QueryCondition searchAfter;
    private boolean trackTotalHits = false;

    public QueryBuilder() {
        this.size = Constants.UNSET;
        this.from = 0;
    }

    /**
     * 设置查询条件
     * @param cond 查询条件
     * @return self
     */
    public QueryBuilder query(QueryCondition cond) {
        if (null == this.query) {
            this.query = Maps.newHashMap();
        }
        this.query.put(cond.cond(), cond.value());
        return this;
    }

    /**
     * 设置统计查询
     * @param cond 统计查询
     * @return 当前操作的builder
     */
    public QueryBuilder aggs(AggCondition cond) {
        if (null == this.aggs) {
            this.aggs = Maps.newHashMap();
        }
        this.aggs.put(cond.name(), cond.value());
        return this;
    }

    /**
     * 设置补全条件
     * @param condition
     * @return
     */
    public QueryBuilder suggester(SuggestCondition condition) {
        if (null == this.suggesters) {
            this.suggesters = Maps.newHashMap();
        }
        this.suggesters.put(condition.name(), condition.value());
        return this;
    }

    /**
     * 设置查询需要返回的记录条数
     * @param size 需要返回的记录条数
     * @return self
     */
    public QueryBuilder size(int size) {
        this.size = size;
        return this;
    }

    /**
     * 设置查询结果的偏移量
     * @param from 从from条记录后开始返回结果
     * @return self
     */
    public QueryBuilder from(int from) {
        this.from = from;
        return this;
    }

    /**
     * 设置排序条件，设置后会覆盖已有条件
     * @param cond 排序条件
     * @return self
     */
    public QueryBuilder sort(QueryCondition cond) {
        this.sort = cond;
        return this;
    }

    /**
     * 设置返回字段，设置后会覆盖已有设置
     * @param cond 字段条件
     * @return self
     */
    public QueryBuilder source(QueryCondition cond) {
        this.source = cond;
        return this;
    }

    /**
     * 设置高亮查询条件
     * @param cond 高亮查询
     * @return self
     */
    public QueryBuilder highlight(QueryCondition cond) {
        this.highlight = cond;
        return this;
    }

    /**
     * 设置返回精确的总命中数
     * @param trackTotalHits 是否返回精确总数
     * @return self
     */
    public QueryBuilder trackTotalHits(boolean trackTotalHits) {
        this.trackTotalHits = trackTotalHits;
        return this;
    }

    /**
     * 遍历数据
     * @return self
     */
    public QueryBuilder searchAfter(QueryCondition condition) {
        this.searchAfter = condition;
        return this;
    }

    @Override
    public String toString() {
        return toJsonString();
    }

    /**
     * 返回json格式字符串
     * @return json格式字符串
     */
    public String toJsonString() {
        return toJsonString(SerialFeaturesEnum.WRITE_DATE_WITH_DATEFORMT);
    }

    /**
     * 按照指定的序列化设置返回json格式字符串
     * @param features 序列化设置, 参考 {@link SerialFeaturesEnum}
     * @return self
     */
    public String toJsonString(SerialFeaturesEnum features) {
        Map<String, Object> finalQuery = Maps.newHashMap();

        boolean hasCondition = false;
        // query & filter
        if (null != this.query) {
            finalQuery.put("query", this.query);
            hasCondition = true;
        }
        // aggregations
        if (null != this.aggs) {
            finalQuery.put("aggs", this.aggs);
            hasCondition = true;
        }
        // suggest
        if (null != this.suggesters) {
            finalQuery.put("suggest", this.suggesters);
            hasCondition = true;
        }
        // default match_all
        if (!hasCondition) {
            finalQuery.put("query", ImmutableMap.of("match_all", Collections.emptyMap()));
        }

        // track total hits
        if (this.trackTotalHits) {
            finalQuery.put("track_total_hits", true);
        }

        // search_after
        if (null != this.searchAfter) {
            finalQuery.put(this.searchAfter.cond(), this.searchAfter.value());
        }

        // sort & source & highlight
        if (null != this.sort) {
            finalQuery.put(this.sort.cond(), this.sort.value());
        }
        if (null != this.source) {
            finalQuery.put(this.source.cond(), this.source.value());
        }
        if (null != this.highlight) {
            finalQuery.put(this.highlight.cond(), this.highlight.value());
        }

        // from & size
        finalQuery.put("from", this.from);
        if (null != this.aggs) {
            // 没有查询条件的统计，默认size=0
            finalQuery.put("size", Constants.UNSET == this.size ? 0 : this.size);
        } else {
            // 其余默认size=10
            finalQuery.put("size", Constants.UNSET == this.size ? 10 : this.size);
        }

        switch (features) {
            case WRITE_DATE_WITH_DATEFORMT:
                return JSON.toJSONString(finalQuery, SerializerFeature.WriteDateUseDateFormat);
            case SAME_AS_FASTJSON_DEFAULT:
            default:
                return JSON.toJSONString(finalQuery);
        }
    }

}
