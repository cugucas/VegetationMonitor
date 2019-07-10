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
        //String fileName = ".\\test\\tiff\\GF\\wenchang_GF2.tif";
        String fileName = ".\\test\\tiff\\Sentinel\\\\wengchang_Sentinel.tif";
        gdal.AllRegister();
        ImageProcess.readTiffBands(fileName, new int[]{1,2});

    }
}
