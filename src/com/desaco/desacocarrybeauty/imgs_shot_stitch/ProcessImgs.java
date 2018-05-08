package com.desaco.desacocarrybeauty.imgs_shot_stitch;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

public class ProcessImgs {
	private ProcessImgs() {
	}

	/**
	 * 横向拼接 <功能详细描述>
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static Bitmap verticalStitch(Bitmap first, Bitmap second) {
		int width = first.getWidth() + second.getWidth();
		int height = Math.max(first.getHeight(), second.getHeight());
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		canvas.drawBitmap(first, 0, 0, null);
		canvas.drawBitmap(second, first.getWidth(), 0, null);
		return result;
	}

	/**
	 * 纵向拼接 <功能详细描述>
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static Bitmap horizontalStitch(Bitmap first, Bitmap second) {
		int width = Math.max(first.getWidth(), second.getWidth());
		int height = first.getHeight() + second.getHeight();
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		canvas.drawBitmap(first, 0, 0, null);
		canvas.drawBitmap(second, first.getHeight(), 0, null);
		return result;
	}

	// public static Bitmap drawableToBitmap(Drawable drawable) {
	// Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight(),
	// drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 :
	// Bitmap.Config.RGB_565);
	// Canvas canvas = new Canvas(bitmap);
	// // canvas.setBitmap(bitmap);
	// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight());
	// drawable.draw(canvas);
	// return bitmap;
	// }
}
