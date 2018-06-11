package com.fan.manage.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传下载文件
 * @author hanxiaofan
 *
 */
public class FileUpload {
	
	/**
	 * 上传文件
	 * @param file		上传的文件
	 * @param filePath	上传的路径
	 * @param fileName	文件的名字
	 * @return 返回 图片名+后缀
	 */
	public static String fileUp(MultipartFile file, String filePath, String fileName) {
		String extName = ""; // 后缀
		try {
			if (file.getOriginalFilename().indexOf(".") >= 0) { // 如果文件全名的存在'.'
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'));// 则以下标分割，取其后面。
			}
			copyFile(file.getInputStream(), filePath, fileName + extName).replaceAll("-", ""); //写入文件
		} catch (IOException e) {
			System.out.println(e);
		}
		return fileName + extName;// 名字+后缀
	}
	
	
	/**
	 * 写文件到当前目录的upload目录中
	 * @param in  	输入流
	 * @param dir 	路径
	 * @param realName 文件名
	 * @return 返回图片名
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private static String copyFile(InputStream in,String dir,String fileName) throws IOException{
		File file = mkdirsPath(dir,fileName);
		FileUtils.copyInputStreamToFile(in, file);	//写入文件
		return fileName;
	}
	
	
	/**
	 * @param path 路径
	 * @param fileName	文件名
	 * @return 返回文件
	 * @throws IOException 
	 * @throws Exception
	 */
	public static File mkdirsPath(String path,String fileName) throws IOException{
		File file = new File(path,fileName);
		if(!file.exists()){	//文件不存在
			if(!file.getParentFile().exists()){	//是否存在父文件
				file.getParentFile().mkdirs();
			}
			file.createNewFile();			//创建文件
		}
		return file;
	}
	
	/**
	 * 下载网络图片上传到服务器上
	 * @param httpUrl 图片网络地址
	 * @param filePath 图片保管路径
	 * @param myFileName 图片文件名(null时用网络图片)
	 * @return 返回图片名称
	 */
	public static String getHtmlPicture(String httpUrl, String filePath , String myFileName) {
		
		URL url;				//定义URL对象url
		BufferedInputStream in;	//定义输入字节缓冲流对象in
		FileOutputStream file;	//定义文件输出流对象file
		
		try {
			String fileName = null == myFileName ? httpUrl.substring(httpUrl.lastIndexOf("/")).replace("/", "")
					: myFileName;		// 图片文件名(null时用网络图片原名)		
			url = new URL(httpUrl);		//初始化url对象
			in = new BufferedInputStream(url.openStream());
			file = new FileOutputStream(mkdirsPath(filePath,fileName));
			int t;
			while((t = in.read()) != -1) {
				file.write(t);
			}
			file.close();
			in.close();
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
