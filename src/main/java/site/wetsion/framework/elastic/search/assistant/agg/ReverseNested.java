package site.wetsion.framework.elastic.search.assistant.agg;

import java.util.Collections;

/**
 * 在nested聚合中重新join父文档数据
 *
 * @author wetsion
 */
public class ReverseNested extends BaseAggs<ReverseNested> {

    public ReverseNested(String name) {
        super(name);
    }

    @Override
    public String cond() {
        return "reverse_nested";
    }

    @Override
    public Object value() {
        return toBodyWithNested(Collections.emptyMap());
    }
}
