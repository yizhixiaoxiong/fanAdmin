package zxing;

import java.io.File;
import java.io.IOException;

public class Demo {
	public static void main(String[] args) throws IOException {
		File file = new File("E:/game\\uploadFiles/uploadImgs/20180608","3241d1c28775401a8bdbb.png");
		if(!file.exists()){	//文件不存在
			if(!file.getParentFile().exists()){	//是否存在父文件
				file.getParentFile().mkdirs();
			}
			file.createNewFile();			//创建文件
		}
		
		System.out.println("执行完毕");
	}
}
