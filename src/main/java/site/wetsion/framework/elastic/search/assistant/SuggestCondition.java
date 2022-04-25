package site.wetsion.framework.elastic.search.assistant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * 搜索提示基础类
 *
 * @author wetsion
 */
public interface SuggestCondition extends QueryCondition {

    /**
     * 返回补全查询返回的补全列表名称
     * @return 聚合名称
     */
    String name();

    /**
     * 返回单条件的键值对
     * @return map
     */
    @Override
    default Map<String, Object> toMap() {
        return ImmutableMap.of(name(), value());
    }

}
