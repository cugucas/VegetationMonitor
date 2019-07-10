package cn.ac.iscas;

import cn.ac.iscas.image.ImageProcess;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

import static javax.swing.text.StyleConstants.Size;

/**
 * @program: VegetationMonitor
 * @description:
 * @author: LiangJian
 * @create: 2019-07-10 09:50
 **/
public class ImageApplication {
    public static void main(String[] args) {
        gdal.AllRegister();
        //String fileName_tif = "E:\\VegetationMonitor\\test\\tiff\\Sentinel\\wengchang_Sentinel.tif";
        String fileName = "E:\\VegetationMonitor\\test\\tiff\\GF\\wenchang_GF2.tif";
        gdal.AllRegister();
        ImageProcess.getTiffBands(fileName, new int[]{1,2});



        Dataset hDataset = gdal.Open(fileName, gdalconstConstants.GA_ReadOnly);
        if (hDataset == null)
        {
            System.err.println("GDALOpen failed - " + gdal.GetLastErrorNo());
            System.err.println(gdal.GetLastErrorMsg());
            System.exit(1);
        }
        Driver hDriver = hDataset.GetDriver();
        System.out.println("Driver: " + hDriver.getShortName() + "/" + hDriver.getLongName());
        int iXSize = hDataset.getRasterXSize();
        int iYSize = hDataset.getRasterYSize();
        String waveLength = hDataset.GetRasterBand(1).GetMetadataItem("wavelength");
        System.out.println("Size is " + iXSize + ", " + iYSize);
        Band band = hDataset.GetRasterBand(1);
        String description = hDriver.GetDescription();
        String projection = hDataset.GetProjection();
        String projectionRef = hDataset.GetProjectionRef();
        double[] geoTransform = hDataset.GetGeoTransform();
        int buf[] = new int[iXSize];
        for(int i=0; i<10; i++)
        {
            band.ReadRaster(0, i, iXSize, 1, buf);
            for(int j=0; j<10; j++) {
                System.out.print(buf[j] + ", ");
            }
            System.out.println("\n");
        }
        hDataset.delete();

    }
}
