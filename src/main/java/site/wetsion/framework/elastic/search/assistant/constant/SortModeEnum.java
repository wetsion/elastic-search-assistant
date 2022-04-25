package site.wetsion.framework.elastic.search.assistant.constant;

/**
 * 排序模式
 *
 * @author wetsion
 * @date 2022-04-25 10:27
 */
public enum SortModeEnum {
    MIN("min"), MAX("max"), MEDIAN("median"), AVG("avg"), SUM("sum");

    private String mode;
    SortModeEnum(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return this.mode;
    }
}
