package site.wetsion.framework.elastic.search.assistant;

/**
 * 补全查询父类，用于保存通用信息
 *
 * @author wetsion
 */
public abstract class BaseSuggest implements SuggestCondition {

    protected String sugName;

    public BaseSuggest(String name) {
        this.sugName = name;
    }

    @Override
    public String name() {
        return this.sugName;
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}
