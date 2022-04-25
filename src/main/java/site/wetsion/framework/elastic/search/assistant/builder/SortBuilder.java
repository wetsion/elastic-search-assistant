package site.wetsion.framework.elastic.search.assistant.builder;


import site.wetsion.framework.elastic.search.assistant.sort.GeoDistanceSort;

/**
 * 排序条件组装
 * @author wetsion
 */
public class SortBuilder {

    public static GeoDistanceSort geoDistanceSort() {
        return new GeoDistanceSort();
    }

}
