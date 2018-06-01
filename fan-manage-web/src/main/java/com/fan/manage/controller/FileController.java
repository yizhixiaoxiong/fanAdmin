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
import common.utils.DeleteFiles;
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
			npd.put("DOWNLOAD", fileList.get(i).getString("DOWNLOAD"));					//备注
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
		pd.put("DOWNLOAD", "0");	//获取文件大小
		
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
			Iterator<String> iter=multiRequest.getFileNames();
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
	 * 删除文件
	 * (删除数据库，删除磁盘)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete() throws Exception {
		Map<String, String> map = new HashMap<String,String>();
		String info = "";
		PageData pd = this.getPageData();
		//获取路径
		pd = fileService.findById(pd);
		String FILEPATH = pd.getString("FILEPATH");
		if(FILEPATH == null && "".equals(FILEPATH)) {
			info = "数据库中的路径为空！";
		}
		try {
			//删除磁盘
			String path = basePath + File.separator+Const.FILEPATHFILE+FILEPATH;
			DeleteFiles.deleteFile(path);
			info = "success";
			fileService.delete(pd);	//删除数据
		} catch (Exception e) {
			e.printStackTrace();
			info = "failed";
		}
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	/**
	 * 批量删除
	 * (删除数据库，删除磁盘)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public String deleteAll() throws Exception {
		Map<String, String> map = new HashMap<String,String>();
		String info = "";
		PageData pd = this.getPageData();
		try {
			//获取id字符串
			String strIds = pd.getString("IDS");
			String[] ids = strIds.split(",");	//以','分割
			List<PageData> pdList = fileService.findListByIds(ids);	//获取file集合
			if(pdList != null) {
			for (PageData pageData : pdList) {
					String FILEPATH = pageData.getString("FILEPATH");	//获取路径
					String path = basePath + File.separator+Const.FILEPATHFILE+FILEPATH;
					DeleteFiles.deleteFile(path);		//单个删除磁盘上的文件
				}
			fileService.deleteAll(ids);
			info = "success";
			}else {
				info = "不存在该数据，亲检查数据库";
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	/**
	 * 下载
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/download")
	public void download(HttpServletResponse response) throws Exception {
		PageData pd = this.getPageData();
		pd = fileService.findById(pd);
		String DOWNLOAD = pd.getString("DOWNLOAD");	//获取下载次数
		String FILEPATH = pd.getString("FILEPATH");	//下载路径
		String fileName = pd.getString("NAME");		//获取文件名
		String extName = FILEPATH.substring(FILEPATH.lastIndexOf("."));
		DOWNLOAD = String.valueOf(Long.parseLong(DOWNLOAD)+1);	//下载次数+1
		pd.put("DOWNLOAD", DOWNLOAD);
		fileService.update(pd);		//保存对象
		//获取路径
		String filePath = basePath + File.separator+Const.FILEPATHFILE+FILEPATH;
		FileUtil.fileDownload(response, filePath, fileName+extName);
	}
	
	/**
	 * 跳转到PDF显示页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goViewPdf")
	public ModelAndView goViewPdf() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = fileService.findById(pd);
		mv.setViewName("information/file/filePDF");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 跳转到文本显示页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goViewTxt")
	public ModelAndView goViewTxt() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String encoding = pd.getString("encoding");	//用来转换格式
		pd = fileService.findById(pd);
		
		String FILEPATH = pd.getString("FILEPATH");
		String fileName = pd.getString("NAME");		//获取文件名
		String extName = FILEPATH.substring(FILEPATH.lastIndexOf("."));
		String filePath = basePath + File.separator+Const.FILEPATHFILE+FILEPATH+File.separator+fileName+extName;;
		String code = Tools.readTxtFileAll(filePath,encoding);
		
		pd.put("code", code);
		mv.setViewName("information/file/fileTxt");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 获取上传的文本类型(用于页面图标显示)
	 * @param extName	文件拓展名
	 * @return
	 */
	@SuppressWarnings("unused")
	public String getFileType(String extName) {
		String fileType = "file";
		int zindex1 = "java,php,jsp,html,css,txt,asp".indexOf(extName);
		if(zindex1 != -1) {
			fileType = "wenben";
		}
		int zindex2 = "jpg,gif,bmp,png,PNG".indexOf(extName);
		if(zindex2 != -1) {
			fileType = "tupian";
		}
		int zindex3 = "rar,zip,rar5".indexOf(extName);
		if(zindex3 != -1) {
			fileType = "yasuo";
		}
		int zindex4 = "doc,docx".indexOf(extName);
		if(zindex4 != -1) {
			fileType = "doc";
		}
		int zindex5 = "xls,xlsx".indexOf(extName);
		if(zindex5 != -1){
			fileType = "xls";		//xls文件类型
		}
		int zindex6 = "ppt,pptx".indexOf(extName);
		if(zindex6 != -1){
			fileType = "ppt";		//ppt文件类型
		}
		int zindex7 = "pdf".indexOf(extName);
		if(zindex7 != -1){
			fileType = "pdf";		//pdf文件类型
		}		
		return fileType;
	}
}
