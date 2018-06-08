package com.fan.manage.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import common.utils.Const;

/**
 * 验证码生成器
 * @author hanxiaofan
 *
 */
@Controller
@RequestMapping("/code")
public class SetCodeController {
	
	/**
	 * 生成图片放入session中
	 * @param response
	 */
	@RequestMapping
	public void generate(HttpServletResponse response){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String code = drawImg(output);
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		session.setAttribute(Const.SESSION_SECURITY_CODE, code);	//放入session
		
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 画一个图片
	 * @param output
	 * @return
	 */
	private String drawImg(ByteArrayOutputStream output){
		String code = "";
		for (int i = 0; i < 4; i++) {
			code += randomChar();
		}
		
		int width = 70;
		int height = 25;
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Times New Roman",Font.PLAIN,20);
		Graphics2D g = bi.createGraphics();
		g.setFont(font);	//设置字体
		Color color = new Color(66,2,82);
		g.setColor(color);
		g.setBackground(new Color(226,226,240));
		//用背景色填充指定矩形达到清除该矩形的效果，相当一个“橡皮擦”
		g.clearRect(0, 0, width, height); 
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int)x, (int)baseY);
		g.dispose();
		try {
			ImageIO.write(bi, "jpg", output);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return code;
	}
	
	
	/**
	 * 随机生成一个“ABCDEFGHJKLMNPRSTUVWXYZ0123456789”中的字符
	 * API:	Random.nextInt(n),随机生成一个0-n的正整数
	 * 		String.charAt(index),取String下标为index的字符
	 * @return
	 */
	private char randomChar(){
		Random r = new Random();
		String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
		return s.charAt(r.nextInt(s.length()));
	}
}
