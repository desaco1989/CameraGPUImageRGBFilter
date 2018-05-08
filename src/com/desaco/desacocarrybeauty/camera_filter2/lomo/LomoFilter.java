package com.desaco.desacocarrybeauty.camera_filter2.lomo;

import com.desaco.desacocarrybeauty.camera_filter2.light_edge.BrightContrastFilter;
import com.desaco.desacocarrybeauty.common_interface.ImageFilterInterface;
import com.desaco.desacocarrybeauty.javabean.ImageData;

import android.graphics.Bitmap;

/**
 * 图像处理滤镜系列之八  LOMO特效- http://www.eoeandroid.com/thread-179152-1-1.html?_dsign=2a770791
 * @author desaco
 *
 */
public class LomoFilter implements ImageFilterInterface {

	private BrightContrastFilter contrastFx;
	private GradientMapFilter gradientMapFx;
	private ImageBlender blender = new ImageBlender();
	private VignetteFilter vignetteFX;
	private NoiseFilter noiseFx;

	private ImageData image;

	public LomoFilter(Bitmap bm) {
		image = new ImageData(bm);
		contrastFx = new BrightContrastFilter(image);
		contrastFx.BrightnessFactor = 0.05f;
		contrastFx.ContrastFactor = 0.5f;

		blender.Mixture = 0.5f;
		blender.Mode = ImageBlender.BlendMode.Multiply;

		vignetteFX = new VignetteFilter(image);
		vignetteFX.Size = 0.6f;

	}

	public ImageData imageProcess() {
		// TODO Auto-generated method stub
		ImageData tempImg = contrastFx.imageProcess();
		noiseFx = new NoiseFilter(tempImg);
		noiseFx.Intensity = 0.02f;
		tempImg = noiseFx.imageProcess();
		gradientMapFx = new GradientMapFilter(tempImg);
		image = gradientMapFx.imageProcess();
		image = blender.Blend(image, tempImg);
		vignetteFX = new VignetteFilter(image);
		vignetteFX.Size = 0.6f;
		image = vignetteFX.imageProcess();
		return image;
	}

}
