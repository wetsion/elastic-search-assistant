package site.wetsion.framework.elastic.search.assistant.constant;

/**
 * @author wetsion
 * @date 2022-04-25 11:31
 */
public enum MultiMatchTypeEnum {

    BEST_FIELDS("best_fields"),
    MOST_FIELDS("most_fields"),
    PHRASE("phrase");

    private String type;

    MultiMatchTypeEnum(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
