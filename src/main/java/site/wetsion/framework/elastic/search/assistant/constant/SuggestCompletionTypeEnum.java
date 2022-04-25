package site.wetsion.framework.elastic.search.assistant.constant;

/**
 * 自动补全查询类型
 *
 * @author wetsion
 */
public enum SuggestCompletionTypeEnum {

    /**
     * 前缀补全
     */
    PREFIX("prefix"),

    /**
     * 正则补全
     */
    REGEX("regex");

    private String name;

    SuggestCompletionTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
