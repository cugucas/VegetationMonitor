package cn.ac.iscas;

import cn.ac.iscas.domain.TiffDataset;
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
        //String fileName = ".\\test\\tiff\\GF\\wenchang_GF2.tif";
        String fileName = ".\\test\\tiff\\Sentinel\\wengchang_Sentinel.tif";
        gdal.AllRegister();
        TiffDataset tiffDataset = ImageProcess.readTiffBands(fileName, new int[]{1,2});

        TiffDataset afterCalc = tiffDataset;
        afterCalc.setCalcResult(tiffDataset.getRasters().get(0));
        afterCalc.setRasters(null);
        String calcPath = ".\\test\\tiff\\Sentinel\\wengchang_Sentinel_calc.tif";

        ImageProcess.writeTiffBands(calcPath, afterCalc);
    }
}
