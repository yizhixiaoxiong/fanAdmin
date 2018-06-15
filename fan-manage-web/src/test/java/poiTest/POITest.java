package poiTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.fan.manage.service.UserService;

import common.utils.POIUtils;
import common.utils.PageData;

public class POITest {
	
	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 生成Excel(.xls)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPoiXls() throws IOException {
		// 1.创建工作薄
		Workbook wb = new HSSFWorkbook();
		// 2.创建工作表Sheet
		Sheet sheet = wb.createSheet();
		// 3.创建行对象row,下表从0开始
		Row row = sheet.createRow(3);
		// 4.设置单元格对象，从0记数
		Cell cell = row.createCell(3);
		// 5.设置单元格内容
		cell.setCellValue("这是一个POI测试");
		// 6.设置单元格的样式
		CellStyle cellStyle = wb.createCellStyle();
		Font font = wb.createFont(); // 创建字体对象
		font.setFontName("微软雅黑"); // 设置字体名称
		font.setFontHeightInPoints((short) 48);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		// 保存关闭流
		OutputStream os = new FileOutputStream("E:/测试.xls");
	}

	/**
	 * 导出03
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void readExtExcel03() throws IOException {
		Map<String, Object> map = new HashMap<>();
		//标题头
		List<String> titles = new ArrayList<String>();
		titles.add("用户名"); 	// 1
		titles.add("编号"); 		// 2
		titles.add("姓名"); 		// 3
		titles.add("职位"); 		// 4
		titles.add("手机"); 		// 5
		titles.add("邮箱"); 		// 6
		titles.add("最近登录"); 	// 7
		titles.add("上次登录IP"); // 8
		HSSFWorkbook workbook = new HSSFWorkbook(); // 工作簿
		Sheet sheet = null;
		Cell cell = null;
		sheet = workbook.createSheet("03第一个sheet1");
		int len = titles.size();
		Row row = sheet.createRow(0);
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 单元格居中
		Font headerFont = workbook.createFont(); // 标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		headerFont.setFontHeightInPoints((short) 11);
		headerStyle.setFont(headerFont);
		short width = 20, height = 25 * 20;	//高宽
		sheet.setDefaultColumnWidth(width);
		for(int i=0; i<len; i++){ //设置标题
			String title = titles.get(i);
			cell = row.createCell(i);
			cell.setCellValue(title);
			cell.setCellStyle(headerStyle);	//设置样式
		}
		
		OutputStream os = new FileOutputStream("E:/写入用户列表.xls");
		workbook.write(os);
		os.close();
	}

	/**
	 * 读excel(03版本)
	 * 
	 * @throws IOException
	 */
//	@Test
//	public void readExcel03() throws IOException {
//		FileInputStream inputStream = new FileInputStream(new File("E:/测试.xls"));
//		// 读取工作簿
//		HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
//		// 读取工作表
//		HSSFSheet sheet = workBook.getSheetAt(0);
//		// 读取行
//		HSSFRow row = sheet.getRow(3);
//		// 读取单元格
//		HSSFCell cell = row.getCell(3);
//		String value = cell.getStringCellValue();
//		System.out.println(value);
//		inputStream.close();
//		workBook.close();// 最后记得关闭工作簿
//	}

	/**
	 * 生成Excel(xlsx07)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPoiXlsx() throws IOException {
		// 创建工作簿
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 创建工作表
		XSSFSheet sheet = workBook.createSheet("helloWorld");
		// 创建行
		XSSFRow row = sheet.createRow(2);
		// 创建单元格，操作第三行第三列
		XSSFCell cell = row.createCell(2, CellType.STRING);
		cell.setCellValue("这是一个POI测试");
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		XSSFFont font = workBook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeight(24);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		FileOutputStream os = new FileOutputStream(new File("E://测试07.xlsx"));
		workBook.write(os);
		os.close();
		workBook.close();// 记得关闭工作簿
	}
	
	/**
	 * 生成Excel(xlsx07)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPoiXlsx07() throws IOException {
		// 创建工作簿
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 创建工作表
		XSSFSheet sheet = workBook.createSheet("helloWorld");
		// 创建行
		XSSFRow row = sheet.createRow(0);
		// 创建单元格，操作第三行第三列
		XSSFCell cell = null;
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		XSSFFont font = workBook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeight(14);
		cellStyle.setFont(font);
		short width = 14, height = 25 * 20;	//高宽
		sheet.setDefaultColumnWidth(width);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	//居中
		//标题头
		List<String> titles = new ArrayList<String>();
		titles.add("用户名"); 	// 1
		titles.add("编号"); 		// 2
		titles.add("姓名"); 		// 3
		titles.add("职位"); 		// 4
		titles.add("手机"); 		// 5
		titles.add("邮箱"); 		// 6
		titles.add("最近登录"); 	// 7
		titles.add("上次登录IP"); // 8
		int len = titles.size();
		for (int i = 0; i < len; i++) {
			String title = titles.get(i);
			cell = row.createCell(i);
			cell.setCellValue(title);
			cell.setCellStyle(cellStyle);
		}
		FileOutputStream os = new FileOutputStream(new File("E://测试.xlsx"));
		workBook.write(os);
		os.close();
		workBook.close();// 记得关闭工作簿
	}

	/**
	 * 导出Excel(07)
	 * 
	 * @throws IOException
	 */
	@Test
	public void readExcel07() throws IOException {
		FileInputStream inputStream = new FileInputStream(new File("E://测试07.xlsx"));
		// 读取工作簿
		XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
		// 读取工作表
		XSSFSheet sheet = workBook.getSheetAt(0);
		// 读取行
		XSSFRow row = sheet.getRow(2); // 2行：index默认0
		// 读取单元格
		XSSFCell cell = row.getCell(2); // 2列：index默认 0
		String value = cell.getStringCellValue();
		System.out.println(value);
		inputStream.close();// 关闭工作簿
		workBook.close();
	}
	
}
