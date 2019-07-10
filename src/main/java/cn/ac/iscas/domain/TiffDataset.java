package cn.ac.iscas.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: VegetationMonitor
 * @description:
 * @author: LiangJian
 * @create: 2019-07-10 15:57
 **/
public class TiffDataset {
    private String description;
    private String projection;
    private String projectionRef;
    private double[] geoTransform;
    private List<float[][]> rasters;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjection() {
        return projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public String getProjectionRef() {
        return projectionRef;
    }

    public void setProjectionRef(String projectionRef) {
        this.projectionRef = projectionRef;
    }

    public double[] getGeoTransform() {
        return geoTransform;
    }

    public void setGeoTransform(double[] geoTransform) {
        this.geoTransform = geoTransform;
    }

    public List<float[][]> getRasters() {
        return rasters;
    }

    public void setRasters(List<float[][]> rasters) {
        this.rasters = rasters;
    }
}
