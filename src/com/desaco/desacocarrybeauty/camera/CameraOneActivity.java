package com.desaco.desacocarrybeauty.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.desaco.desacocarrybeauty.R;

/**
 * 
 * com.desaco.carrybeauty.camera.CameraOneActivity 发现你的美，携带好心情
 * Android 5.0之前的Camera API
 * @author desaco
 *
 */
public class CameraOneActivity extends Activity implements Camera.PictureCallback, View.OnClickListener {

	private static final String TAG = CameraOneActivity.class.getSimpleName();
	private SurfacePreview mCameraSurPreview;
	private ImageView mCaptureButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cameraone);

		// Create our Preview view and set it as the content of our activity.
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		mCameraSurPreview = new SurfacePreview(this);
		preview.addView(mCameraSurPreview);

		// Add a listener to the Capture button
		mCaptureButton = (ImageView) findViewById(R.id.capture);
		mCaptureButton.setOnClickListener(this);
		
//		closeFlashLight();
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		// save the picture to sdcard
		File pictureFile = getOutputMediaFile();
		if (pictureFile == null) {
			Log.d(TAG, "Error creating media file, check storage permissions: ");
			return;
		}

		try {
			FileOutputStream fos = new FileOutputStream(pictureFile);
			fos.write(data);
			fos.close();
		} catch (FileNotFoundException e) {
			Log.d(TAG, "File not found: " + e.getMessage());
		} catch (IOException e) {
			Log.d(TAG, "Error accessing file: " + e.getMessage());
		}

		// Restart the preview and re-enable the shutter button so that we can
		// take another picture
		camera.startPreview();

		// See if need to enable or not
		mCaptureButton.setEnabled(true);
		Toast.makeText(this, "拍照成功", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.capture:
			mCaptureButton.setEnabled(false);
			// get an image from the camera
			mCameraSurPreview.takePicture(this);
			break;

		default:
			break;
		}
		
	}

	private File getOutputMediaFile() {
		// get the mobile Pictures directory
		File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		// get the current time
		String timeStamp = new SimpleDateFormat("yyyy-MMdd-HHmmss").format(new Date());
		return new File(picDir.getPath() + File.separator + "desaco_" + timeStamp + ".jpg");
	}
	/**
	 * <uses-permission android:name="android.permission.FLASHLIGHT" />
	 * 打开闪光灯
	 */
	@SuppressWarnings("deprecation")
	public void openFlashLight() {
		try {
			Camera m_Camera = Camera.open();
			Camera.Parameters mParameters;
			mParameters = m_Camera.getParameters();
			mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			m_Camera.setParameters(mParameters);
            
		} catch (Exception ex) {
		}
	}
	/**
	 * <uses-permission android:name="android.permission.FLASHLIGHT" />
	 * 关闭闪光灯
	 */
	@SuppressWarnings("deprecation")
	public void closeFlashLight() {
		try {
			Camera m_Camera = Camera.open();
			Camera.Parameters mParameters;
			mParameters = m_Camera.getParameters();
			mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			m_Camera.setParameters(mParameters);
			m_Camera.release();
		} catch (Exception ex) {
		}
	}
}
