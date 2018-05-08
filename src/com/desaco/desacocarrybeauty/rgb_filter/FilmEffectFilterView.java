package com.desaco.desacocarrybeauty.rgb_filter;

import com.desaco.desacocarrybeauty.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 底片效果：https://blog.csdn.net/sjf0115/article/details/7266974
 * 算法原理：将当前像素点的RGB值分别与255之差后的值作为当前点的RGB值。 B.r = 255 - B.r; B.g = 255 - B.g; B.b
 * = 255 - B.b;
 * 
 * @author desaco
 *
 */
public class FilmEffectFilterView extends ImageView {
	// com.desaco.desacocarrybeauty.rgb_filter.FilmEffectFilterView

	private Paint myPaint = null;
	private Bitmap bitmap = null;
	private int width, height;
	private int[] oldPixels;
	private int[] newPixels;
	private int color, color2;
	private int pixelsR, pixelsG, pixelsB, pixelsA, pixelsR2, pixelsG2, pixelsB2;

	public FilmEffectFilterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		oldPixels = new int[width * height];
		newPixels = new int[width * height];
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取像素
		bitmap.getPixels(oldPixels, 0, width, 0, 0, width, height);

		for (int i = 1; i < height * width; i++) {
			color = oldPixels[i];
			// 获取RGB分量
			pixelsA = Color.alpha(color);
			pixelsR = Color.red(color);
			pixelsG = Color.green(color);
			pixelsB = Color.blue(color);

			// 转换
			pixelsR = (255 - pixelsR);
			pixelsG = (255 - pixelsG);
			pixelsB = (255 - pixelsB);
			// 均小于等于255大于等于0
			if (pixelsR > 255) {
				pixelsR = 255;
			} else if (pixelsR < 0) {
				pixelsR = 0;
			}
			if (pixelsG > 255) {
				pixelsG = 255;
			} else if (pixelsG < 0) {
				pixelsG = 0;
			}
			if (pixelsB > 255) {
				pixelsB = 255;
			} else if (pixelsB < 0) {
				pixelsB = 0;
			}
			// 根据新的RGB生成新像素
			newPixels[i] = Color.argb(pixelsA, pixelsR, pixelsG, pixelsB);

		}
		// 根据新像素生成新图片
		bitmap.setPixels(newPixels, 0, width, 0, 0, width, height);
		canvas.drawBitmap(bitmap, 0, 0, myPaint);
	}

}
