package site.wetsion.framework.elastic.search.assistant.sort;

import com.google.common.collect.ImmutableMap;
import site.wetsion.framework.elastic.search.assistant.BaseSort;
import site.wetsion.framework.elastic.search.assistant.constant.GeoDistanceCalculateAlgorithmEnum;
import site.wetsion.framework.elastic.search.assistant.constant.GeoDistanceUnitEnum;

import java.util.Map;

/**
 * 地址位置排序
 * @author wetsion
 */
public class GeoDistanceSort extends BaseSort<GeoDistanceSort> {

    private double lat;
    private double lon;

    private String distanceType = "plane";
    private String unit = "km";

    @Override
    public String name() {
        return "_geo_distance";
    }

    /**
     * 设置距离原点
     * @param lat 纬度
     * @param lon 经度
     * @return self
     */
    public GeoDistanceSort point(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        return this;
    }

    /**
     * 设置距离计算单位
     * @param unit 距离计算单位（默认km）
     * @return self
     */
    public GeoDistanceSort unit(GeoDistanceUnitEnum unit) {
        if (unit == GeoDistanceUnitEnum.METER) {
            this.unit = "m";
        }
        return this;
    }

    /**
     * 设置距离计算方法（按何种参照系计算）
     * @param type 参照系
     * @return self
     */
    public GeoDistanceSort distanceType(GeoDistanceCalculateAlgorithmEnum type) {
        if (type == GeoDistanceCalculateAlgorithmEnum.SLOPPY_ARC) {
            this.distanceType = "sloppy_arc";
        } else if (type == GeoDistanceCalculateAlgorithmEnum.ARC) {
            this.distanceType = "arc";
        }
        return this;
    }

    @Override
    public Map<String, Object> output() {
        Map<String, Object> output = super.output();
        output.put(super.name(), ImmutableMap.of("lat", this.lat, "lon", this.lon));
        output.put("unit", this.unit);
        output.put("distance_type", this.distanceType);
        return output;
    }

}
