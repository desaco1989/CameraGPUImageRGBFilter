//package com.desaco.desacocarrybeauty.camera_filter2;
//
//import com.desaco.desacocarrybeauty.camera_filter2.lomo.Gradient;
//import com.desaco.desacocarrybeauty.common_interface.ImageFilterInterface;
//import com.desaco.desacocarrybeauty.javabean.ImageData;
//
///**
// * android 图像处理滤镜系列之九  电影特效- http://www.eoeandroid.com/thread-199506-1-1.html
// * @author desaco
// *
// */
//public class FilmFilter implements ImageFilterInterface {
//	private GradientFilter gradient;
//	private SaturationModifyFilter saturationFx;
//	ImageData imageData;
//
//	public FilmFilter(Bitmap bmp, float angle) {
//		imageData = new ImageData(bmp);
//		gradient = new GradientFilter(imageData);
//		gradient.Gradient = Gradient.Fade();
//		gradient.OriginAngleDegree = angle;
//	}
//
//	public ImageData imageProcess() {
//		// TODO Auto-generated method stub
//		ImageData clone = imageData.clone();
//		imageData = gradient.imageProcess();
//		ImageBlender blender = new ImageBlender();
//		blender.Mode = BlendMode.Multiply;
//		saturationFx = new SaturationModifyFilter(blender.Blend(clone, imageData));
//		saturationFx.SaturationFactor = -0.6f;
//		return saturationFx.imageProcess();
//	}
//}
