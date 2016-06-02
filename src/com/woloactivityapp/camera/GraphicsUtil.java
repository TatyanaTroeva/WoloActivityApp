package com.woloactivityapp.camera;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GraphicsUtil {
	public static ByteBuffer makeByteBuffer(int paramInt) {
		ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(paramInt);
		localByteBuffer.position(0);
		return localByteBuffer;
	}

	public static ByteBuffer makeByteBuffer(byte[] paramArrayOfByte) {
		ByteBuffer localByteBuffer = ByteBuffer
				.allocateDirect(paramArrayOfByte.length);
		localByteBuffer.order(ByteOrder.nativeOrder());
		localByteBuffer.put(paramArrayOfByte);
		localByteBuffer.position(0);
		return localByteBuffer;
	}

	public static ByteBuffer makeByteBuffer(byte[] paramArrayOfByte,
			int paramInt) {
		ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(paramInt);
		localByteBuffer.order(ByteOrder.nativeOrder());
		localByteBuffer.put(paramArrayOfByte, 0, paramInt);
		localByteBuffer.position(0);
		return localByteBuffer;
	}

	public static FloatBuffer makeFloatBuffer(float[] paramArrayOfFloat) {
		ByteBuffer localByteBuffer = ByteBuffer
				.allocateDirect(4 * paramArrayOfFloat.length);
		localByteBuffer.order(ByteOrder.nativeOrder());
		FloatBuffer localFloatBuffer = localByteBuffer.asFloatBuffer();
		localFloatBuffer.put(paramArrayOfFloat);
		localFloatBuffer.position(0);
		return localFloatBuffer;
	}
}