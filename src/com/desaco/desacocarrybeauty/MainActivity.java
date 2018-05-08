package com.desaco.desacocarrybeauty;

import com.desaco.desacocarrybeauty.camera.CameraOneActivity;
import com.desaco.desacocarrybeauty.camera2.CameraTwoActivity;
import com.desaco.desacocarrybeauty.camera_filter.ImageFilterMain;
import com.desaco.desacocarrybeauty.camera_filter2.actiivity.ImageSpecialEffectsProcessActivity;
import com.desaco.desacocarrybeauty.camera_filter2.actiivity.TestFilterMainActivity;
import com.desaco.desacocarrybeauty.gpu_image.gpu_sample.activity.TestGpuImageActivity;
import com.desaco.desacocarrybeauty.imgs_shot_stitch.TestScreenshotsActivity;
import com.desaco.desacocarrybeauty.sobel_camera.CameraActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * com.desaco.desacocarrybeauty.MainActivity
 * 
 * @author desaco
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initData();
	}

	private void initView() {
		Button beforeAndroidL = (Button) findViewById(R.id.before_l_camare);
		beforeAndroidL.setOnClickListener(this);

		Button afterAndroidL = (Button) findViewById(R.id.after_l_camare);
		afterAndroidL.setOnClickListener(this);
		//
		Button imageFilterBt = (Button) findViewById(R.id.image_filter_bt);
		imageFilterBt.setOnClickListener(this);
		// gpu_image_filter
		Button gpuFilterBt = (Button) findViewById(R.id.gpu_image_filter);
		gpuFilterBt.setOnClickListener(this);
		// 99
		Button imagefilterBt = (Button) findViewById(R.id.imagefilter_bt);
		imagefilterBt.setOnClickListener(this);
		//
		Button stitchpicBt = (Button) findViewById(R.id.stitchpic_bt);
		stitchpicBt.setOnClickListener(this);
		//sobel_img_bt
		Button sobelBt = (Button) findViewById(R.id.sobel_img_bt);
		sobelBt.setOnClickListener(this);
		//image_specialeffects_bt
		Button image_specialeffectsBt = (Button) findViewById(R.id.image_specialeffects_bt);
		image_specialeffectsBt.setOnClickListener(this);

	}

	private void initData() {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.before_l_camare:// 5.0之前的照相机
			Intent cameraOneIntent = new Intent();
			cameraOneIntent.setClass(getApplicationContext(), CameraOneActivity.class);
			startActivity(cameraOneIntent);
			break;
		case R.id.after_l_camare:// 5.0之后的照相机
			Intent camera2Intent = new Intent();
			camera2Intent.setClass(getApplicationContext(), CameraTwoActivity.class);
			startActivity(camera2Intent);
			break;
		case R.id.image_filter_bt:// 9滤镜效果
			Intent filterIntent = new Intent();
			filterIntent.setClass(getApplicationContext(), TestFilterMainActivity.class);
			startActivity(filterIntent);
			break;
		case R.id.gpu_image_filter://71 GPU滤镜效果
			Intent gpufilterIntent = new Intent();
			gpufilterIntent.setClass(getApplicationContext(), TestGpuImageActivity.class);
			startActivity(gpufilterIntent);
			break;
		case R.id.imagefilter_bt://99种滤镜
			Intent imagefilterIntent = new Intent();
			imagefilterIntent.setClass(getApplicationContext(), ImageFilterMain.class);
			startActivity(imagefilterIntent);
			break;
		case R.id.stitchpic_bt://图片拼接
			Intent stitchpicIntent = new Intent();
			stitchpicIntent.setClass(getApplicationContext(), TestScreenshotsActivity.class);
			startActivity(stitchpicIntent);
			break;
		case R.id.sobel_img_bt:
			Intent sobelIntent = new Intent();
			sobelIntent.setClass(getApplicationContext(), CameraActivity.class);
			startActivity(sobelIntent);
		case R.id.image_specialeffects_bt:
			jumpToGoalActivity(ImageSpecialEffectsProcessActivity.class);
			break;
		default:
			break;
		}

	}
	private void jumpToGoalActivity(Class<?> clazz){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),clazz);
        startActivity(intent);
    }
}
