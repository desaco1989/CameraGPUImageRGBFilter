package com.desaco.desacocarrybeauty.camera_filter2.light_edge;

import com.desaco.desacocarrybeauty.common_interface.ImageFilterInterface;
import com.desaco.desacocarrybeauty.javabean.ImageData;

import android.graphics.Bitmap;

/**
 * http://www.eoeandroid.com/thread-177493-1-1.html?_dsign=9bd044fe
 * 
 * 柔化美白特效，该特效由高斯柔化+高亮对比度特效组合而成，整个处理流程稍微需要点时间，看懂源码的童鞋可以自己进行优化。
 *  图像处理滤镜系列之四 柔化美白特效
 */
public class SoftGlowFilter implements ImageFilterInterface {

	/**
	 * 高亮对比度特效
	 */
	BrightContrastFilter contrastFx;
	GaussianBlurFilter gaussianBlurFx;

	private ImageData image = null;

	public SoftGlowFilter(Bitmap bmp) {
		image = new ImageData(bmp);
	}

	public SoftGlowFilter(Bitmap bmp, int nSigma, float nBrightness, float nContrast) {
		gaussianBlurFx = new GaussianBlurFilter(bmp);
		gaussianBlurFx.Sigma = nSigma;
		image = gaussianBlurFx.imageProcess(); // 柔化处理
		contrastFx = new BrightContrastFilter(image);
		contrastFx.BrightnessFactor = nBrightness;
		contrastFx.ContrastFactor = nContrast;

		image = contrastFx.imageProcess(); // 高亮对比度
	}

	public ImageData imageProcess() {
		int width = image.getWidth();
		int height = image.getHeight();
		ImageData clone = image.clone();
		int old_r, old_g, old_b, r, g, b;
		for (int x = 0; x < (width - 1); x++) {
			for (int y = 0; y < (height - 1); y++) {
				old_r = clone.getRComponent(x, y);
				old_g = clone.getGComponent(x, y);
				old_b = clone.getBComponent(x, y);

				r = 255 - (255 - old_r) * (255 - image.getRComponent(x, y)) / 255;
				g = 255 - (255 - old_g) * (255 - image.getGComponent(x, y)) / 255;
				b = 255 - (255 - old_b) * (255 - image.getBComponent(x, y)) / 255;
				image.setPixelColor(x, y, r, g, b);
			}
		}
		return image;
	}

}
