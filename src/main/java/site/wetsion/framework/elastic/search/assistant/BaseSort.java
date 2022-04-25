package site.wetsion.framework.elastic.search.assistant;

import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.constant.SortModeEnum;
import site.wetsion.framework.elastic.search.assistant.constant.SortOrderTypeEnum;

import java.util.Map;

/**
 * @author wetsion
 * @date 2022-04-25 10:27
 */
public class BaseSort<T extends BaseSort> {

    private String name;
    private SortOrderTypeEnum order = SortOrderTypeEnum.DESC;
    private SortModeEnum mode = null;

    /**
     * 返回字段名
     * @return 字段名
     */
    public String name() {
        return this.name;
    }

    /**
     * 设置字段名
     * @param name 字段名
     * @return self
     */
    public T name(String name) {
        this.name = name;
        return (T) this;
    }

    /**
     * 设置升序／降序
     * @param order 升序／降序
     * @return self
     */
    public T order(SortOrderTypeEnum order) {
        this.order = order;
        return (T) this;
    }

    /**
     * 返回排序顺序
     * @return 升序／降序
     */
    public String order() {
        return this.order.toString();
    }

    /**
     * 设置多值字段选取方式
     * @param mode 取值模式
     * @return self
     */
    public T mode(SortModeEnum mode) {
        this.mode = mode;
        return (T) this;
    }

    /**
     * 返回取值方式
     * @return 模式
     */
    public String mode() {
        return this.mode.toString();
    }

    /**
     * 排序条件详细内容
     * @return 详细配置
     */
    public Map<String, Object> output() {
        Map<String, Object> output = Maps.newHashMap();
        output.put("order", order());
        if (null != mode) {
            output.put("mode", mode());
        }
        return output;
    }
}
