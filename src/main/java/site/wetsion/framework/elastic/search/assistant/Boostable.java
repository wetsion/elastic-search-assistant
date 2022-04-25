package site.wetsion.framework.elastic.search.assistant;

/**
 * @author wetsion
 */
public interface Boostable<T> {

    /**
     * 设置查询条件的提权
     * @param boost 提权大小
     * @return T
     */
    T boost(double boost);

}
