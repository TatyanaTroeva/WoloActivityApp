package com.woloactivityapp.camera;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.AsyncTask;

public class CineFrameRecorder {
	protected static final int MAX_FRAMES = 20 * CinemagraphGenerator
			.getMaxTimeCine();
	protected static final boolean PRESERVE_FRAME_ASPECT_RATIO = FragmentationManager.doesDeviceNotPreserveAspectRatio();
	public static boolean shouldAdjustAspectRatio;
	private static ArrayList<Integer> storedList = new ArrayList();
	private final float bitmapSizeRatio = 4 / 3.0f;
	private final ArrayList<Bitmap> bitmaps = new ArrayList();
	private Callback callback;
	private int cineOrientation = 0;
	private float frameSizeRatio;
	private int frames = 0;
	private final Camera.PreviewCallback getPreviewBufferSizeCallback = new Camera.PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera) {

			mCamera.addCallbackBuffer(data);
			mCamera.setPreviewCallbackWithBuffer(previewCallback);
		}
	};
	private boolean hasStartedRecording = false;
	private Camera mCamera;
	private Matrix matrix = null;
	private long normalizedFrameStart = 0L;
	private int numBitmapProcessTasksFinished = 0;
	private Camera.Size optimalSize;
	private final BitmapFactory.Options opts = new BitmapFactory.Options();
	private Camera.Size preferedSize;
	private float preferredSizeRatio;

	private final Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
		float fps;
		boolean shouldStillRecord;

		public void onPreviewFrame(byte[] data, Camera _camera) {
			if (CineFrameRecorder.this.totalFrames >= CineFrameRecorder.MAX_FRAMES)
				shouldStillRecord = false;
			else
				shouldStillRecord = true;

			if (CameraActivity.isRecording == true && shouldStillRecord == true) {
				CineFrameRecorder.storedList.add(Integer.valueOf(NativeBuffer.setData(data)));
				totalFrames = totalFrames + 1;
			}

			if (CameraActivity.isRecording == true && shouldStillRecord == true) {
				if (hasStartedRecording == false) {
					frames = 0;
					normalizedFrameStart = System.currentTimeMillis();
					hasStartedRecording = true;
				}
				frames = frames + 1;
			} else {
				if (hasStartedRecording == true	&& frames > 0) {
					hasStartedRecording = false;
					fps = (1000.0F / ((float) (System.currentTimeMillis() - normalizedFrameStart) / frames));
					// AppUtils.log("FPS", this.fps);
					normalizedFrameStart = System.currentTimeMillis();
				}
			}

			mCamera.addCallbackBuffer(data);
			return;
		}
	};

	private int previewFormat;
	private float scaleX;
	private int totalBitmapProcessingTasks = 2;
	private int totalFrames = 0;
	private int totalFramesProcessed = 0;

	static {
		shouldAdjustAspectRatio = false;
	}

	public CineFrameRecorder() {
		this.opts.inPreferredConfig = Bitmap.Config.RGB_565;
		freeNativeBuffer();
	}

	private void decodeYUV420SP(int[] rgba, byte[] yuv420sp, int width,
			int height) {
		final int frameSize = width * height;

		for (int j = 0, yp = 0; j < height; j++) {
			int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
			for (int i = 0; i < width; i++, yp++) {
				int y = (0xff & ((int) yuv420sp[yp])) - 16;
				if (y < 0)
					y = 0;
				if ((i & 1) == 0) {
					v = (0xff & yuv420sp[uvp++]) - 128;
					u = (0xff & yuv420sp[uvp++]) - 128;
				}

				int y1192 = 1192 * y;
				int r = (y1192 + 1634 * v);
				int g = (y1192 - 833 * v - 400 * u);
				int b = (y1192 + 2066 * u);

				if (r < 0)
					r = 0;
				else if (r > 262143)
					r = 262143;
				if (g < 0)
					g = 0;
				else if (g > 262143)
					g = 262143;
				if (b < 0)
					b = 0;
				else if (b > 262143)
					b = 262143;

				// rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) &
				// 0xff00) | ((b >> 10) & 0xff);
				// rgba, divide 2^10 ( >> 10)
				rgba[yp] = ((r << 14) & 0xff000000) | ((g << 6) & 0xff0000)
						| ((b >> 2) | 0xff00);
			}
		}
	}

	public int[] decodeYUV420SP(byte[] yuv420sp, int width, int height) {
		final int frameSize = width * height;
		int rgb[] = new int[width * height];
		for (int j = 0, yp = 0; j < height; j++) {
			int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
			for (int i = 0; i < width; i++, yp++) {
				int y = (0xff & ((int) yuv420sp[yp])) - 16;
				if (y < 0)
					y = 0;
				if ((i & 1) == 0) {
					v = (0xff & yuv420sp[uvp++]) - 128;
					u = (0xff & yuv420sp[uvp++]) - 128;
				}
				int y1192 = 1192 * y;
				int r = (y1192 + 1634 * v);
				int g = (y1192 - 833 * v - 400 * u);
				int b = (y1192 + 2066 * u);
				if (r < 0)
					r = 0;
				else if (r > 262143)
					r = 262143;
				if (g < 0)
					g = 0;
				else if (g > 262143)
					g = 262143;
				if (b < 0)
					b = 0;
				else if (b > 262143)
					b = 262143;
				rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000)
						| ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
			}
		}
		return rgb;
	}

	private YuvImage getYuvImage(byte[] paramArrayOfByte) {
		if (previewFormat != ImageFormat.NV21) {
			return null;
		}

		return new YuvImage(paramArrayOfByte, ImageFormat.NV21,
				optimalSize.width, optimalSize.height, null);
	}

	private Bitmap rotateAndScale(Bitmap paramBitmap) {
		if (paramBitmap == null)
			return null;

		int i;
		float f;

		if (matrix == null) {

			matrix = new Matrix();
			scaleX = (360.0F / paramBitmap.getHeight());

		}

		if (CameraActivity.cameraToUse != 0) {
			i = -cineOrientation;
		} else {
			i = cineOrientation;
		}

		if (CameraActivity.cameraToUse != 0) {
			f = -scaleX;
		} else {
			f = scaleX;
		}

		if ((CameraActivity.cameraToUse == 0) && (shouldAdjustAspectRatio)) {
			if (preferredSizeRatio <= frameSizeRatio) {
				scaleX *= frameSizeRatio / preferredSizeRatio;
			} else {
				if ((i != 90) && (i != 270))
					scaleX *= preferredSizeRatio / frameSizeRatio;

				f *= preferredSizeRatio / frameSizeRatio;
			}
		}

		matrix.preScale(scaleX, f);
		matrix.preRotate(i);
		Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0,
				paramBitmap.getWidth(), paramBitmap.getHeight(), matrix, true);
		localBitmap.getWidth();
		localBitmap.getHeight();
		return localBitmap;
	}

	public void freeNativeBuffer() {
		Iterator localIterator = storedList.iterator();
		while (localIterator.hasNext()) {
			Integer localInteger = (Integer) localIterator.next();
			try {
				NativeBuffer.free(localInteger.intValue());
			} catch (NullPointerException localNullPointerException) {
			}
		}
	}

	protected void getBitmapsAsync(Callback paramCallback) {
		callback = paramCallback;
		int i = storedList.size();
		if (totalBitmapProcessingTasks > storedList.size())
			totalBitmapProcessingTasks = storedList.size();

		int j = i / this.totalBitmapProcessingTasks;

		ThreadPoolExecutor localThreadPoolExecutor = new ThreadPoolExecutor(
				this.totalBitmapProcessingTasks,
				this.totalBitmapProcessingTasks, 1L, TimeUnit.MINUTES,
				new ArrayBlockingQueue(this.totalBitmapProcessingTasks, true));

		ArrayList localArrayList;
		for (int k = 0; k < totalBitmapProcessingTasks - 1; k++) {
			ProcessBitmapsTask localProcessBitmapsTask = new ProcessBitmapsTask(
					k);

			localArrayList = new ArrayList(storedList.subList(0, j));
			storedList.removeAll(localArrayList);

			localProcessBitmapsTask.executeOnExecutor(localThreadPoolExecutor,
					new List[] { localArrayList });

			localArrayList = storedList;

		}
	}

	protected int getOrientation() {
		return cineOrientation;
	}

	public Camera.PreviewCallback getPreviewCallback() {
		return getPreviewBufferSizeCallback;
	}

	protected int getTotalFrames() {
		return totalFrames;
	}

	public void setCamera(Camera paramCamera) {
		mCamera = paramCamera;
	}

	public void setFrameSize(Camera.Size paramSize) {
		optimalSize = paramSize;
		frameSizeRatio = (optimalSize.width / optimalSize.height);
	}

	protected void setOrientation(int paramInt) {
		cineOrientation = paramInt;
	}

	public void setPreferedFrameSize(Camera.Size paramSize) {
		preferedSize = paramSize;
		preferredSizeRatio = (preferedSize.width / preferedSize.height);
	}

	public void setPreviewFormat(int paramInt) {
		previewFormat = paramInt;
	}

	public static abstract interface Callback {
		public abstract void onFrameProcessed(int paramInt1, int paramInt2);

		public abstract void onProcessed(ArrayList<Bitmap> paramArrayList);
	}

	private class ProcessBitmapsTask extends
			AsyncTask<List, Integer, ArrayList<Bitmap>> {
		ByteArrayOutputStream baos = null;
		byte[] jpeg = null;
		public int taskNum = 0;

		public ProcessBitmapsTask(int arg2) {
			taskNum = arg2;
		}

		private Bitmap createBitmapFromYuvImage(YuvImage paramYuvImage) {
			if (baos == null)
				baos = new ByteArrayOutputStream();
			else
				baos.reset();

			int i = optimalSize.width;
			int j = optimalSize.height;
			if ((CameraActivity.cameraToUse == 0) && (shouldAdjustAspectRatio))
				i = (int) (i * (frameSizeRatio / preferredSizeRatio));
			paramYuvImage.compressToJpeg(new Rect(0, 0, i, j), 100, baos);
			jpeg = baos.toByteArray();
			return BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length, opts);
		}

		protected ArrayList<Bitmap> doInBackground(List[] paramArrayOfList) {
			Thread.currentThread().setName(
					"converting bitmaps task #" + this.taskNum);
			List localList = paramArrayOfList[0];
			ArrayList localArrayList = new ArrayList();

			while ((localList != null) && (localList.size() > 0)) {
				int i = ((Integer) localList.remove(0)).intValue();
				try {
					Bitmap localBitmap = createBitmapFromYuvImage(CineFrameRecorder.this
							.getYuvImage(NativeBuffer.getData(i)));
					NativeBuffer.free(i);
					localArrayList.add(rotateAndScale(localBitmap));
					CineFrameRecorder localCineFrameRecorder = CineFrameRecorder.this;
					localCineFrameRecorder.totalFramesProcessed = (1 + localCineFrameRecorder.totalFramesProcessed);
					Integer[] arrayOfInteger = new Integer[1];
					arrayOfInteger[0] = Integer.valueOf(totalFramesProcessed);
					publishProgress(arrayOfInteger);
				} catch (NullPointerException localNullPointerException) {

				}
			}

			return localArrayList;
		}

		protected void onPostExecute(ArrayList<Bitmap> paramArrayList) {
			CineFrameRecorder localCineFrameRecorder = CineFrameRecorder.this;
			localCineFrameRecorder.numBitmapProcessTasksFinished = (1 + localCineFrameRecorder.numBitmapProcessTasksFinished);

			if (taskNum == 0) {
				bitmaps.addAll(0, paramArrayList);
				if (numBitmapProcessTasksFinished >= totalBitmapProcessingTasks)
					callback.onProcessed(bitmaps);
			} else {
				bitmaps.addAll(paramArrayList);
			}
		}

		protected void onProgressUpdate(Integer[] paramArrayOfInteger) {
			callback.onFrameProcessed(totalFramesProcessed, totalFrames);
		}
	}
}
