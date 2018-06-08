package com.fan.manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import serviceUtils.Jurisdiction;

import com.fan.manage.pojo.Page;
import com.fan.manage.service.PictureService;
import com.fan.manage.utils.FileUpload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.qrcode.decoder.Mode;

import common.utils.Const;
import common.utils.DateUtil;
import common.utils.PageData;
import common.utils.Tools;

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
		String  ffile = DateUtil.getDays(), fileName = "";
		PageData pd = new PageData();
		if(null != file && !file.isEmpty()){	//非空
			String filePath = basePath + File.separator+Const.FILEPATHIMG + ffile;	//E:/game/uploadFiles/uploadImgs/ffile
			fileName = FileUpload.fileUp(file, filePath, this.get32UUID());	//返回文件名
		}else {
			System.out.println("上传失败");
		}
		pd.put("PICTURES_ID", this.get32UUID());			//主键
		pd.put("TITLE", "图片");								//标题
		pd.put("NAME", fileName);							//文件名
		pd.put("PATH", ffile + "/" + fileName);				//路径
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
	@RequestMapping(value = "/IoReadImage/20180608/{imgPath}" , method = RequestMethod.GET)
	public String IoReadImage(@PathVariable String imgPath,HttpServletRequest request,HttpServletResponse response) throws IOException {
		ServletOutputStream out = null;  
        FileInputStream ips = null;  
        
		try {
			String path = basePath + File.separator + Const.FILEPATHIMG +"20180608/"+imgPath+".png";
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
	@RequestMapping("/toEdit")
	public ModelAndView toEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = pictureService.findById(pd);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.setViewName("information/pictures/picturesEdit");
		return mv;
	}
	
	/**
	 * 更新
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit() {
		
		return null;
	}
	
}
