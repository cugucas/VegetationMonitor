package cn.ac.iscas.image;

import cn.ac.iscas.domain.TiffDataset;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

        tiffDataset.setXSize(xSize);
        tiffDataset.setYSize(ySize);
        tiffDataset.setDescription(driver.GetDescription());
        tiffDataset.setProjection(dataset.GetProjection());
        tiffDataset.setProjectionRef(dataset.GetProjectionRef());
        tiffDataset.setGeoTransform(dataset.GetGeoTransform());

        List<float[][]> rasters = new ArrayList<>();
        Double[] noDataValue = new Double[1];
        float bandBuf[] = new float[xSize * ySize];
        for(int bandNo : bandsNo) {
            Band band = dataset.GetRasterBand(bandNo);
            band.GetNoDataValue(noDataValue);
            band.ReadRaster(0, 0, xSize, ySize, bandBuf);
            float[][] raster = new float[ySize][xSize];
            for ( int i = 0; i < ySize; i++ ) {
                System.arraycopy(bandBuf, (i * xSize), raster[i], 0, xSize);
            }
            rasters.add(raster);
        }
        tiffDataset.setNoDataValue(noDataValue);
        tiffDataset.setRasters(rasters);
        dataset.delete();
        return tiffDataset;
    }

    public static void writeTiffBands(String tiffPath, TiffDataset tiffDataset) {
        int xSize = tiffDataset.getXSize();
        int ySize = tiffDataset.getYSize();
        Dataset dataset = gdal.GetDriverByName(tiffDataset.getDescription()).Create(tiffPath, xSize, ySize, 1, gdalconstConstants.GDT_Float32);
        dataset.SetGeoTransform(tiffDataset.getGeoTransform());
        dataset.SetProjection(tiffDataset.getProjection());

        float[][] calcResult = tiffDataset.getCalcResult();
        float[] tiffBuf = new float[xSize * ySize];
        for(int i = 0; i < ySize; i++) {
            System.arraycopy(calcResult[i], 0, tiffBuf, i * xSize, xSize);
        }

        Band band = dataset.GetRasterBand(1);
        band.SetNoDataValue(tiffDataset.getNoDataValue()[0]);
        dataset.GetRasterBand(1).WriteRaster(0, 0, xSize, ySize, tiffBuf);
        dataset.delete();
    }
}
