package com.desaco.desacocarrybeauty.picture_widget_utils;

import com.desaco.desacocarrybeauty.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.View;

/**
 * Bitmap位图的缩放 https://blog.csdn.net/sjf0115/article/details/7262777
 * @author desaco
 *
 */
public class ScalePictureView extends View {
	public ScalePictureView(Context context) {
		super(context);
	}

	// 重写onDraw方法
	public void onDraw(Canvas canvas) {
		// 获取资源文件的引用res
		Resources res = getResources();
		// 获取图形资源文件
		Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.image);
		// 设置canvas画布背景为白色
		canvas.drawColor(Color.BLACK);
		// 在画布上绘制缩放之前的位图，以做对比
		// 屏幕上的位置坐标是0,0
		canvas.drawBitmap(bmp, 0, 0, null);
		// 定义矩阵对象
		Matrix matrix = new Matrix();
		// 缩放原图
		matrix.postScale(0.5f, 0.3f);
		// bmp.getWidth(), bmp.getHeight()分别表示缩放后的位图宽高
		Bitmap dstbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
		// 在画布上绘制旋转后的位图
		// 放在坐标为60,460的位置
		canvas.drawBitmap(dstbmp, 60, 460, null);
	}

}
