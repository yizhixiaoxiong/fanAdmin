package com.fan.manage.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fan.manage.utils.TowCodeUtils;

import common.utils.PageData;

/**
 * 系统工具
 * @author hanxiaofan
 *
 */
@Controller
@RequestMapping("tool")
public class ToolController extends BaseController{
	
	/**
	 * 进入二维码页面
	 * @return
	 */
	@RequestMapping("/goTwoDimensionCode")
	public ModelAndView goTwoDimensionCode(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.setViewName("system/tools/twoDimenisonCode");
		return mv;
	}
	
	/**
	 * 使用QRCode生成二维码
	 * 参数：
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping("/createRQCode")
	@ResponseBody
	public String createQRCode() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success", encoderImgId = this.get32UUID()+".png"; //encoderImgId此处二维码的图片名
		String content = pd.getString("content");	
		if(content == null){
			errInfo = "error";
		}else{
			HttpServletRequest request = this.getRequest();
			String path = request.getSession().getServletContext().getRealPath("")+File.separator+"uploadFiles"+File.separator+"twoDimensionCode"+File.separator+encoderImgId;
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			TowCodeUtils.encoderORCode(content, path, "png", 7);
		}
		map.put("result", errInfo);
		map.put("encoderImgId", encoderImgId);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	/**
	 * 使用Zxing生成二维码
	 * 参数：
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping("/createZxing")
	@ResponseBody
	public String createZxing() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success", encoderImgId = this.get32UUID()+".png"; //encoderImgId此处二维码的图片名
		String content = pd.getString("content");	
		if(content == null){
			errInfo = "error";
		}else{
			HttpServletRequest request = this.getRequest();
			String path = request.getSession().getServletContext().getRealPath("")+File.separator+"uploadFiles"+File.separator+"twoDimensionCode"+File.separator+encoderImgId;
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			TowCodeUtils.encoderZxingCode(content, "png", 300, path);
		}
		map.put("result", errInfo);
		map.put("encoderImgId", encoderImgId);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	/**
	 * 上传逻辑
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	@RequestMapping("/fileUpload")
	public void uploadFile(HttpServletRequest request,
            HttpServletResponse response) throws IOException{
		 String savePath = request.getSession().getServletContext().getRealPath("");
		 savePath = savePath + "/upload/";
		 File f1 = new File(savePath);
		 System.out.println(savePath);
		 //如果文件不存在,就新建一个
		 if (!f1.exists()) {
			 f1.mkdirs();
		 }
		 //这个是文件上传需要的类,具体去百度看看,现在只管使用就好
		 DiskFileItemFactory fac = new DiskFileItemFactory();
		 ServletFileUpload upload = new ServletFileUpload(fac);
		 upload.setHeaderEncoding("utf-8");
		 List fileList = null;
		 try {
	            fileList = upload.parseRequest(request);
	        } catch (FileUploadException ex) {
	            return;
	        }
		 //迭代器,搜索前端发送过来的文件
		 Iterator<FileItem> it = fileList.iterator();
		 String name = "";
		 String extName = "";
		 while (it.hasNext()) {
		 FileItem item = it.next();
		 //判断该表单项是否是普通类型
		 	if (!item.isFormField()) {
		 		name = item.getName();
		 		long size = item.getSize();
		 		String type = item.getContentType();
		 		System.out.println(size + " " + type);
		 		if (name == null || name.trim().equals("")) {
		 			continue;
		 		}
		 		// 扩展名格式： extName就是文件的后缀,例如 .txt
		 		if (name.lastIndexOf(".") >= 0) {
		 			extName = name.substring(name.lastIndexOf("."));
		 		}
		 		File file = null;
		 		do {
		 			// 生成文件名：
		 			name = UUID.randomUUID().toString();
		 			file = new File(savePath + name + extName);
		 		} while (file.exists());
		 		File saveFile = new File(savePath + name + extName);
		 		try {
		 			item.write(saveFile);
		 		} catch (Exception e) {
		 			e.printStackTrace();
		 		}
		 	}
		 }
		 System.out.println("上传成功");
	}
	
	
	/**
	 * 解析QRCode二维码
	 * @return
	 * @throws IOException 
	 */
	/*@RequestMapping("")
	@ResponseBody
	public String readQRCode() throws IOException{
		Map<String, String> map = new HashMap<String, String>();
		String info = "";
		//获取路径
		String imageFile = "";
		String decodedData = "";
		try {
			decodedData = TowCodeUtils.readderORCode(imageFile);
			info = "success";
		} catch (Exception e) {
			e.printStackTrace();
			info = "failed";
		}
		map.put("decodedData", decodedData);
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		String json =  mapper.writeValueAsString(map);
		return json;
	}*/
}
