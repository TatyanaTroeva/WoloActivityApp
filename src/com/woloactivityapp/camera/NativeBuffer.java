package com.woloactivityapp.camera;

import android.graphics.Bitmap;
import java.util.HashMap;

public class NativeBuffer {
	static int bytes;
	static HashMap<Integer, Integer> pointers = new HashMap<Integer, Integer>();

	static {
		System.loadLibrary("nativebuffer");
	}

	public static void free(int paramInt) {
		int i = ((Integer) pointers.get(Integer.valueOf(paramInt))).intValue();
		bytes -= i;
		pointers.remove(Integer.valueOf(paramInt));
		free2(paramInt);
	}

	private static native void free2(int paramInt);

	public static void getBitmapData(int paramInt, Bitmap paramBitmap) {
		getBitmapData2(paramInt, paramBitmap);
	}

	private static native void getBitmapData2(int paramInt, Bitmap paramBitmap);

	public static int getBytes() {
		return bytes;
	}

	public static byte[] getData(int paramInt) {
		return getData(paramInt,
				((Integer) pointers.get(Integer.valueOf(paramInt))).intValue());
	}

	private static native byte[] getData(int paramInt1, int paramInt2);

	public static int[] getIntData(int paramInt) {
		return getIntData(
				paramInt,
				((Integer) pointers.get(Integer.valueOf(paramInt))).intValue() / 4);
	}

	private static native int[] getIntData(int paramInt1, int paramInt2);

	public static native int getMemFree();

	public static native int getMemTotal();

	public static int setBitmapData(Bitmap paramBitmap) {
		bytes += paramBitmap.getRowBytes() * paramBitmap.getHeight();
		int i = setBitmapData2(paramBitmap);
		pointers.put(
				Integer.valueOf(i),
				Integer.valueOf(paramBitmap.getRowBytes()
						* paramBitmap.getHeight()));
		return i;
	}

	private static native int setBitmapData2(Bitmap paramBitmap);

	public static int setData(byte[] data) {
		bytes += data.length;
		int i = setData(data, data.length);
		pointers.put(Integer.valueOf(i),
				Integer.valueOf(data.length));
		return i;
	}

	private static native int setData(byte[] data, int length);

	public static int setIntData(int[] data) {
		bytes += 4 * data.length;
		int i = setIntData(data, data.length);
		pointers.put(Integer.valueOf(i),
				Integer.valueOf(4 * data.length));
		return i;
	}

	private static native int setIntData(int[] paramArrayOfInt, int paramInt);
}