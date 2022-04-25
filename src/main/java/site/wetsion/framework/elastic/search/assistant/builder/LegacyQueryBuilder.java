package site.wetsion.framework.elastic.search.assistant.builder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.AggCondition;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;
import site.wetsion.framework.elastic.search.assistant.constant.Constants;
import site.wetsion.framework.elastic.search.assistant.constant.SerialFeaturesEnum;

import java.util.Collections;
import java.util.Map;

/**
 * 适配es1.x版本
 * @author wetsion
 */
public class LegacyQueryBuilder {

    private final Map<String, Object> query;
    private final Map<String, Object> filter;
    private Map<String, Object> aggs;
    private int size;
    private int from;
    private QueryCondition sort;
    private QueryCondition source;
    private QueryCondition highlight;

    public LegacyQueryBuilder() {
        this.query = Maps.newHashMap();
        this.filter = Maps.newHashMap();
        this.aggs = null;
        this.size = Constants.UNSET;
        this.from = 0;
    }

    /**
     * 在filter子句中添加过滤条件
     * @param cond 查询条件
     * @return self
     */
    public LegacyQueryBuilder filter(QueryCondition cond) {
        this.filter.put(cond.cond(), cond.value());
        return this;
    }

    /**
     * 在query子句中添加查询条件
     * @param cond 查询条件
     * @return self
     */
    public LegacyQueryBuilder query(QueryCondition cond) {
        this.query.put(cond.cond(), cond.value());
        return this;
    }

    /**
     * 添加统计查询条件
     * @param cond 统计条件
     * @return self
     */
    public LegacyQueryBuilder aggs(AggCondition cond) {
        if (null == this.aggs) {
            this.aggs = Maps.newHashMap();
        }
        this.aggs.put(cond.name(), cond.value());
        return this;
    }

    /**
     * 设置查询需要返回的记录条数
     * @param size 需要返回的记录条数
     * @return self
     */
    public LegacyQueryBuilder size(int size) {
        this.size = size;
        return this;
    }

    /**
     * 设置查询结果的偏移量
     * @param from 从from条记录后开始返回结果
     * @return self
     */
    public LegacyQueryBuilder from(int from) {
        this.from = from;
        return this;
    }

    /**
     * 设置排序条件，设置后会覆盖已有条件
     * @param cond 排序条件
     * @return self
     */
    public LegacyQueryBuilder sort(QueryCondition cond) {
        this.sort = cond;
        return this;
    }

    /**
     * 设置返回字段，设置后会覆盖已有设置
     * @param cond 字段条件
     * @return self
     */
    public LegacyQueryBuilder source(QueryCondition cond) {
        this.source = cond;
        return this;
    }

    /**
     * 设置高亮查询条件
     * @param cond 高亮查询
     * @return self
     */
    public LegacyQueryBuilder highlight(QueryCondition cond) {
        this.highlight = cond;
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

        // query & filter
        if (!this.filter.isEmpty()) {
            Map<String, Object> filtered = Maps.newHashMap();
            filtered.put("filter", this.filter);
            if (!this.query.isEmpty()) {
                filtered.put("query", this.query);
            }
            Map<String, Object> outQuery = Maps.newHashMap();
            outQuery.put("filtered", filtered);
            finalQuery.put("query", outQuery);
        } else if (!this.query.isEmpty()) {
            finalQuery.put("query", this.query);
        } else {
            Map<String, Object> matchAll = Maps.newHashMap();
            matchAll.put("match_all", Collections.emptyMap());
            finalQuery.put("query", matchAll);
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

        // aggregations
        if (null != this.aggs) {
            finalQuery.put("aggs", this.aggs);
        }

        // from & size
        finalQuery.put("from", this.from);
        if (null != this.aggs) {
            // 没有查询条件的统计查询，默认size=0
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
