package site.wetsion.framework.elastic.search.assistant.constant;

/**
 * @author wetsion
 * @date 2022-04-25 10:29
 */
public enum SortOrderTypeEnum {

    ASC("asc"), DESC("desc");

    private String order;
    SortOrderTypeEnum(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return this.order;
    }
}
