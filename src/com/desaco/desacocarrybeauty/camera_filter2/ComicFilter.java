package com.desaco.desacocarrybeauty.camera_filter2;

import com.desaco.desacocarrybeauty.common_interface.ImageFilterInterface;
import com.desaco.desacocarrybeauty.javabean.ImageData;
import com.desaco.desacocarrybeauty.utils.ImageUtil;

import android.graphics.Bitmap;

/**
 * android 图像处理滤镜系列之三  连环画特效- http://www.eoeandroid.com/thread-177210-1-1.html?_dsign=abcda611
 * @author desaco
 *
 */
public class ComicFilter implements ImageFilterInterface {

	private ImageData image = null;

	public ComicFilter(Bitmap bmp) {
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

				// R = |g – b + g + r| * r / 256;
				pixel = G - B + G + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				R = pixel;

				// G = |b – g + b + r| * r / 256;
				pixel = B - G + B + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				// B = |b – g + b + r| * g / 256;
				pixel = B - G + B + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * G / 256;
				if (pixel > 255)
					pixel = 255;
				B = pixel;
				image.setPixelColor(x, y, R, G, B);
			}
		}
		Bitmap bitmap = image.getDstBitmap();
		bitmap = ImageUtil.toGrayscale(bitmap); // 图片灰度化处理
		image = new ImageData(bitmap);
		return image;
	}

}
