package com.desaco.desacocarrybeauty.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hejunlin on 2016/10/5.
 */
@SuppressWarnings("deprecation")
public class SurfacePreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

	private static final String TAG = SurfacePreview.class.getSimpleName();
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private Camera.Parameters mParameters;

	public SurfacePreview(Context context) {
		super(context);

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.setFormat(PixelFormat.TRANSPARENT);
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated() is called");
		try {
			// Open the Camera in preview mode
			mCamera = Camera.open(0);
			mCamera.setDisplayOrientation(90);
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			// 关掉闪光灯
			// Camera.Parameters mParameters;
			Camera.Parameters mParameters = mCamera.getParameters();
			mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			mCamera.setParameters(mParameters);
		} catch (IOException e) {
			Log.d(TAG, "Error setting camera preview: " + e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Log.d(TAG, "surfaceChanged() is called");
		try {
			// mCamera.startPreview();
			mCamera.autoFocus(new Camera.AutoFocusCallback() {
				@Override
				public void onAutoFocus(boolean success, Camera camera) {
					if (success) {
						initCamera();
						camera.cancelAutoFocus();
					}
				}
			});
		} catch (Exception e) {
			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	private void initCamera() {
		mParameters = mCamera.getParameters();
		mParameters.setPictureFormat(PixelFormat.JPEG);
		mParameters.setPictureSize(1080, 1920);
		mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);// TODO FLASH_MODE_OFF  FLASH_MODE_TORCH
		mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		setDispaly(mParameters, mCamera);
		mCamera.setParameters(mParameters);
		mCamera.startPreview();
		mCamera.cancelAutoFocus();

	}

	@SuppressWarnings("deprecation")
	private void setDispaly(Camera.Parameters parameters, Camera camera) {
		if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
			setDisplayOrientation(camera, 90);
		} else {
			parameters.setRotation(90);
		}
	}

	private void setDisplayOrientation(Camera camera, int i) {
		Method downPolymorphic;
		try {
			downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
			if (downPolymorphic != null) {
				downPolymorphic.invoke(camera, new Object[] { i });
			}
		} catch (Exception e) {
			Log.e(TAG, "image error");
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
		Log.d(TAG, "surfaceDestroyed() is called");
	}

	@SuppressWarnings("deprecation")
	public void takePicture(Camera.PictureCallback imageCallback) {
		mCamera.takePicture(null, null, imageCallback);
	}

	/**
	 * camera 通过onPreviewFrame采集视频,Camera.PreviewCallback
	 */
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		// TODO
		// 刚刚拍照的文件名
		String fileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString() + ".jpg";
		File sdRoot = Environment.getExternalStorageDirectory();
		String dir = "/a_music_video/";
		File mkDir = new File(sdRoot, dir);
		if (!mkDir.exists())
			mkDir.mkdirs();
		File pictureFile = new File(sdRoot, dir + fileName);
		if (!pictureFile.exists()) {
			try {
				pictureFile.createNewFile();
				Camera.Parameters parameters = camera.getParameters();
				Size size = parameters.getPreviewSize();
				YuvImage image = new YuvImage(data, parameters.getPreviewFormat(), size.width, size.height, null);
				FileOutputStream filecon = new FileOutputStream(pictureFile);
				image.compressToJpeg(new Rect(0, 0, image.getWidth(), image.getHeight()), 90, filecon);
				Toast.makeText(getContext(), "拍照了，看看有没有！", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
//		Toast.makeText(getContext(), "拍照了，onPreviewFrame()！", Toast.LENGTH_LONG).show();
//		try {
//	        Camera.Parameters parameters = camera.getParameters();
//	        Size size = parameters.getPreviewSize();
//	        YuvImage image = new YuvImage(data, parameters.getPreviewFormat(),
//	                size.width, size.height, null);
//	        File file = new File(Environment.getExternalStorageDirectory()
//	                .getPath() + "/out.jpg");
//	        FileOutputStream filecon = new FileOutputStream(file);
//	        image.compressToJpeg(
//	                new Rect(0, 0, image.getWidth(), image.getHeight()), 90,
//	                filecon);
//	    } catch (FileNotFoundException e) {
//	        Toast toast = Toast
//	                .makeText(getContext(), e.getMessage(), 1000);
//	        toast.show();
//	    }
		
	}
	
	ByteArrayOutputStream baos;  
    byte[] rawImage;  
    Bitmap bitmap; 
    //http://blog.csdn.net/qiguangyaolove/article/details/53130061
    public void runInPreviewFrame(byte[] data, Camera camera) {  
        camera.setOneShotPreviewCallback(null);  
        //处理data  
        Camera.Size previewSize = camera.getParameters().getPreviewSize();//获取尺寸,格式转换的时候要用到  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        newOpts.inJustDecodeBounds = true;  
        YuvImage yuvimage = new YuvImage(  
                data,  
                ImageFormat.NV21,  
                previewSize.width,  
                previewSize.height,  
                null);  
        baos = new ByteArrayOutputStream();  
        yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100, baos);// 80--JPG图片的质量[0-100],100最高  
        rawImage = baos.toByteArray();  
        //将rawImage转换成bitmap  
        BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inPreferredConfig = Bitmap.Config.RGB_565;  
        bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options); 
    }
}
