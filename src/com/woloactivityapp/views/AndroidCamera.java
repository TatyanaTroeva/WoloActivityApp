package com.woloactivityapp.views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Face;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.woloactivityapp.camera.CameraPreview;
import com.woloactivityapp.camera.FragmentationManager;
import com.woloactivityapp.main.R;

public class AndroidCamera extends Activity {
	private FrameLayout previewLayout;
	private Camera.Size optimalSize;
	Camera camera;
	boolean previewing = false;
	LayoutInflater controlInflater = null;
	Button camerabtn;
	Face[] detectedFaces;
	final int RESULT_SAVEIMAGE = 0;
	private CameraPreview mPreview;
	private CameraOrientationEventListener cameraOrientationEventListener;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_camera);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFormat(PixelFormat.UNKNOWN);

		camerabtn = (Button) findViewById(R.id.camerabtn);
		previewLayout = ((FrameLayout) findViewById(R.id.camera_preview));
		camerabtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				camera.takePicture(myShutterCallback, myPictureCallback_RAW,
						myPictureCallback_JPG);
			}
		});
		cameraOrientationEventListener = new CameraOrientationEventListener(
				this);

	}

	AutoFocusCallback myAutoFocusCallback = new AutoFocusCallback() {

		@Override
		public void onAutoFocus(boolean arg0, Camera arg1) {
			// TODO Auto-generated method stub

		}
	};

	ShutterCallback myShutterCallback = new ShutterCallback() {

		@Override
		public void onShutter() {
			// TODO Auto-generated method stub

		}
	};

	PictureCallback myPictureCallback_RAW = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub

		}
	};

	private void releaseCamera() {
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}

	private Camera getCameraInstance() {
		try {
			Camera localCamera = Camera.open(0);
			return localCamera;
		} catch (Exception localException) {

		}
		return null;
	}

	private Camera.Size getVideoSize() {
		Camera.Parameters localParameters = camera.getParameters();
		Camera.Size localSize = getOptimalPreviewSize(
				localParameters.getSupportedPreviewSizes(), 480, 360);
		if (localSize == null)
			localSize = localParameters.getPreferredPreviewSizeForVideo();
		return localSize;
	}

	private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w,
			int h) {
		final double ASPECT_TOLERANCE = 0.05;
		double targetRatio = (double) w / h;
		if (sizes == null) {
			return null;
		}

		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		// Try to find an size match aspect ratio and size
		for (Camera.Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
				continue;
			}
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Camera.Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	private void setupCamera() {
		if (camera != null)
			camera.release();

		camera = getCameraInstance();

		if (this.camera == null) {
			finish();
			return;
		}
		Camera.Parameters localParameters = this.camera.getParameters();
		optimalSize = getVideoSize();
		localParameters.setPreviewSize(optimalSize.width, optimalSize.height);
		localParameters.setRecordingHint(true);
		localParameters.set("cam_mode", 1);
		if ((localParameters.getFlashMode() != null)
				&& (!FragmentationManager.flashlightDoesNotWorkForDevice())) {

			if (localParameters.getSupportedFocusModes().contains(
					"continuous-video"))
				localParameters.setFocusMode("continuous-video");
			camera.setParameters(localParameters);

			setCameraDisplayOrientation(this, 0, camera);
			if (mPreview != null)
				previewLayout.removeView(mPreview);

		} else {

		}

		try {
			mPreview = new CameraPreview(this, camera);

			previewLayout.addView(mPreview);
			camera.startPreview();

		} catch (Exception localException) {
			if (localException != null) {

				finish();
			}
		}
	}

	public static void setCameraDisplayOrientation(Activity activity,
			int cameraId, android.hardware.Camera camera) {
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	PictureCallback myPictureCallback_JPG = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub
			/*
			 * Bitmap bitmapPicture = BitmapFactory.decodeByteArray(arg0, 0,
			 * arg0.length);
			 */

			Uri uriTarget = getContentResolver().insert(
					Media.EXTERNAL_CONTENT_URI, new ContentValues());
			
			OutputStream imageFileOS;
			try {
				imageFileOS = getContentResolver().openOutputStream(uriTarget);
				imageFileOS.write(arg0);
				imageFileOS.flush();
				imageFileOS.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent();
			intent.putExtra("captureString", getRealPathFromURI(uriTarget));
			setResult(Activity.RESULT_OK, intent);
			AndroidCamera.this.finish();
			// camera.startPreview();
		}
	};

	protected void onPause() {
		super.onPause();
		releaseCamera();
		if (this.cameraOrientationEventListener != null)
			this.cameraOrientationEventListener.disable();
	}

	protected void onResume() {
		super.onResume();
		setupCamera();
		if (cameraOrientationEventListener != null)
			cameraOrientationEventListener.enable();
	}

	private static class CameraOrientationEventListener extends
			OrientationEventListener {
		public int currentOrientation = ORIENTATION_UNKNOWN;

		public CameraOrientationEventListener(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onOrientationChanged(int orientation) {
			// TODO Auto-generated method stub
			if (orientation == ORIENTATION_UNKNOWN)
				return;

			if ((orientation >= 315) || (orientation < 45)) {
				currentOrientation = 0;
				return;
			}
			if ((orientation >= 225) && (orientation < 315)) {
				currentOrientation = 270;
				return;
			}
			if ((orientation >= 135) && (orientation < 225)) {
				currentOrientation = 180;
				return;
			}

			currentOrientation = 90;

		}
	}
}