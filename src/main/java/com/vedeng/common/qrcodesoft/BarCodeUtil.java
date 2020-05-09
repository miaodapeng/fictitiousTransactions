package com.vedeng.common.qrcodesoft;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.common.constants.Contant;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * <b>Description:</b><br> 条形码生成测试
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.qrcodesoft
 * <br><b>ClassName:</b> BarCodeUtil
 * <br><b>Date:</b> 2017年8月8日 上午11:36:07
 */
public class BarCodeUtil {
	public static Logger logger = LoggerFactory.getLogger(BarCodeUtil.class);

	//-------------------------------------条形码生成One（开始）-------------------------------------------------------
	public void encode(String contents, int width, int height, String imgPath) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, height, null);

			MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(imgPath));

		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}

	/**
	 * 解析条形码
	 * 
	 * @param imgPath
	 * @return
	 */
	public String decode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}
	//-------------------------------------条形码生产One（结束）-------------------------------------------------------
	public static void main(String[] args) {

		//------------------One--------------------
		String imgPath = "E://zxing_EAN13.png";
		// 益达无糖口香糖的条形码
		String contents = "6923450657713";

		int width = 205, height = 60;
		BarCodeUtil handler = new BarCodeUtil();
		handler.encode(contents, width, height, imgPath);
		System.out.println("生成EAN13条形码ok.");
		
		//-------------------Two----------------------
		String msg = "6923450657713";
        String path = "E://barcode.png";
        generateFile(msg, path);
        System.out.println("生成Code39_EAN13条形码ok.");
	}
	
	
	//-------------------------------------条形码生产Two（开始）-------------------------------------------------------
	/**
     * 生成文件
     *
     * @param msg
     * @param path
     * @return
     */
    public static File generateFile(String msg, String path) {
        File file = new File(path);
        try {
            generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
 
    /**
     * 生成字节
     *
     * @param msg
     * @return
     */
    public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }
 
    /**
     * 生成到流
     *
     * @param msg
     * @param ous
     */
    public static void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }
 
        Code39Bean bean = new Code39Bean();
//        EAN13Bean bean = new EAN13Bean();// 条形码类型
 
        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(1.0f / dpi);
 
        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);//Code39Bean时使用
        bean.doQuietZone(false);
 
        String format = "image/png";
        try {
 
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
 
            // 生成条形码
            bean.generateBarcode(canvas, msg);
 
            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
  //-------------------------------------条形码生产Two（结束）-------------------------------------------------------
}
