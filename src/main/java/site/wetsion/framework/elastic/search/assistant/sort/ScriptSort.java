package site.wetsion.framework.elastic.search.assistant.sort;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.BaseSort;

import java.util.Map;

/**
 * 脚本排序
 * @author wetsion
 */
public class ScriptSort extends BaseSort<ScriptSort> {

    private String inlineScript;
    private Map<String, Object> params;

    @Override
    public String name() {
        return "_script";
    }

    /**
     * 设置内嵌脚本
     * @param inline 脚本
     * @return self
     */
    public ScriptSort script(String inline) {
        this.inlineScript = inline;
        return this;
    }

    /**
     * 设置内嵌脚本中需要用到的参数，如果没有脚本参数，可以不设置
     * @param params 脚本参数
     * @return self
     */
    public ScriptSort params(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    @Override
    public Map<String, Object> output() {
        Map<String, Object> output = super.output();
        output.put("type", "number");
        // 拼装脚本
        Map<String, Object> script = ImmutableMap.of("lang", "painless");
        script.put("inline", this.inlineScript);
        if (null != this.params) {
            script.put("params", this.params);
        }
        output.put("script", script);
        return output;
    }

}
