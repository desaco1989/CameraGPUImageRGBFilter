package com.desaco.desacocarrybeauty.picture_widget_utils;

import com.desaco.desacocarrybeauty.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

/**
 * 给图片添加边框（上）- https://blog.csdn.net/sjf0115/article/details/7267056 给图片添加边框（中）-
 * https://blog.csdn.net/sjf0115/article/details/7267043 给图片添加边框（下）－图片叠加-
 * https://blog.csdn.net/sjf0115/article/details/7267036
 * 
 * @author desaco
 *
 */
public class PictureFrameUtils {
	/**
	 * 图片与边框组合
	 * 
	 * @param bm
	 *            原图片
	 * @param res
	 *            边框资源
	 * @return
	 */
	private Bitmap combinateFrame(Context context, Bitmap bm, int[] res) {
		Bitmap bmp = decodeBitmap(context, res[0]);
		// 边框的宽高
		final int smallW = bmp.getWidth();
		final int smallH = bmp.getHeight();

		// 原图片的宽高
		final int bigW = bm.getWidth();
		final int bigH = bm.getHeight();

		int wCount = (int) Math.ceil(bigW * 1.0 / smallW);
		int hCount = (int) Math.ceil(bigH * 1.0 / smallH);

		// 组合后图片的宽高
		int newW = (wCount + 2) * smallW;
		int newH = (hCount + 2) * smallH;

		// 重新定义大小
		Bitmap newBitmap = Bitmap.createBitmap(newW, newH, Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		Paint p = new Paint();
		p.setColor(Color.TRANSPARENT);
		canvas.drawRect(new Rect(0, 0, newW, newH), p);

		Rect rect = new Rect(smallW, smallH, newW - smallW, newH - smallH);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawRect(rect, paint);

		// 绘原图
		canvas.drawBitmap(bm, (newW - bigW - 2 * smallW) / 2 + smallW, (newH - bigH - 2 * smallH) / 2 + smallH, null);
		// 绘边框
		// 绘四个角
		int startW = newW - smallW;
		int startH = newH - smallH;
		Bitmap leftTopBm = decodeBitmap(context, res[0]); // 左上角
		Bitmap leftBottomBm = decodeBitmap(context, res[2]); // 左下角
		Bitmap rightBottomBm = decodeBitmap(context, res[4]); // 右下角
		Bitmap rightTopBm = decodeBitmap(context, res[6]); // 右上角

		canvas.drawBitmap(leftTopBm, 0, 0, null);
		canvas.drawBitmap(leftBottomBm, 0, startH, null);
		canvas.drawBitmap(rightBottomBm, startW, startH, null);
		canvas.drawBitmap(rightTopBm, startW, 0, null);

		leftTopBm.recycle();
		leftTopBm = null;
		leftBottomBm.recycle();
		leftBottomBm = null;
		rightBottomBm.recycle();
		rightBottomBm = null;
		rightTopBm.recycle();
		rightTopBm = null;

		// 绘左右边框
		Bitmap leftBm = decodeBitmap(context, res[1]);
		Bitmap rightBm = decodeBitmap(context, res[5]);
		for (int i = 0, length = hCount; i < length; i++) {
			int h = smallH * (i + 1);
			canvas.drawBitmap(leftBm, 0, h, null);
			canvas.drawBitmap(rightBm, startW, h, null);
		}

		leftBm.recycle();
		leftBm = null;
		rightBm.recycle();
		rightBm = null;

		// 绘上下边框
		Bitmap bottomBm = decodeBitmap(context, res[3]);
		Bitmap topBm = decodeBitmap(context, res[7]);
		for (int i = 0, length = wCount; i < length; i++) {
			int w = smallW * (i + 1);
			canvas.drawBitmap(bottomBm, w, startH, null);
			canvas.drawBitmap(topBm, w, 0, null);
		}

		bottomBm.recycle();
		bottomBm = null;
		topBm.recycle();
		topBm = null;

		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		return newBitmap;
	}

	private Bitmap decodeBitmap(Context context, int bitmap) {
		Bitmap bt = BitmapFactory.decodeResource(context.getResources(), bitmap);
		return bt;
	}

	/**
	 * 截取图片的中间的200X200的区域
	 * 
	 * @param bm
	 * @return
	 */
	private Bitmap cropCenter(Bitmap bm) {
		int dstWidth = 200;
		int dstHeight = 200;
		int startWidth = (bm.getWidth() - dstWidth) / 2;
		int startHeight = ((bm.getHeight() - dstHeight) / 2);
		Rect src = new Rect(startWidth, startHeight, startWidth + dstWidth, startHeight + dstHeight);
		return dividePart(bm, src);
	}

	/**
	 * 剪切图片
	 * 
	 * @param bmp
	 *            被剪切的图片
	 * @param src
	 *            剪切的位置
	 * @return 剪切后的图片
	 */
	private Bitmap dividePart(Bitmap bmp, Rect src) {
		int width = src.width();
		int height = src.height();
		Rect des = new Rect(0, 0, width, height);
		Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(croppedImage);
		canvas.drawBitmap(bmp, src, des, null);
		return croppedImage;
	}

	/**
	 * 添加边框
	 * 
	 * @param bm
	 *            原图片
	 * @param res
	 *            边框资源
	 * @return
	 */
	private Bitmap addBigFrame(Context context, Bitmap bm, int res) {
		Bitmap bitmap = decodeBitmap(context, res);
		Drawable[] array = new Drawable[2];
		array[0] = new BitmapDrawable(bm);
		// TODO
		// Bitmap b = resize(bitmap, bm.getWidth(), bm.getHeight());
		// array[1] = new BitmapDrawable(b);
		array[1] = new BitmapDrawable(bm);
		LayerDrawable layer = new LayerDrawable(array);
		return drawableToBitmap(layer);
	}

	/**
	 * 将Drawable转换成Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 将R.drawable.*转换成Bitmap
	 * 
	 * @param res
	 * @return
	 */
	// private Bitmap decodeBitmap(Context context,int res)
	// {
	// return BitmapFactory.decodeResource(mContext.getResources(), res);
	// }

	/**
	 * https://blog.csdn.net/sjf0115/article/details/7267036
	 * 
	 * getPixel()，组成新图片的时候要一个个setPixel()。另外可以用getPixels()，和setPixels()方法
	 * 
	 * 图片叠加
	 * 
	 * @param context
	 * @param bmp
	 * @param framePicture
	 * @return
	 */
	private Bitmap alphaLayer(Context context, Bitmap bmp, int framePicture) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

		// 边框图片
		Bitmap overlay = BitmapFactory.decodeResource(context.getResources(), framePicture);
		int w = overlay.getWidth();
		int h = overlay.getHeight();
		float scaleX = width * 1F / w;
		float scaleY = height * 1F / h;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);

		Bitmap overlayCopy = Bitmap.createBitmap(overlay, 0, 0, w, h, matrix, true);

		int pixColor = 0;
		int layColor = 0;
		int newColor = 0;

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int pixA = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;
		int newA = 0;

		int layR = 0;
		int layG = 0;
		int layB = 0;
		int layA = 0;

		float alpha = 0.3F;
		float alphaR = 0F;
		float alphaG = 0F;
		float alphaB = 0F;
		for (int i = 0; i < width; i++) {
			for (int k = 0; k < height; k++) {
				pixColor = bmp.getPixel(i, k);
				layColor = overlayCopy.getPixel(i, k);

				// 获取原图片的RGBA值
				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				pixA = Color.alpha(pixColor);

				// 获取边框图片的RGBA值
				layR = Color.red(layColor);
				layG = Color.green(layColor);
				layB = Color.blue(layColor);
				layA = Color.alpha(layColor);

				// 颜色与纯黑色相近的点
				if (layR < 20 && layG < 20 && layB < 20) {
					alpha = 1F;
				} else {
					alpha = 0.3F;
				}

				alphaR = alpha;
				alphaG = alpha;
				alphaB = alpha;

				// 两种颜色叠加
				newR = (int) (pixR * alphaR + layR * (1 - alphaR));
				newG = (int) (pixG * alphaG + layG * (1 - alphaG));
				newB = (int) (pixB * alphaB + layB * (1 - alphaB));
				layA = (int) (pixA * alpha + layA * (1 - alpha));

				// 值在0~255之间
				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));
				newA = Math.min(255, Math.max(0, layA));

				newColor = Color.argb(newA, newR, newG, newB);
				bitmap.setPixel(i, k, newColor);
			}
		}

		return bitmap;
	}

	/**
	 * 图片效果叠加
	 * 
	 * @param bmp
	 *            限制了尺寸大小的Bitmap
	 * @return
	 */
	private Bitmap overlay(Context context, Bitmap bmp) {
		long start = System.currentTimeMillis();
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

		// 对边框图片进行缩放
		Bitmap overlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.image);
		int w = overlay.getWidth();
		int h = overlay.getHeight();
		float scaleX = width * 1F / w;
		float scaleY = height * 1F / h;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);

		Bitmap overlayCopy = Bitmap.createBitmap(overlay, 0, 0, w, h, matrix, true);

		int pixColor = 0;
		int layColor = 0;

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int pixA = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;
		int newA = 0;

		int layR = 0;
		int layG = 0;
		int layB = 0;
		int layA = 0;

		final float alpha = 0.5F;

		int[] srcPixels = new int[width * height];
		int[] layPixels = new int[width * height];
		bmp.getPixels(srcPixels, 0, width, 0, 0, width, height);
		overlayCopy.getPixels(layPixels, 0, width, 0, 0, width, height);

		int pos = 0;
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < width; k++) {
				pos = i * width + k;
				pixColor = srcPixels[pos];
				layColor = layPixels[pos];

				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				pixA = Color.alpha(pixColor);

				layR = Color.red(layColor);
				layG = Color.green(layColor);
				layB = Color.blue(layColor);
				layA = Color.alpha(layColor);

				newR = (int) (pixR * alpha + layR * (1 - alpha));
				newG = (int) (pixG * alpha + layG * (1 - alpha));
				newB = (int) (pixB * alpha + layB * (1 - alpha));
				layA = (int) (pixA * alpha + layA * (1 - alpha));

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));
				newA = Math.min(255, Math.max(0, layA));

				srcPixels[pos] = Color.argb(newA, newR, newG, newB);
			}
		}

		bitmap.setPixels(srcPixels, 0, width, 0, 0, width, height);
		long end = System.currentTimeMillis();
		Log.d("may", "overlayAmeliorate used time=" + (end - start));
		return bitmap;
	}

	/**
	 * 光晕效果
	 * 
	 * @param bmp
	 * @param x
	 *            光晕中心点在bmp中的x坐标
	 * @param y
	 *            光晕中心点在bmp中的y坐标
	 * @param r
	 *            光晕的半径
	 * @return
	 */
	public Bitmap halo(Bitmap bmp, int x, int y, float r) {
		long start = System.currentTimeMillis();
		// 高斯矩阵
		int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };

		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int delta = 18; // 值越小图片会越亮，越大则越暗

		int idx = 0;
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				idx = 0;
				int distance = (int) (Math.pow(k - x, 2) + Math.pow(i - y, 2));
				// 不是中心区域的点做模糊处理
				if (distance > r * r) {
					for (int m = -1; m <= 1; m++) {
						for (int n = -1; n <= 1; n++) {
							pixColor = pixels[(i + m) * width + k + n];
							pixR = Color.red(pixColor);
							pixG = Color.green(pixColor);
							pixB = Color.blue(pixColor);

							newR = newR + (int) (pixR * gauss[idx]);
							newG = newG + (int) (pixG * gauss[idx]);
							newB = newB + (int) (pixB * gauss[idx]);
							idx++;
						}
					}

					newR /= delta;
					newG /= delta;
					newB /= delta;

					newR = Math.min(255, Math.max(0, newR));
					newG = Math.min(255, Math.max(0, newG));
					newB = Math.min(255, Math.max(0, newB));

					pixels[i * width + k] = Color.argb(255, newR, newG, newB);

					newR = 0;
					newG = 0;
					newB = 0;
				}
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		long end = System.currentTimeMillis();
		Log.d("may", "used time=" + (end - start));
		return bitmap;
	}

}
