package com.fan.manage.utils;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;
/**
 * 生成二维码的实现类
 * @author hanxiaofan
 *
 */
public class TowDimensionCodeImage implements QRCodeImage {
	
	BufferedImage bufImg;
	
	public TowDimensionCodeImage(BufferedImage bufImg) {
		this.bufImg = bufImg;
	}

	@Override
	public int getHeight() {
		return bufImg.getHeight();
	}

	@Override
	public int getPixel(int arg0, int arg1) {
		return bufImg.getRGB(arg0, arg1);
	}

	@Override
	public int getWidth() {
		return bufImg.getWidth();
	}

}
