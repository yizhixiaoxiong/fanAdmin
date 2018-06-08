package QRCode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;

public class ReadQRCode {
	public static void main(String[] args) throws IOException {
		File imageFile = new File("E:"+File.separator+"game"+File.separator+"qrcode.png");
		BufferedImage bufImg = null;
		String decodedData = null;
		if(!imageFile.exists()){
			System.out.println("二维码不存在！");
		}
		bufImg = ImageIO.read(imageFile);
		QRCodeDecoder decoder = new QRCodeDecoder();
		decodedData = new String(decoder.decode(new TowDimensionCodeImage(bufImg)));
		
		System.out.println(decodedData.toString());
		
	}
}
