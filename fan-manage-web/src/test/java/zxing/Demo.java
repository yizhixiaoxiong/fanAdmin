package zxing;

import java.io.File;
import java.io.IOException;

public class Demo {
	public static void main(String[] args) throws IOException {
		File file = new File("E:\\game\\qrcode.png");
		Double dkb = Double.valueOf(file.length())/1024;
		System.out.println(dkb);
	}
}
