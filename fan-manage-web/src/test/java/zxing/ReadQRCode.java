package zxing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class ReadQRCode {
	public static void main(String[] args) {
		MultiFormatReader formatReader = new MultiFormatReader();
		File file = new File("E:"+File.separator+"game"+File.separator+"qrcode.png");
		try {
			BufferedImage image = ImageIO.read(file);
			//定义二维码参数
			Map map = new HashMap();
			map.put(EncodeHintType.CHARACTER_SET, "utf-8");
			
			//获取读取二维码结果
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
			Result result = formatReader.decode(binaryBitmap, map);
			System.out.println("读取二维码："+result.toString());
			System.out.println("二维码格式："+result.getBarcodeFormat());
			System.out.println("二维码内容:"+result.getText());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
}
