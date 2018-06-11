package com.fan.manage.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * 上传工具类
 * @author hanxiaofan
 *
 */
public class FileUtil {
	
	/**
	 * 获取文件的大小
	 * @param fielPath	文件路径
	 * @return
	 */
	public static String getFilesize(String fielPath) {
		DecimalFormat df = new DecimalFormat("#.00");
		File file = new File(fielPath);
		return df.format(Double.valueOf(file.length())/1024);
	}
	
	/**
	 * 单个文件上传
	 * @param is
	 * @param fileName	文件名字
	 * @param filePath	存储路径
	 */
	public static void upFile(InputStream is,String fileName,String filePath){
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f = new File(filePath+File.separator+fileName);
		try {
			bis = new BufferedInputStream(is);
			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);
			byte[] bt = new byte[4096];		//4MB
			int len;
			while((len = bis.read(bt))>0){
				bos.write(bt, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != bos){
					bos.close();
					bos = null;
				}
				if(null != is){
					is.close();
					is=null;
				}
				if (null != bis) {
					bis.close();
					bis = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
