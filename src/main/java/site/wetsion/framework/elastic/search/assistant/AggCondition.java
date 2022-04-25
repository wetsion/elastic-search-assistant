package site.wetsion.framework.elastic.search.assistant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-24 17:51
 */
public interface AggCondition<T> extends QueryCondition {

    String name();

    T sub(AggCondition aggCondition);

    @Override
    default Map<String, Object> toMap() {
        return ImmutableMap.of(this.name(), this.value());
    }
}
