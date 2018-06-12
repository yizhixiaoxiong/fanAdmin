package com.fan.manage.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletResponse;

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
		DecimalFormat df = new DecimalFormat("#0.00");
		File file = new File(fielPath);
		return df.format(Double.valueOf(file.length())/1024);
	}
	
	/**
	 * 读取文件到字节数组
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static byte[] toByteArray(String filePath) throws IOException {
		File f = new File(filePath);
		if(!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while((channel.read(byteBuffer))>0) {
				
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				channel.close();
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 下载
	 * @param response
	 * @param filePath	//文件完整路径(包括文件名和扩展名)
	 * @param fileName	//下载后看到的文件名
	 * @throws IOException 
	 */
	public static void fileDownload(final HttpServletResponse response, String filePath, String fileName) throws IOException {
		byte[] data = FileUtil.toByteArray(filePath);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");  
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);  
		outputStream.flush();  
		outputStream.close();
    	response.flushBuffer();
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
