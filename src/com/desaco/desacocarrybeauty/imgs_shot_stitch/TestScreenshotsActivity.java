package com.desaco.desacocarrybeauty.imgs_shot_stitch;

import java.io.File;

import com.desaco.desacocarrybeauty.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

//com.desaco.desacocarrybeauty.utils.TestScreenshotsActivity
public class TestScreenshotsActivity extends Activity implements OnClickListener {

	private ImageView mShowScreenShotIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_screenshots);

		initView();
		initData();
	}

	private void initView() {
		// 截屏
		Button junpToVideoAct = (Button) findViewById(R.id.jump_video_avtivity);
		junpToVideoAct.setOnClickListener(this);
		// 显示截屏图片
		Button junpToMusicAct = (Button) findViewById(R.id.jump_music_avt);
		junpToMusicAct.setOnClickListener(this);
		//
		mShowScreenShotIv = (ImageView) findViewById(R.id.show_screenshot_imgs);
		//  
		Button horizontalBt = (Button) findViewById(R.id.horizontal_stitchpic);
		horizontalBt.setOnClickListener(this);
		//
		Button verticalBt = (Button) findViewById(R.id.vertical_stitchpic);
		verticalBt.setOnClickListener(this);
	}

	private void initData() {

	}

	@SuppressWarnings("deprecation")
	private void verticalStitchPic() {//
		// vertical
		Bitmap firstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.autoadjust_filter);
		Bitmap secondBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.colortone_filter);
		Bitmap result = ProcessImgs.verticalStitch(firstBitmap, secondBitmap);
		Drawable drawable = new BitmapDrawable(result);
		mShowScreenShotIv.setBackgroundResource(0);// ImageView 清空图片、去除背景图片
		mShowScreenShotIv.setBackgroundDrawable(drawable);
	}

	@SuppressWarnings("deprecation")
	private void horizontalStitchPic() {
		// horizontal
		Bitmap firstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.autoadjust_filter);
		Bitmap secondBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.colortone_filter);
		Bitmap result = ProcessImgs.horizontalStitch(firstBitmap, secondBitmap);
		@SuppressWarnings("deprecation")
		Drawable drawable = new BitmapDrawable(result);
		mShowScreenShotIv.setBackgroundResource(0);// ImageView 清空图片、去除背景图片
		mShowScreenShotIv.setBackgroundDrawable(drawable);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.jump_video_avtivity:// 截屏
			screenShot();
			break;
		case R.id.jump_music_avt:// 显示截屏图片
			show();
			break;
		case R.id.horizontal_stitchpic:
			horizontalStitchPic();
			break;
		case R.id.vertical_stitchpic:
			verticalStitchPic();
			break;
		default:
			break;
		}

	}

	public void screenShot() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_UNMOUNTED)) {
			return;
		}
		String filePath = Environment.getExternalStorageDirectory() + "/a_music_video/screenShot.png";
		File file = new File(filePath);
		Screenshots.shoot(TestScreenshotsActivity.this, file);
	}

	private void show() {
		mShowScreenShotIv.setBackgroundResource(0);// ImageView 清空图片、去除背景图片
		String filename = Environment.getExternalStorageDirectory() + "/a_music_video/screenShot.png";
		File file = new File(filename);
		if (file.exists()) {
			Bitmap bm = BitmapFactory.decodeFile(filename);// 从文件中读取图片
			createWaterMark(bm);
			// mShowScreenShotIv.setImageBitmap(bm);
		}
	}

	@SuppressWarnings("deprecation")
	private void createWaterMark(Bitmap sourBitmap) {
		Bitmap waterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user);

		Bitmap watermarkBitmap = ImgsWaterMark.createWaterMaskCenter(sourBitmap, waterBitmap);
		watermarkBitmap = ImgsWaterMark.createWaterMaskLeftBottom(this, watermarkBitmap, waterBitmap, 0, 0);
		watermarkBitmap = ImgsWaterMark.createWaterMaskRightBottom(this, watermarkBitmap, waterBitmap, 0, 0);
		watermarkBitmap = ImgsWaterMark.createWaterMaskLeftTop(this, watermarkBitmap, waterBitmap, 0, 0);
		watermarkBitmap = ImgsWaterMark.createWaterMaskRightTop(this, watermarkBitmap, waterBitmap, 0, 0);

		Bitmap textBitmap = ImgsWaterMark.drawTextToLeftTop(this, watermarkBitmap, "左上角", 16, Color.RED, 0, 0);
		textBitmap = ImgsWaterMark.drawTextToRightBottom(this, textBitmap, "右下角", 16, Color.RED, 0, 0);
		textBitmap = ImgsWaterMark.drawTextToRightTop(this, textBitmap, "右上角", 16, Color.RED, 0, 0);
		textBitmap = ImgsWaterMark.drawTextToLeftBottom(this, textBitmap, "左下角", 16, Color.RED, 0, 0);
		textBitmap = ImgsWaterMark.drawTextToCenter(this, textBitmap, "中间", 16, Color.RED);

		String filename = Environment.getExternalStorageDirectory() + "/a_music_video/screenShot_mark.png";
		File file = new File(filename);
		save(textBitmap, file);
		
		Drawable drawable = new BitmapDrawable(textBitmap);
		mShowScreenShotIv.setBackgroundDrawable(drawable);
//		mShowScreenShotIv.setImageBitmap(textBitmap);
	}

	private void save(Bitmap bitmap, File file) {
		if (file == null) {
			return;
		}
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		String filename2 = Environment.getExternalStorageDirectory() + "/a_music_video/encode_h264.3gp";
		File file2 = new File(filename2);
		if (!file2.getParentFile().exists()) {
			file2.getParentFile().mkdirs();
		}

		Screenshots.savePic(bitmap, file);
	}
}
