package com.desaco.desacocarrybeauty.camera_filter2;

import com.desaco.desacocarrybeauty.common_interface.ImageFilterInterface;
import com.desaco.desacocarrybeauty.javabean.ImageData;

import android.graphics.Bitmap;

/**
 * 图像处理滤镜系列之七  缩放模糊- http://www.eoeandroid.com/thread-178648-1-1.html?_dsign=547400fa
 * @author desaco
 *
 */
public class ZoomBlurFilter implements ImageFilterInterface {

	private ImageData image = null;

	int m_length;
	double m_offset_x;
	double m_offset_y;
	int m_fcx, m_fcy;
	final int RADIUS_LENGTH = 64;

	public ZoomBlurFilter(Bitmap bmp, int nLength) {
		m_length = nLength;
		m_offset_x = 0f;
		m_offset_y = 0f;
		image = new ImageData(bmp);
	}

	public ZoomBlurFilter(Bitmap bmp, int nLength, double offset_x, double offset_y) {
		m_length = (nLength >= 1) ? nLength : 1;
		m_offset_x = (offset_x > 2.0 ? 2.0 : (offset_x < -2.0 ? 0 : offset_x));
		m_offset_y = (offset_y > 2.0 ? 2.0 : (offset_y < -2.0 ? 0 : offset_y));
		image = new ImageData(bmp);
	}

	public ImageData imageProcess() {
		int width = image.getWidth();
		int height = image.getHeight();
		m_fcx = (int) (width * m_offset_x * 32768.0) + (width * 32768);
		m_fcy = (int) (height * m_offset_y * 32768.0) + (height * 32768);
		final int ta = 255;
		ImageData clone = image.clone();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int sr = 0, sg = 0, sb = 0, sa = 0;
				sr = clone.getRComponent(x, y) * ta;
				sg = clone.getGComponent(x, y) * ta;
				sb = clone.getBComponent(x, y) * ta;
				sa += ta;
				int fx = (x * 65536) - m_fcx;
				int fy = (y * 65536) - m_fcy;
				for (int i = 0; i < RADIUS_LENGTH; i++) {
					fx = fx - (fx / 16) * m_length / 1024;
					fy = fy - (fy / 16) * m_length / 1024;

					int u = (fx + m_fcx + 32768) / 65536;
					int v = (fy + m_fcy + 32768) / 65536;
					if (u >= 0 && u < width && v >= 0 && v < height) {
						sr += clone.getRComponent(u, v) * ta;
						sg += clone.getGComponent(u, v) * ta;
						sb += clone.getBComponent(u, v) * ta;
						sa += ta;
					}
				}
				image.setPixelColor(x, y, image.safeColor(sr / sa), image.safeColor(sg / sa), image.safeColor(sb / sa));
			}
		}
		return image;
	}
}
