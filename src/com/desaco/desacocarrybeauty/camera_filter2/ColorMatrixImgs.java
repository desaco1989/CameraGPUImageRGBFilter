package com.desaco.desacocarrybeauty.camera_filter2;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 图像滤镜功能:实时滤镜和静态滤镜
 * 
 * @author desaco
 *
 */
public class ColorMatrixImgs {
	/**
	 * http://blog.csdn.net/real_myth/article/details/50925986
	 * 
	 * Android提供了改变图像数值的方法ColorMatrix，通过ColorMatrix方法可以实现基本滤镜，如黑白、灰色、泛黄等效果。
	 * @param src
	 * @return
	 */
	public Bitmap getBlackStyle(Bitmap src){
		
		Bitmap dst = Bitmap.createBitmap(src.getWidth(),src.getHeight(), Config.ARGB_8888);
				Canvas canvas = new Canvas(dst);
				ColorMatrix cm = new ColorMatrix();
		        //设定图像为灰色，通过查资料 R 0.3 G0.59 B 0.11
				cm.set(new float[] { 
						0.3f, 0.59f, 0.11f, 0, 0, 
						0.3f, 0.59f, 0.11f, 0, 0, 
						0.3f, 0.59f, 0.11f, 0, 0, 
						0, 0, 0, 1, 0 });
				Paint paint = new Paint();
				paint.setColorFilter(new ColorMatrixColorFilter(cm));
				canvas.drawBitmap(src, 0, 0, paint);
				// 保存图像
				canvas.save(Canvas.ALL_SAVE_FLAG);
		        // 存储
				canvas.restore();
				return dst;
			}
}
