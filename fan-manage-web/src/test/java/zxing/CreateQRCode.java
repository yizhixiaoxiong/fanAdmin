package zxing;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class CreateQRCode {
	
	public static void main(String[] args) {
		int width = 300;
		int height = 300;
		String format = "png";
		String content = "www.baidu,com";
		//定义二维码参数
		HashMap hints = new HashMap();
		//设置编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		//纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		//设置二维码白边
		hints.put(EncodeHintType.MARGIN, 2);	
		
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			Path file = new File("E:"+File.separator+"game"+File.separator+"TDC-test.png").toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
