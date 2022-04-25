package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.Boostable;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.Map;

/**
 * 脚本查询，比较耗CPU，执行效率看命中范围，越大越高，理论上不会走缓存，建议在指导下使用
 * <br>
 * @author wetsion
 */
public class Script implements QueryCondition, Boostable<Script> {

    private String script;
    private String lang;
    private Map<String, Object> params;
    private double boost = Double.NEGATIVE_INFINITY;

    /**
     * 默认构造器，用painless脚本
     */
    public Script() {
        this("painless");
    }

    /**
     * 构造器
     * @param lang 脚本类型
     */
    public Script(String lang) {
        this.lang(lang);
    }

    /**
     * 设置脚本
     * @param script 脚本代码
     * @return 流式调用
     */
    public Script script(String script) {
        this.script = script;
        return this;
    }

    /**
     * 设置脚本语言种类
     * @param lang 脚本语言种类
     * @return self
     */
    public Script lang(String lang) {
        this.lang = lang;
        return this;
    }

    /**
     * 设置脚本参数
     * @param key/value 脚本参数，kv形式
     * @return self
     */
    public Script params(String key, Object value) {
        this.addParam(key, value);
        return this;
    }

    /**
     * 设置脚本参数
     * @param key1/value1 脚本参数，kv形式
     * @return self
     */
    public Script params(String key1, Object value1, String key2, Object value2) {
        this.addParam(key1, value1).addParam(key2, value2);
        return this;
    }

    /**
     * 设置脚本参数
     * @param key1/value1 脚本参数，kv形式
     * @return self
     */
    public Script params(String key1, Object value1, String key2, Object value2, String key3, Object value3) {
        this.addParam(key1, value1).addParam(key2, value2).addParam(key3, value3);
        return this;
    }

    /**
     * 设置脚本参数
     * @param key/value 脚本参数，kv形式
     * @return self
     */
    public Script addParam(String key, Object value) {
        if (null == this.params) {
            this.params = ImmutableMap.of(key, value);
        } else {
            this.params.put(key, value);
        }
        return this;
    }

    /**
     * 控制条件的boost提权
     * @param boost 额度
     * @return self
     */
    @Override
    public Script boost(double boost) {
        if (boost > 0) {
            this.boost = boost;
        }
        return this;
    }

    @Override
    public String cond() {
        return "script";
    }

    @Override
    public Object value() {
        Map<String, Object> inner = Maps.newHashMapWithExpectedSize(3);
        inner.put("inline", this.script);
        inner.put("lang", this.lang);
        if (null != this.params && !this.params.isEmpty()) {
            inner.put("params", params);
        }
        Map<String, Object> value = ImmutableMap.of("script", inner);
        if (Double.NEGATIVE_INFINITY != this.boost) {
            value.put("boost", this.boost);
        }
        return value;
    }

}
