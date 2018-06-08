package com.fan.manage.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.swetake.util.Qrcode;

/**
 * 二维码工具类
 * @author hanxiaofan
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class TowCodeUtils {
	
	/**
	 * 生成二维码(QRCode)图片
	 * @param content 存储内容
	 * @param imgPath 存储路径
	 * @param imgType 存储形式
	 * @param size	   存储大小
	 */
	public static void encoderORCode(String content,String imgPath,String imgType,int size){
		try {
			BufferedImage bufImg = qRCodeCommon(content, imgType, size);
			File imgFile = new File(imgPath);
			//生成二维码QRCode图片
			ImageIO.write(bufImg, imgType, imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解析二维码(QRCode) 
	 * @param imgPath	存储路径
	 * @throws IOException 
	 */
	public static String readderORCode(String imgPath){
		File imageFile = new File(imgPath);
		BufferedImage bufImg = null;
		String decodedData = null;
		try {
			if(!imageFile.exists()){
				System.out.println("二维码不存在！");
			}
			bufImg = ImageIO.read(imageFile);
			QRCodeDecoder decoder = new QRCodeDecoder();
			decodedData = new String(decoder.decode(new TowDimensionCodeImage(bufImg)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decodedData;
	}
	
	/**
	 * 二维码生成公共类(QRCode)
	 * @param content 存储内容  
     * @param imgType 图片类型  
     * @param size 二维码尺寸  
	 * @return
	 */
	public static BufferedImage qRCodeCommon(String content,String imgType,int size){
		BufferedImage bufImg = null;
		try {
			Qrcode qrcodeHandler = new Qrcode();
			//设置二维码排错率，可选L(7%),M(15%),Q(25%),H(30%),
			//排错率越高可存储的信息越少
			//但对二维码清晰度的要求越小 
			qrcodeHandler.setQrcodeErrorCorrect('M');
			//N代表数字，A代表a-z，B代表其他字符,
			qrcodeHandler.setQrcodeEncodeMode('B');
			//设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大   
			qrcodeHandler.setQrcodeVersion(size);
			//图片尺寸
			int imgSize = 67 + 12 * (size - 1);
			
			bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			//绘图
			Graphics2D gs = bufImg.createGraphics();
			//设置背景颜色
			gs.setBackground(Color.WHITE);
			 // 设定图像颜色> BLACK    
			gs.setColor(Color.BLACK);
			gs.clearRect(0, 0, imgSize, imgSize);//清除下画板内容
			//设置偏移量，不设置可能导致的解析出错
			int pixoff = 2; 
			//对内容进行编码
			byte[] contentBytes = content.getBytes("utf-8");
			if (contentBytes.length > 0 && contentBytes.length < 800) {
				boolean[][] s = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < s.length; i++) {
					for (int j = 0; j < s.length; j++) {
						if (s[j][i]) {
							gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);
						}
					}
				}
			}else{
				throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
			}
			gs.dispose();
			bufImg.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufImg;
	}
	
	/**
	 * 生成二维码(zxing)
	 * @param content
	 * @param imgType
	 * @param size
	 * @param imagePath
	 */
	public static void encoderZxingCode(String content,String imgType,int size,String imagePath){
		Path file = new File(imagePath).toPath();
		try {
			MatrixToImageWriter.writeToPath(zXCommon(content, size), imgType, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readerZxingCode(String imagePath){
		MultiFormatReader formatReader = new MultiFormatReader();
		File file = new File(imagePath);
		Result result = null;
		try {
			BufferedImage image = ImageIO.read(file);
			//定义二维码参数
			
			Map map = new HashMap();
			map.put(EncodeHintType.CHARACTER_SET, "utf-8");
			//获取读取二维码结果
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
			result = formatReader.decode(binaryBitmap, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString().trim();
	}
	
	/**
	 * zxing 公共类
	 * @param content
	 * @param imgType
	 * @return
	 */
	
	public static BitMatrix zXCommon(String content,int size){
		//定义二维码参数
		HashMap hints = new HashMap();
		//设置编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		//纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		//设置二维码白边
		hints.put(EncodeHintType.MARGIN, 2);
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return bitMatrix;
	}
	
	
}
