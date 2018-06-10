package com.fan.manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.Tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fan.manage.pojo.Page;
import com.fan.manage.service.PictureService;
import com.fan.manage.utils.FileUpload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.utils.Const;
import common.utils.DeleteFiles;
import common.utils.PageData;
import common.utils.Tools;
import serviceUtils.Jurisdiction;

/**
 * 图片控制类
 * @author hanxiaofan
 *
 */
@RequestMapping("pictures")
@Controller
public class PicturesController extends BaseController{
	
	@Resource(name = "pictureService")
	private PictureService pictureService;
	@Value("${basePath}")
	private String basePath;	//图片上传基础路径
	
	/**
	 * 图片列表
	 * @param page	分页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> varList = pictureService.list(page);
		mv.addObject("varList", varList);
		mv.addObject("QX", Jurisdiction.getHC());
		mv.setViewName("information/pictures/picturesList");
		return mv;
	}
	
	/**
	 * 去新增页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("msg", "add");
		mv.setViewName("information/pictures/picturesAdd");
		return mv;
	}
	
	/**
	 * 新增(上传)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String add(MultipartFile file) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String fileName = "";
		PageData pd = new PageData();
		if(null != file && !file.isEmpty()){	//非空
			String filePath = basePath + File.separator+Const.FILEPATHIMG;	//E:/game/uploadFiles/uploadImgs/fileName
			fileName = FileUpload.fileUp(file, filePath, this.get32UUID());	//返回文件名
		}else {
			System.out.println("上传失败");
		}
		pd.put("PICTURES_ID", this.get32UUID());			//主键
		pd.put("TITLE", "图片");								//标题
		pd.put("NAME", fileName);							//文件名
		pd.put("PATH", fileName);				//路径
		pd.put("CREATETIME", Tools.dateToStr(new Date()));	//创建时间
		pd.put("MASTER_ID", "1");							//附属与
		pd.put("BZ", "图片管理处上传");							//备注
		pictureService.save(pd);
		
		map.put("result", "ok");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	/**
	 * 二进制显示图片
	 * @param imgPath 数据库中存放的相对路径
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/IoReadImage/{imgPath}" , method = RequestMethod.GET)
	public String IoReadImage(@PathVariable String imgPath,HttpServletRequest request,HttpServletResponse response) throws IOException {
		ServletOutputStream out = null;  
        FileInputStream ips = null;  
        
		try {
			String path = basePath + File.separator + Const.FILEPATHIMG+imgPath+".png";
			ips = new FileInputStream(new File(path));		//流
			response.setContentType("multipart/form-data");	//设置编码类型
			out = response.getOutputStream();
			//读取文件流
			int len = 0;
			byte[] buffer = new byte[1024*10];
			while((len = ips.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();  
            ips.close(); 
		}
		return null;
	}
	
	/**
	 * 去修改页
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//根据id读取图片
		pd = pictureService.findById(pd);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.setViewName("information/pictures/picturesEdit");
		return mv;
	}
	
	/**
	 * 更新
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(
							@RequestParam(value="tp",required=false) MultipartFile file,
							@RequestParam(value="TITLE",required=false) String TITLE,
							@RequestParam(value="PICTURES_ID",required=false) String PICTURES_ID,
							@RequestParam(value="tpz",required=false) String PATH,
							@RequestParam(value="MASTER_ID",required=false) String MASTER_ID,
							@RequestParam(value="BZ",required=false) String BZ) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String fileName = "";
		if(PATH == null) {
			PATH = "";
		}
		if(null != file && !file.isEmpty()){	//非空
			String filePath = basePath + File.separator+Const.FILEPATHIMG;	//E:/game/uploadFiles/uploadImgs/fileName
			fileName = FileUpload.fileUp(file, filePath, this.get32UUID());	//返回文件名
			pd.put("NAME", fileName);							//文件名
			pd.put("PATH", fileName);							//路径
		}else {
			pd.put("PATH", PATH);
		}
		pd.put("TITLE", TITLE);				//标题
		pd.put("PICTURES_ID", PICTURES_ID);	//主键
		pd.put("MASTER_ID", MASTER_ID);		//所属
		pd.put("BZ", BZ);					//备注
		pictureService.edit(pd);
		mv.addObject("success", "msg");
		mv.setViewName("saveResult");
		return mv;
	}
	
	/**
	 * 删除
	 * 参数：id和path
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete() throws Exception {
		Map<String, String> map = new HashMap<String,String>();
		String info = "";
		PageData pd = this.getPageData();
		String PATH = pd.getString("PATH");
		try {
			if(Tools.notEmpty(PATH.trim())) {
				String fielPath = basePath+File.separator+Const.FILEPATHIMG+PATH;
				DeleteFiles.deleteFile(fielPath);
			}
			if(PATH != null) {
				pictureService.delete(pd);
			}
			info = "success";
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
	 * 参数:ids(id字符串)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public String deleteAll() throws Exception {
		Map<String, String> map = new HashMap<String,String>();
		String info = "";
		PageData pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		List<PageData> pathList = new ArrayList<PageData>();
		String[] arrayIds = DATA_IDS.split(",");	//id数组
		try {
			pathList = pictureService.getAllById(arrayIds);	//根据id获取路径集合
			for (int i = 0; i < arrayIds.length; i++) {
				String PATH = pathList.get(i).getString("PATH");
				if(Tools.notEmpty(PATH.trim())) {	//获取路径
					String filePath = basePath + File.separator + Const.FILEPATHIMG+PATH;
					DeleteFiles.deleteFile(filePath);
				}
			}
			pictureService.deleteAll(arrayIds);
			info = "success";
		} catch (Exception e) {
			info = "failed";
			e.printStackTrace();
		}
		
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	/**
	 * 在更新的窗口中删除图片
	 * 参数:id和path
	 * 	id:数据库中删除
	 * 	path:磁盘删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deltp")
	@ResponseBody
	public String deltp() throws Exception {
		Map<String, String> map = new HashMap<String,String>();
		PageData pd = this.getPageData();
		String info = "";
		String path = pd.getString("PATH");
		try {
			if(Tools.notEmpty(path.trim())) {
				String filePath = basePath+File.separator+Const.FILEPATHIMG+path;
				DeleteFiles.deleteFile(filePath);		//磁盘删除
			}
			if(path != null) {
				pictureService.delTp(pd);	//这里是将PATH更新为""
			}
			info = "success";
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
	 * 去爬虫页
	 * @return
	 */
	@RequestMapping("/goImageCrawler")
	public ModelAndView goImageCrawler() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("information/pictures/imageCrawler");
		return mv;
	}
}
