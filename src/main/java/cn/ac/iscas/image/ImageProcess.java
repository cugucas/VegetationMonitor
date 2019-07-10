package cn.ac.iscas.image;

import cn.ac.iscas.domain.TiffDataset;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: VegetationMonitor
 * @description:
 * @author: LiangJian
 * @create: 2019-07-10 15:51
 **/
public class ImageProcess {
    public static TiffDataset readTiffBands(String tiffPath, int[] bandsNo) {
        TiffDataset tiffDataset = new TiffDataset();
        Dataset dataset = gdal.Open(tiffPath, gdalconstConstants.GA_ReadOnly);
        if (dataset == null) {
            System.err.println("GDALOpen failed - " + gdal.GetLastErrorNo());
            System.err.println(gdal.GetLastErrorMsg());
            return null;
        }

        Driver driver = dataset.GetDriver();
        int xSize = dataset.getRasterXSize();
        int ySize = dataset.getRasterYSize();
        String waveLength = dataset.GetRasterBand(1).GetMetadataItem("wavelength");
        System.out.println("Size is " + xSize + ", " + ySize);

        tiffDataset.setDescription(driver.GetDescription());
        tiffDataset.setProjection(dataset.GetProjection());
        tiffDataset.setProjectionRef(dataset.GetProjectionRef());
        tiffDataset.setGeoTransform(dataset.GetGeoTransform());

        List<float[][]> rasters = new ArrayList<>();
        float bandBuf[] = new float[xSize * ySize];
        for(int bandNo : bandsNo) {
            Band band = dataset.GetRasterBand(bandNo);
            band.ReadRaster(0, 0, xSize, ySize, bandBuf);
            float[][] raster = new float[ySize][xSize];
            for ( int i = 0; i < ySize; i++ ) {
                System.arraycopy(bandBuf, (i * xSize), raster[i], 0, xSize);
            }
            rasters.add(raster);
        }
        tiffDataset.setRasters(rasters);
        dataset.delete();
        return tiffDataset;
    }

    public static boolean writeTiffBands(String tiffPath, TiffDataset tiffDataset) {

        return false;
    }
}
