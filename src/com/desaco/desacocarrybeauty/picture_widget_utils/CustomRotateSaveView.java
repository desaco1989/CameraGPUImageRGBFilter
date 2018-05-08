package com.desaco.desacocarrybeauty.picture_widget_utils;

import java.io.File;
import java.io.FileOutputStream;

import com.desaco.desacocarrybeauty.R;
import com.desaco.desacocarrybeauty.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.View;

/**
 * 画图并保存图片到本地
 * https://blog.csdn.net/sjf0115/article/details/7269117
 * @author desaco
 *
 */
public class CustomRotateSaveView extends View {
	private Matrix matrix = null;

	public CustomRotateSaveView(Context context) {
		super(context);
	}

	public void onDraw(Canvas canvas) {

		// 获取资源文件的引用res
		Resources res = getResources();
		// 获取图形资源文件
		Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.image);
		// 设置canvas画布背景为白色
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(bmp, 0, 0, null);
		// 定义矩阵对象
		matrix = new Matrix();
		// 旋转30度
		matrix.postRotate(30);
		Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 50, bmp.getWidth(), bmp.getHeight() / 2, matrix, true);
		canvas.drawBitmap(bitmap, 0, 250, null);
		SaveBitmap(bitmap);
	}

	// 保存到本地
	public void SaveBitmap(Bitmap bmp) {
		
		Bitmap bitmap = Bitmap.createBitmap(800, 600, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		// 加载背景图片
		Bitmap bmps = BitmapFactory.decodeResource(getResources(), R.drawable.image);
		canvas.drawBitmap(bmps, 0, 0, null);
		// 加载要保存的画面
		canvas.drawBitmap(bmp, 10, 100, null);
		// 保存全部图层
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		// 存储路径
		File file = new File("/sdcard/song/");
		if (!file.exists())
			file.mkdirs();
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file.getPath() + "/xuanzhuan.jpg");
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
			fileOutputStream.close();
			System.out.println("saveBmp is here");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
