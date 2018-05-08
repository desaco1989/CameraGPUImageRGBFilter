package com.desaco.desacocarrybeauty.camera_filter2.actiivity;

import java.io.File;
import java.util.ArrayList;

import com.desaco.desacocarrybeauty.R;
import com.desaco.desacocarrybeauty.picture_widget_utils.CommonImageUilts;
import com.desaco.desacocarrybeauty.picture_widget_utils.CustomFramePictureView;
import com.desaco.desacocarrybeauty.picture_widget_utils.CustomGraffitiView;
import com.desaco.desacocarrybeauty.picture_widget_utils.HueSaturationBrightnessView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * Android实战经验之图像处理及特效处理的集锦（总结版） https://www.oschina.net/question/231733_44154
 * 图像像素的操作
 * 
 * @author desaco
 *
 */
public class ImageSpecialEffectsProcessActivity extends Activity implements OnSeekBarChangeListener {
	// com.desaco.desacocarrybeauty.camera_filter2.actiivity.ImageSpecialEffectsProcessActivity

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_specialeffects_process);

		initView();
		initData();
	}

	private void initView() {
		// graffiti_view 图片涂鸦
		final CustomGraffitiView graffitiView = (CustomGraffitiView) findViewById(R.id.graffiti_view);
		Button clearBt = (Button) findViewById(R.id.clear_screen);
		clearBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				graffitiView.clear();
			}
		});

		ImageView roundImage = (ImageView) findViewById(R.id.round_img);
		// round_img 圆角图片
		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.image);
		// roundImage.setImageBitmap(CommonImageUilts.getRoundCornerImage(bitmap,
		// 100));

		// TODO 得到缩略图 https://blog.csdn.net/sjf0115/article/details/7277286
		// ImageView thumbnailIv = (ImageView)findViewById(R.id.thumbnail_img);
		// //得到原图片
		// bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.image);
		// //得到缩略图
		// bitmap = ThumbnailUtils.extractThumbnail(bitmap, 100, 100);
		// thumbnailIv.setImageBitmap(bitmap);

		// 给图片加边框 https://blog.csdn.net/sjf0115/article/details/7269251
		CustomFramePictureView image = (CustomFramePictureView) findViewById(R.id.FramePicture_img);
		image.setColour(Color.YELLOW);
		image.setBorderWidth(10);

	}

	HueSaturationBrightnessView mToneLayer;
	Bitmap mBitmap;
	ImageView mImageView;
	private LinearLayout mParentView;

	private void initData() {
		// 图片色调饱和度、色相、亮度处理
		mToneLayer = new HueSaturationBrightnessView(this);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
		mImageView = (ImageView) findViewById(R.id.img_view);
		mImageView.setImageBitmap(mBitmap);
		mParentView = ((LinearLayout) findViewById(R.id.tone_view));
		mParentView.addView(mToneLayer.getParentView());

		ArrayList<SeekBar> seekBars = mToneLayer.getSeekBars();
		for (int i = 0, size = seekBars.size(); i < size; i++) {
			seekBars.get(i).setOnSeekBarChangeListener(this);
		}

		//
		// clip_img 裁剪图片
		clipBt = (Button) findViewById(R.id.clip_img);
		clipBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tempFile = new File("/sdcard/a_music_video/b.jpg");
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.

				intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
				intent.putExtra("aspectY", 2);// x:y=1:2

				intent.putExtra("output", Uri.fromFile(tempFile));
				intent.putExtra("outputFormat", "JPEG");// 返回格式

				startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE_CLIP);
			}
		});

	}

	Button clipBt;
	private File tempFile;
	private final static int SELECT_PICTURE_CLIP = 100;

	/**
	 * 裁剪完图片后系统调用的方法:onActivityResult
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK)
			if (requestCode == SELECT_PICTURE_CLIP)
				clipBt.setBackgroundDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		int flag = (Integer) seekBar.getTag();
		switch (flag) {
		case HueSaturationBrightnessView.FLAG_SATURATION:
			mToneLayer.setSaturation(progress);
			break;
		case HueSaturationBrightnessView.FLAG_LUM:
			mToneLayer.setLum(progress);
			break;
		case HueSaturationBrightnessView.FLAG_HUE:
			mToneLayer.setHue(progress);
			break;
		}

		mImageView.setImageBitmap(mToneLayer.handleImage(mBitmap, flag));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

}
