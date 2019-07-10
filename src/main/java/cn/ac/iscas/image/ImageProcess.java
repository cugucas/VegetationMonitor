package cn.ac.iscas.image;

import cn.ac.iscas.domain.TiffDataset;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

import java.util.List;

/**
 * @program: VegetationMonitor
 * @description:
 * @author: LiangJian
 * @create: 2019-07-10 15:51
 **/
public class ImageProcess {
    public static List<TiffDataset> getTiffBands(String tiffPath, int[] bandsNo) {
        Dataset hDataset = gdal.Open(tiffPath, gdalconstConstants.GA_ReadOnly);
        if (hDataset == null) {
            System.err.println("GDALOpen failed - " + gdal.GetLastErrorNo());
            System.err.println(gdal.GetLastErrorMsg());
            return null;
        }

        Driver hDriver = hDataset.GetDriver();
        System.out.println("Driver: " + hDriver.getShortName() + "/" + hDriver.getLongName());
        int iXSize = hDataset.getRasterXSize();
        int iYSize = hDataset.getRasterYSize();
        String waveLength = hDataset.GetRasterBand(1).GetMetadataItem("wavelength");
        System.out.println("Size is " + iXSize + ", " + iYSize);

        String description = hDriver.GetDescription();
        String projection = hDataset.GetProjection();
        String projectionRef = hDataset.GetProjectionRef();
        double[] geoTransform = hDataset.GetGeoTransform();

        float buf[] = new float[iXSize];
        for(int bandNo : bandsNo) {
            Band band = hDataset.GetRasterBand(bandNo);
            band.ReadRaster(0, bandNo, iXSize, 1, buf);
            for(int j=0; j<10; j++) {
                System.out.print(buf[j] + ", ");
            }
            System.out.println("\n");
        }
        hDataset.delete();
        return null;
    }
}
