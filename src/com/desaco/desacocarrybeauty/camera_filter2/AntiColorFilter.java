package com.desaco.desacocarrybeauty.camera_filter2;

import com.desaco.desacocarrybeauty.common_interface.ImageFilterInterface;
import com.desaco.desacocarrybeauty.javabean.ImageData;

import android.graphics.Bitmap;

// http://blog.csdn.net/real_myth/article/details/50925986
public class AntiColorFilter implements ImageFilterInterface {
	private ImageData image = null; // 图片信息类

	public AntiColorFilter(Bitmap bmp) {
		image = new ImageData(bmp);
	}

	public ImageData imageProcess() {
		int width = image.getWidth();
		int height = image.getHeight();
		int R, G, B, pixel;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				R = image.getRComponent(x, y); // 获取RGB三原色
				G = image.getGComponent(x, y);
				B = image.getBComponent(x, y);

				R = 255 - R;
				B = 255 - B;
				G = 255 - G;

				image.setPixelColor(x, y, R, G, B);
			} // x
		} // y
		return image;
	}
}
