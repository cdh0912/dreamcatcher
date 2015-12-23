package com.dreamcatcher.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

public class ThumbnailImage {
	public static int createImage(String loadFile, String saveFile, int size) {

		int type = 0;
		File saveImage = new File(saveFile);

		// image 파일로부터 읽어오기
		RenderedOp renderedOp = JAI.create("fileload", loadFile); // 첫번쨰 인자값 :
																	// 옵션
		BufferedImage bufferedImage = renderedOp.getAsBufferedImage();

		int image_x = bufferedImage.getWidth();
		int image_y = bufferedImage.getHeight();

		int width = getWidth(image_x, image_y, size);
		int height = getHeight(image_x, image_y, size);
				
		BufferedImage thumbImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = thumbImage.createGraphics();
		graphics2d.drawImage(bufferedImage, 0, 0, width, height, null);
		try {
			ImageIO.write(thumbImage, "jpg", saveImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return type;

	}

	private static int getWidth(int image_x, int image_y, int size) {
		double width = 0;
		if (image_y > image_x) {
			double ratio = (double) image_x / image_y;
			width = (double) ratio * size;
		} else {
			width = size;
		}

		return (int) width;

	}

	private static int getHeight(int image_x, int image_y, int size) {
		double height = 0;
		if (image_x > image_y) {
			double ratio = (double) image_y / image_x;
			height = (double) ratio * size;
		} else {
			height = size;
		}

		return (int) height;

	}
	
	public static void main(String[] args) {
		int image_x = 768;
		int image_y = 1024;
		int size = 500;
		System.out.println("width : "+getWidth(image_x, image_y, size));
		System.out.println("height : "+getHeight(image_x, image_y, size));
	}

}
