package com.desaco.desacocarrybeauty.camera_filter2.actiivity;



import com.desaco.desacocarrybeauty.R;
import com.desaco.desacocarrybeauty.R.drawable;
import com.desaco.desacocarrybeauty.R.id;
import com.desaco.desacocarrybeauty.R.layout;
import com.desaco.desacocarrybeauty.camera_filter2.AntiColorFilter;
import com.desaco.desacocarrybeauty.camera_filter2.ComicFilter;
import com.desaco.desacocarrybeauty.camera_filter2.FeatherFilter;
import com.desaco.desacocarrybeauty.camera_filter2.GlowingEdgeFilter;
import com.desaco.desacocarrybeauty.camera_filter2.IceFilter;
import com.desaco.desacocarrybeauty.camera_filter2.MoltenFilter;
import com.desaco.desacocarrybeauty.camera_filter2.ZoomBlurFilter;
import com.desaco.desacocarrybeauty.camera_filter2.light_edge.SoftGlowFilter;
import com.desaco.desacocarrybeauty.camera_filter2.lomo.LomoFilter;
import com.desaco.desacocarrybeauty.utils.ImageCache;
import com.desaco.desacocarrybeauty.utils.ImageUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 
 * @author desaco
 *
 */
public class ImageFilterActivity extends Activity {

	ImageView imageView1, imageView2;
	Drawable mDrawable;
	Bitmap mBitmap;
	
	private ProgressBar mProcessBar;
	Context mContext;
	
	private String[] key = {"IceFilter","MoltenFilter", "ComicFilter", "SoftGlowFilter", "GlowingEdgeFilter"
			,"FeatherFilter", "ZoomBlurFilter", "LomoFilter","AntiColorFilter"};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.image);
		mContext = this;
		findView();

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		int pos = bundle.getInt("position", 0);
		Bitmap tmpBitmap;
		switch (pos) {
		case 0:
			if (ImageCache.get(key[0]) != null) {
				tmpBitmap = ImageCache.get(key[0]);
				imageView2.setImageBitmap(tmpBitmap);
				break;
			}
			new Thread(){
				public void run() {
					mHandler.sendEmptyMessage(PROGRESS_WAIT_VISIBLE);
					Bitmap tmpBitmap = new IceFilter(mBitmap).imageProcess().getDstBitmap();
					Message msg = new Message();
					msg.obj = tmpBitmap;
					msg.arg1 = 0;
					msg.what = PROGRESS_WAIT_GONE;
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
			
		case 1:
	
			if (ImageCache.get(key[1]) != null) {
				tmpBitmap = ImageCache.get(key[1]);
				imageView2.setImageBitmap(tmpBitmap);
				break;
			}
			new Thread(){
				public void run() {
					mHandler.sendEmptyMessage(PROGRESS_WAIT_VISIBLE);
					Bitmap tmpBitmap = new MoltenFilter(mBitmap).imageProcess().getDstBitmap();
					Message msg = new Message();
					msg.obj = tmpBitmap;
					msg.arg1 = 1;
					msg.what = PROGRESS_WAIT_GONE;
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
			
		case 2:
			if (ImageCache.get(key[2]) != null) {
				tmpBitmap = ImageCache.get(key[2]);
				imageView2.setImageBitmap(tmpBitmap);
				break;
			}
			new Thread(){
				public void run() {
					mHandler.sendEmptyMessage(PROGRESS_WAIT_VISIBLE);
					Bitmap tmpBitmap = new ComicFilter(mBitmap).imageProcess().getDstBitmap();
					Message msg = new Message();
					msg.obj = tmpBitmap;
					msg.arg1 = 2;
					msg.what = PROGRESS_WAIT_GONE;
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
			
		case 3:		
			if (ImageCache.get(key[3]) != null) {
				tmpBitmap = ImageCache.get(key[3]);
				imageView2.setImageBitmap(tmpBitmap);
				break;
			}
			new Thread(){
				public void run() {
					mHandler.sendEmptyMessage(PROGRESS_WAIT_VISIBLE);
					Bitmap tmpBitmap = new SoftGlowFilter(mBitmap,10, 0.1f, 0.1f).imageProcess().getDstBitmap();
					Message msg = new Message();
					msg.obj = tmpBitmap;
					msg.arg1 = 3;
					msg.what = PROGRESS_WAIT_GONE;
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
		case 4:
			if (ImageCache.get(key[4]) != null) {
				tmpBitmap = ImageCache.get(key[4]);
				imageView2.setImageBitmap(tmpBitmap);
				break;
			}
			new Thread(){
				public void run() {
					mHandler.sendEmptyMessage(PROGRESS_WAIT_VISIBLE);
					Bitmap tmpBitmap = new GlowingEdgeFilter(mBitmap).imageProcess().getDstBitmap();
					Message msg = new Message();
					msg.obj = tmpBitmap;
					msg.arg1 = 4;
					msg.what = PROGRESS_WAIT_GONE;
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
		case 5:		
			if (ImageCache.get(key[5]) != null) {
				tmpBitmap = ImageCache.get(key[5]);
				imageView2.setImageBitmap(tmpBitmap);
				break;
			}
			new Thread(){
				public void run() {
					mHandler.sendEmptyMessage(PROGRESS_WAIT_VISIBLE);
					Bitmap tmpBitmap = new FeatherFilter(mBitmap).imageProcess().getDstBitmap();
					Message msg = new Message();
					msg.obj = tmpBitmap;
					msg.arg1 = 5;
					msg.what = PROGRESS_WAIT_GONE;
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
			
		case 6:
			if (ImageCache.get(key[6]) != null) {
				tmpBitmap = ImageCache.get(key[6]);
				imageView2.setImageBitmap(tmpBitmap);
				break;
			}
			new Thread(){
				public void run() {
					mHandler.sendEmptyMessage(PROGRESS_WAIT_VISIBLE);
					Bitmap tmpBitmap = new ZoomBlurFilter(mBitmap, 30).imageProcess().getDstBitmap();
					Message msg = new Message();
					msg.obj = tmpBitmap;
					msg.arg1 = 6;
					msg.what = PROGRESS_WAIT_GONE;
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
		case 7:
			if (ImageCache.get(key[7]) != null) {
				tmpBitmap = ImageCache.get(key[7]);
				imageView2.setImageBitmap(tmpBitmap);
				break;
			}
			new Thread(){
				public void run() {
					mHandler.sendEmptyMessage(PROGRESS_WAIT_VISIBLE);
					Bitmap tmpBitmap = new LomoFilter(mBitmap).imageProcess().getDstBitmap();
					Message msg = new Message();
					msg.obj = tmpBitmap;
					msg.arg1 = 7;
					msg.what = PROGRESS_WAIT_GONE;
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
		case 8:
			if (ImageCache.get(key[8]) != null) {
				tmpBitmap = ImageCache.get(key[8]);
				imageView2.setImageBitmap(tmpBitmap);
				break;
			}
			new Thread(){
				public void run() {
					mHandler.sendEmptyMessage(PROGRESS_WAIT_VISIBLE);
					Bitmap tmpBitmap = new AntiColorFilter(mBitmap).imageProcess().getDstBitmap();
					Message msg = new Message();
					msg.obj = tmpBitmap;
					msg.arg1 = 8;
					msg.what = PROGRESS_WAIT_GONE;
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
			
		default:
			imageView2.setImageBitmap(mBitmap);
			break;
		}
	}

	private void findView() {
		//mProcessBar 
		mProcessBar = (ProgressBar)findViewById(R.id.loading_circle_progressBar);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		mDrawable = imageView1.getDrawable();
		mBitmap = ImageUtil.readBitMap(mContext, R.drawable.image);
		imageView2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				imageView1.setVisibility(View.GONE);
			}
		});
	}
	
	private Handler mHandler = new Handler() {
		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case PROGRESS_WAIT_VISIBLE://TODO
//					setProgressBarIndeterminateVisibility(true);
					mProcessBar.setVisibility(View.VISIBLE);
					break;
				case PROGRESS_WAIT_GONE:
//					setProgressBarIndeterminateVisibility(false);
					mProcessBar.setVisibility(View.GONE);
					Bitmap tmpBitmap =(Bitmap) msg.obj;
					imageView2.setImageBitmap(tmpBitmap);
					ImageCache.put(key[msg.arg1], tmpBitmap);
					break;
				default:
					break;
			}
		}
	};
	private final int PROGRESS_WAIT_VISIBLE = 1;
	private final int PROGRESS_WAIT_GONE = 2;
}
