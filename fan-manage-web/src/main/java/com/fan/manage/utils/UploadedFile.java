package com.fan.manage.utils;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 * @author hanxiaofan
 *
 */
public class UploadedFile implements Serializable{

	private static final long serialVersionUID = 72348L;
	
	private MultipartFile multipartFile;

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

}
