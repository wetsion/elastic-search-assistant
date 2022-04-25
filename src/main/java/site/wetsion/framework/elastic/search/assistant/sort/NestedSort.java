package site.wetsion.framework.elastic.search.assistant.sort;


import site.wetsion.framework.elastic.search.assistant.BaseSort;
import site.wetsion.framework.elastic.search.assistant.exception.SearchException;

import java.util.HashMap;
import java.util.Map;

/**
 * 嵌套字段排序
 *
 * @author wetsion
 */
public class NestedSort extends BaseSort<NestedSort> {

    private String path;

    /**
     * 设置嵌套字段的父字段
     * @param path 父字段名称
     * @return self
     */
    public NestedSort path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public Map<String, Object> output() {
        Map<String, Object> output = super.output();
        if (null == path) {
            throw new SearchException("nested sort should contain path");
        } else {
            output.put("nested", new HashMap<String, Object>(1) {{
                put("path", path);
            }});
        }
        return output;
    }
}
