package site.wetsion.framework.elastic.search.assistant.suggest;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.BaseSuggest;
import site.wetsion.framework.elastic.search.assistant.constant.SuggestCompletionTypeEnum;

/**
 * 自动完成提示词
 *
 * @author wetsion
 */
public class CompletionSuggest extends BaseSuggest {

    private String field;
    private int size = 10;

    private String text;

    private SuggestCompletionTypeEnum textType = SuggestCompletionTypeEnum.PREFIX;

    public CompletionSuggest(String name) {
        super(name);
    }

    @Override
    public String cond() {
        return "completion";
    }

    /**
     * 设置补全的来源字段
     * @param field
     * @return
     */
    public CompletionSuggest field(String field) {
        this.field = field;
        return this;
    }

    /**
     * 设置返回的补全词数量
     * @param size
     * @return
     */
    public CompletionSuggest size(int size) {
        this.size = size;
        return this;
    }

    /**
     * 设置前缀词
     * @param text
     * @return
     */
    public CompletionSuggest prefix(String text) {
        this.text = text;
        this.textType = SuggestCompletionTypeEnum.PREFIX;
        return this;
    }

    /**
     * 设置正则匹配词
     * @param text
     * @return
     */
    public CompletionSuggest regex(String text) {
        this.text = text;
        this.textType = SuggestCompletionTypeEnum.REGEX;
        return this;
    }

    @Override
    public Object value() {
        return ImmutableMap.of(this.textType.toString(), this.text,
                cond(), ImmutableMap.of("field", this.field, "size", this.size));
    }
}
