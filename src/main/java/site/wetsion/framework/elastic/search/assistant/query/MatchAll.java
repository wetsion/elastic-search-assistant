package site.wetsion.framework.elastic.search.assistant.query;

import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.Collections;

/**
 * 匹配全量数据
 *
 * @author wetsion
 */
public class MatchAll implements QueryCondition {

    @Override
    public String cond() {
        return "match_all";
    }

    @Override
    public Object value() {
        return Collections.emptyMap();
    }
}
