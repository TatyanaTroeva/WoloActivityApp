package com.woloactivityapp.camera;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import com.woloactivityapp.main.R;

public class CameraActivity extends Activity {
	public static final int MEDIA_TYPE_VIDEO = 2;
	protected static final long MIN_RECORDING_TIME = 400L;
	private static final String TAG = "MainActivity";
	protected static int cameraToUse = 0;
	private static int currentDisplayRotation = 0;
	protected static boolean isCapturingFirstTime;
	protected static boolean isRecording = false;
	private int cameraOrientation;
	private CameraOrientationEventListener cameraOrientationEventListener;
	private Button captureButton;
	private int cineOrientation = -1;

	int hintsStage;
	private boolean isExiting = false;
	private Camera mCamera;
	private CameraPreview mPreview;
	private MenuItem nextMenuItem;
	private final int numCameras = Camera.getNumberOfCameras();
	private Camera.Size optimalSize;
	private FrameLayout previewLayout;

	private Camera getCameraInstance() {
		try {
			Camera localCamera = Camera.open(cameraToUse);
			return localCamera;
		} catch (Exception localException) {
			
		}
		return null;
	}

	// @Deprecated
	// private int[] getFpsRange() {
	// int i = 20000;
	// Camera.Parameters localParameters = this.mCamera.getParameters();
	// int[] arrayOfInt = new int[2];
	// localParameters.getPreviewFpsRange(arrayOfInt);
	// if (arrayOfInt[0] > i)
	// i = arrayOfInt[0];
	// while (true) {
	// arrayOfInt[0] = i;
	// arrayOfInt[1] = i;
	// return arrayOfInt;
	// if (arrayOfInt[1] < i)
	// i = arrayOfInt[1];
	// }
	// }

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

	private int getOrientationHint() {
		int i = OrientationEventListener.ORIENTATION_UNKNOWN;
		if (cameraOrientationEventListener != null)
			i = cameraOrientationEventListener.currentOrientation;

		if (i == OrientationEventListener.ORIENTATION_UNKNOWN)
			i = 0;

		return (i + cameraOrientation) % 360;
	}

	private Camera.Size getVideoSize() {
		Camera.Parameters localParameters = mCamera.getParameters();
		Camera.Size localSize = getOptimalPreviewSize(
				localParameters.getSupportedPreviewSizes(), 480, 360);
		if (localSize == null)
			localSize = localParameters.getPreferredPreviewSizeForVideo();
		return localSize;
	}

	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
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

	private void setupActionBar() {
		// ActionBar localActionBar = getActionBar();
		// localActionBar.setDisplayHomeAsUpEnabled(true);
		// localActionBar.setDisplayShowHomeEnabled(true);
		// localActionBar.setDisplayUseLogoEnabled(true);
		// localActionBar.setDisplayShowTitleEnabled(true);
		// AppUtils.setActionbarToCreationTheme(localActionBar, getResources());
	}

	private void setupCamera() {
		if (mCamera != null)
			mCamera.release();

		mCamera = getCameraInstance();

		if (this.mCamera == null) {
			finish();
			return;
		}
		Camera.Parameters localParameters = this.mCamera.getParameters();
		optimalSize = getVideoSize();
		localParameters.setPreviewSize(optimalSize.width, optimalSize.height);
		localParameters.setRecordingHint(true);
		localParameters.set("cam_mode", 1);
		if ((localParameters.getFlashMode() != null)
				&& (!FragmentationManager.flashlightDoesNotWorkForDevice())) {

			if (localParameters.getSupportedFocusModes().contains(
					"continuous-video"))
				localParameters.setFocusMode("continuous-video");
			mCamera.setParameters(localParameters);

			setCameraDisplayOrientation(this, cameraToUse, mCamera);
			if (mPreview != null)
				previewLayout.removeView(mPreview);

		} else {

		}

		try {
			mPreview = new CameraPreview(this, mCamera);

			previewLayout.addView(mPreview);
			mCamera.startPreview();
			
		} catch (Exception localException) {
			if (localException != null) {
				
				finish();
			}
		}
	}

	public void finish() {
		isExiting = true;
		
		super.finish();
	}

	protected void onActivityResult(int paramInt1, int paramInt2,
			Intent paramIntent) {

	}

	public void onBackPressed() {
		if ((this.mPreview != null) && (this.mPreview.isReady())) {
			// abandonCapture();
			finish();
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_camera);

		isCapturingFirstTime = true;
		setupActionBar();

		previewLayout = ((FrameLayout) findViewById(R.id.camera_preview));
		// hintsStage = updateHints(0, 0);
		cameraOrientationEventListener = new CameraOrientationEventListener(this);
		
	}


	protected void onPause() {
		super.onPause();
		releaseCamera();
		if (this.cameraOrientationEventListener != null)
			this.cameraOrientationEventListener.disable();
	}

	protected void onResume() {
		super.onResume();
		isRecording = false;
		setupCamera();
		if (cameraOrientationEventListener != null)
			cameraOrientationEventListener.enable();
	}

	protected void onStart() {
		super.onStart();
		// EasyTracker.getInstance().activityStart(this);
	}

	protected void onStop() {
		super.onStart();
		// EasyTracker.getInstance().activityStop(this);
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