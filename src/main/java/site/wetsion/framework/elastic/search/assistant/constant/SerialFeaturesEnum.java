package site.wetsion.framework.elastic.search.assistant.constant;

/**
 * json序列化配置
 * @author wetsion
 */
public enum SerialFeaturesEnum {

    /**
     * 按照fastjson默认配置序列化，日期变为时间戳
     */
    SAME_AS_FASTJSON_DEFAULT,

    /**
     * 指定日期格式，yyyy-MM-dd HH:mm:ss
     */
    WRITE_DATE_WITH_DATEFORMT

}
