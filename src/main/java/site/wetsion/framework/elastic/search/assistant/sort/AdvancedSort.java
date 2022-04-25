package site.wetsion.framework.elastic.search.assistant.sort;

import site.wetsion.framework.elastic.search.assistant.BaseSort;

import java.util.Map;

/**
 * 高级排序设置
 * @author wetsion
 */
public class AdvancedSort extends BaseSort<AdvancedSort> {

    private Comparable missing = null;

    /**
     * 设置当字段不存在时用来参与排序的默认值
     * @param missing 默认值
     * @return self
     */
    public AdvancedSort missing(Comparable missing) {
        this.missing = missing;
        return this;
    }

    @Override
    public Map<String, Object> output() {
        Map<String, Object> output = super.output();
        if (null != this.missing) {
            output.put("missing", missing);
        }
        return output;
    }

}
