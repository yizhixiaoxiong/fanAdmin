package com.fan.manage.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fan.manage.pojo.Page;
import com.fan.manage.service.FileService;
import com.fan.manage.utils.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.utils.Const;
import common.utils.PageData;
import common.utils.Tools;
import serviceUtils.Jurisdiction;

/**
 * 文件控制类
 * @author hanxiaofan
 *
 */
@RequestMapping("/fhfile")
@Controller
public class FileController extends BaseController{
	
	@Resource(name="fileService")
	private FileService fileService;
	@Value("${basePath}")
	private String basePath;	//图片上传基础路径
	/**
	 * 文件列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);		//分页
		List<PageData> fileList = fileService.list(page);	
		List<PageData>	nvarList = new ArrayList<PageData>();
		for(int i=0;fileList.size()>i;i++) {
			PageData npd = new PageData();
			String FILEPATH = fileList.get(i).getString("FILEPATH");	//获取文件名称
			String extName =  FILEPATH.substring(FILEPATH.lastIndexOf(".")+1);//文件拓展名
			String fileType = this.getFileType(extName);					//获取后缀名
			npd.put("fileType", fileType);								 	//用于文件图标
			npd.put("FHFILE_ID", fileList.get(i).getString("FHFILE_ID"));	//唯一ID	
			npd.put("NAME", fileList.get(i).getString("NAME"));				//文件名
			npd.put("FILEPATH", FILEPATH);									//文件名+扩展名
			npd.put("CTIME", fileList.get(i).getString("CTIME"));			//上传时间
			npd.put("OLDNAME", fileList.get(i).getString("OLDNAME"));			//源文件
			npd.put("USERNAME", fileList.get(i).getString("USERNAME"));		//用户名
			npd.put("DEPARTMENT_ID", fileList.get(i).getString("DEPARTMENT_ID"));//机构级别
			npd.put("FILESIZE", fileList.get(i).getString("FILESIZE"));		//文件大小
			npd.put("BZ", fileList.get(i).getString("BZ"));					//备注
			nvarList.add(npd);
		}
		mv.addObject("fileList", nvarList);
		mv.addObject("pd", pd);
		mv.setViewName("information/file/fileList");
		mv.addObject("QX",Jurisdiction.getHC());
		return mv;
	}
	
	/**
	 * 跳转新增
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("msg", "save");
		mv.setViewName("information/file/fileAdd");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 保存(文件)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public ModelAndView save(@RequestParam(value="NAME") String NAME,
							@RequestParam(value="FILEPATH") String FILEPATH,
							@RequestParam(value="BZ") String BZ) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String fielPath = basePath+File.separator+Const.FILEPATHFILE+FILEPATH;
		pd.put("FHFILE_ID", this.get32UUID());				//主键
		pd.put("NAME", NAME);
		pd.put("FILEPATH", FILEPATH);
		pd.put("OLDNAME", FILEPATH);						//原名暂时是文件名
		pd.put("BZ", BZ);
		pd.put("CTIME", Tools.dateToStr(new Date()));		//时间
		pd.put("USERNAME", Jurisdiction.getUsername());		//获取上传者
		pd.put("FILESIZE", FileUtil.getFilesize(fielPath));	//获取文件大小
		
		fileService.save(pd);		
		mv.addObject("msg","success");
		mv.setViewName("saveResult");
		return mv;
	}
	
	/**
	 * 上传
	 * @param request
	 * @param response
	 * @throws IllegalStateException 
	 * @throws IOException 
	 */
	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFile(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
		 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		 Map<String, String> map = new HashMap<String,String>();
		 String fileName = "";	//文件名
		 if(multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			//获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext()) {
            	 //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null) {
                	fileName = file.getOriginalFilename();
                	String path = basePath+File.separator+Const.FILEPATHFILE+fileName;
                	file.transferTo(new File(path));
                }
            }
		 }
		 map.put("name", fileName);
		 ObjectMapper mapper = new ObjectMapper();
		 String json = mapper.writeValueAsString(map);
		 return json;
	}
	
	/**
	 * 获取上传的文本类型(用于页面图标显示)
	 * @param fileName	文件拓展名
	 * @return
	 */
	@SuppressWarnings("unused")
	public String getFileType(String fileName) {
		String fileType = "file";
		int zindex1 = "java,php,jsp,html,css,txt,asp".indexOf(fileName);
		if(zindex1 != -1) {
			fileType = "wenben";
		}
		int zindex2 = "jpg,gif,bmp,png".indexOf(fileName);
		if(zindex2 != -1) {
			fileType = "tupian";
		}
		int zindex3 = "rar,zip,rar5".indexOf(fileName);
		if(zindex3 != -1) {
			fileType = "yasuo";
		}
		int zindex4 = "doc,docx".indexOf(fileName);
		if(zindex4 != -1) {
			fileType = "doc";
		}
		int zindex5 = "xls,xlsx".indexOf(fileName);
		if(zindex5 != -1){
			fileType = "xls";		//xls文件类型
		}
		int zindex6 = "ppt,pptx".indexOf(fileName);
		if(zindex6 != -1){
			fileType = "ppt";		//ppt文件类型
		}
		int zindex7 = "pdf".indexOf(fileName);
		if(zindex7 != -1){
			fileType = "pdf";		//pdf文件类型
		}		
		return fileType;
	}
}
