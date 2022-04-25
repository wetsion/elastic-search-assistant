package site.wetsion.framework.elastic.search.assistant;

import site.wetsion.framework.elastic.search.assistant.builder.LegacyQueryBuilder;
import site.wetsion.framework.elastic.search.assistant.builder.QueryBuilder;

/**
 * 入口类
 *
 * @author wetsion
 */
public class ElasticSearchAssistant {

    /**
     * 早期es查询
     * @return LegacyQueryBuilder
     */
    @Deprecated
    public static LegacyQueryBuilder legacyBuilder() {
        return new LegacyQueryBuilder();
    }

    /**
     * 查询构造器
     * @return QueryBuilder
     */
    public static QueryBuilder newBuilder() {
        return new QueryBuilder();
    }

}
