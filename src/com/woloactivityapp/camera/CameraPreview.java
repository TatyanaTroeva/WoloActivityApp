package com.woloactivityapp.camera;

import java.io.IOException;
import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

public class CameraPreview extends GLSurfaceView implements
		SurfaceHolder.Callback {
	private static final String TAG = "CameraPreview";
	private final boolean adjustScreenRatio = true;
	private boolean isReady = false;
	private final Camera mCamera;
	private final SurfaceHolder mHolder;
	private final GLRenderer renderer;

	public CameraPreview(Activity paramActivity, Camera paramCamera) {
		super((Context) new WeakReference(paramActivity).get());
		mCamera = paramCamera;
		renderer = new GLRenderer(
				(Activity) new WeakReference(paramActivity).get());
		setEGLContextClientVersion(2);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(3);
	}

	public boolean isReady() {
		return this.isReady;
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		try {
			Camera.Parameters localParameters = mCamera.getParameters();
			Camera.Size preferredSize = localParameters.getPreferredPreviewSizeForVideo();
			Camera.Size previewSize = localParameters.getPreviewSize();
			int i = View.MeasureSpec.getSize(widthMeasureSpec);
			int j = View.MeasureSpec.getSize(heightMeasureSpec);
			float f1 = (float)preferredSize.height / preferredSize.width;
			int k = (int) (i * (1.0F / f1));
						
			setMeasuredDimension(i, k);
			isReady = true;
			
		} catch (RuntimeException localRuntimeException) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	// ERROR //
	public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1,
			int paramInt2, int paramInt3) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (mHolder.getSurface() == null){
             // preview surface does not exist
             return;
        }
        
        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }
        
        Parameters parameter = mCamera.getParameters();
        int format = parameter.getPreviewFormat();
        Size previewSize = parameter.getPreviewSize();
        Size preferSize = parameter.getPreferredPreviewSizeForVideo();
        if (preferSize == null) {
        	return;
        }
        
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
       } catch (Exception e){
           Log.d("CameraView", "Error starting camera preview: " + e.getMessage());
       }
        
	}

	public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
		try {
			this.mCamera.setPreviewDisplay(paramSurfaceHolder);
			setRenderer(this.renderer);
			this.mCamera.startPreview();
			return;
		} catch (IOException localIOException) {
			
			Log.e("CameraPreview", "Error setting camera preview: "
					+ localIOException.getMessage());
			return;
		} catch (Exception localException) {
		
			Log.e("CameraPreview", "Error setting camera preview: "
					+ localException.getMessage());
		}
	}

	public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {
	}
}