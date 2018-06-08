package com.fan.manage.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * 上传下载文件
 * @author hanxiaofan
 *
 */
public class FileUpload {
	//判断路径是否存在，存在就上传，不存在便创建
	
	/**
	 * 写文件到当前目录的upload目录中
	 * @param in  	输入流
	 * @param dir 	路径
	 * @param realName 文件名
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private static String copyFile(InputStream in,String dir,String fileName) throws IOException{
		File file = mkdirsPath(dir,fileName);
		FileUtils.copyInputStreamToFile(in, file);
		return fileName;
	}
	
	
	/**
	 * @param path 路径
	 * @param fileName	文件名
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static File mkdirsPath(String path,String fileName) throws IOException{
		File file = new File(path,fileName);
		if(!file.exists()){	//文件不存在
			if(!file.getParentFile().exists()){	//是否存在父文件
				file.createNewFile();			//创建文件
			}
		}
		return file;
	}
}
