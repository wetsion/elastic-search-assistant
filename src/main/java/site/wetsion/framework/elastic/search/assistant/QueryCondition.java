package site.wetsion.framework.elastic.search.assistant;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-24 17:45
 */
public interface QueryCondition {

    String cond();

    Object value();

    default String toJsonString() {
        return JSON.toJSONString(this.toMap());
    }

    default Map<String, Object> toMap() {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(1);
        map.put(this.cond(), this.value());
        return map;
    }
}
