package com.desaco.desacocarrybeauty.camera_filter2;

import com.desaco.desacocarrybeauty.common_interface.ImageFilterInterface;
import com.desaco.desacocarrybeauty.javabean.ImageData;

import android.graphics.Bitmap;

/**
 * 图像处理滤镜系列之五  照亮边缘特效- http://www.eoeandroid.com/thread-177806-1-1.html?_dsign=da790cb1
 * @author desaco
 *
 */
public class GlowingEdgeFilter implements ImageFilterInterface {
	private ImageData image = null;

	public GlowingEdgeFilter(Bitmap bmp) {
		image = new ImageData(bmp);
	}

	public ImageData imageProcess() {
		int width = image.getWidth();
		int height = image.getHeight();
		// 图像实际处理区域
		// 不考虑最右 1 列，和最下 1 行
		int rectTop = 0;
		int rectBottom = height - 1;
		int rectLeft = 0;
		int rectRight = width - 1;
		int pixel;

		// R = p[2];
		// G = p[1];
		// B = p[0];
		int R, G, B;
		for (int y = rectTop; y < rectBottom; y++) {
			for (int x = rectLeft; x < rectRight; x++) {
				{
					pixel = (int) (Math.pow((image.getBComponent(x, y) - image.getBComponent(x, y, width)), 2)
							+ Math.pow((image.getBComponent(x, y) - image.getBComponent(x, y, 1)), 2));
					pixel = (int) (Math.sqrt(pixel) * 2);

					if (pixel < 0)
						pixel = 0;
					if (pixel > 255)
						pixel = 255;

					B = pixel;
				}
				{
					pixel = (int) (Math.pow((image.getGComponent(x, y) - image.getGComponent(x, y, width)), 2)
							+ Math.pow((image.getGComponent(x, y) - image.getGComponent(x, y, 1)), 2));
					pixel = (int) (Math.sqrt(pixel) * 2);

					if (pixel < 0)
						pixel = 0;
					if (pixel > 255)
						pixel = 255;

					G = pixel;
				}
				{
					pixel = (int) (Math.pow((image.getRComponent(x, y) - image.getRComponent(x, y, width)), 2)
							+ Math.pow((image.getRComponent(x, y) - image.getRComponent(x, y, 1)), 2));
					pixel = (int) (Math.sqrt(pixel) * 2);

					if (pixel < 0)
						pixel = 0;
					if (pixel > 255)
						pixel = 255;

					R = pixel;
				}

				image.setPixelColor(x, y, R, G, B);
			} // x
		} // y

		return image;
	}
}
