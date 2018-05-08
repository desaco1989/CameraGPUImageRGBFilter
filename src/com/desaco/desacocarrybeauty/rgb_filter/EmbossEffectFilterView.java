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

public class EmbossEffectFilterView extends ImageView {
	/**
	 * 浮雕效果 https://blog.csdn.net/sjf0115/article/details/7266979 
	 *  B.r = C.r -B.r + 127;
	 *  B.g = C.g - B.g + 127; 
	 *  B.b = C.b - B.b + 127;
	 * 
	 */
	private Paint myPaint = null;
	private Bitmap bitmap = null;
	private int width, height;
	private int[] oldPixels;
	private int[] newPixels;
	private int color, color2;
	private int pixelsR, pixelsG, pixelsB, pixelsA, pixelsR2, pixelsG2, pixelsB2;

	public EmbossEffectFilterView(Context context, AttributeSet attrs) {
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
			color = oldPixels[i - 1];
			// 前一个像素
			pixelsR = Color.red(color);
			pixelsG = Color.green(color);
			pixelsB = Color.blue(color);
			// 当前像素
			color2 = oldPixels[i];
			pixelsR2 = Color.red(color2);
			pixelsG2 = Color.green(color2);
			pixelsB2 = Color.blue(color2);

			pixelsR = (pixelsR - pixelsR2 + 127);
			pixelsG = (pixelsG - pixelsG2 + 127);
			pixelsB = (pixelsB - pixelsB2 + 127);
			// 均小于等于255
			if (pixelsR > 255) {
				pixelsR = 255;
			}

			if (pixelsG > 255) {
				pixelsG = 255;
			}

			if (pixelsB > 255) {
				pixelsB = 255;
			}

			newPixels[i] = Color.argb(pixelsA, pixelsR, pixelsG, pixelsB);

		}
		bitmap.setPixels(newPixels, 0, width, 0, 0, width, height);
		canvas.drawBitmap(bitmap, 0, 0, myPaint);
	}

}
