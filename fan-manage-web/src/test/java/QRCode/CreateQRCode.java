package QRCode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * 二维码生成
 * @author hanxiaofan
 *
 */
public class CreateQRCode {
	public static void main(String[] args) throws Exception {
		Qrcode x = new Qrcode();
		x.setQrcodeErrorCorrect('M');	//纠错等级(分为L、M、H三个等级)
		x.setQrcodeEncodeMode('N');		//B代表数字，A代表a-z，B代表其他字符
		x.setQrcodeVersion(7);			//版本
		//生成二维码中药存储的信息
		String qrData = "7734577704300040294129441200400200000002000040000";
		int width = 67+12*(7-1);
		int height = 67+12*(7-1);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//绘图
		Graphics2D gs = bufferedImage.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, width, height);//清除下画板内容
		//设置下偏移量，如果不加偏移量，有时会导致出错
		int pixoff = 2;
		
		byte[] d = qrData.getBytes("utf-8");
		if (d.length > 0 && d.length < 800) {
			boolean[][] s = x.calQrcode(d);
			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s.length; j++) {
					if (s[j][i]) {
						gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);
					}
				}
			}
		}
		gs.dispose();
		bufferedImage.flush();
		ImageIO.write(bufferedImage, "png", new File("E:"+File.separator+"game"+File.separator+"qrcode.png"));
	}
}
