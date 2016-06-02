package com.woloactivityapp.camera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.woloactivityapp.main.R;

public class GLRenderer implements GLSurfaceView.Renderer,
		Camera.PreviewCallback {
	private static final int LENGTH = 115200;
	private static final int LENGTH_4 = 19200;
	private static final int U_INDEX = 76800;
	private static final int V_INDEX = 96000;
	private final Activity activity;
	private final ByteBuffer frameData;
	private final ShortBuffer mIndices;
	private final short[] mIndicesData;
	private int mPositionLoc;
	private int mProgramObject;
	private int mTexCoordLoc;
	private final FloatBuffer mVertices;
	private final float[] mVerticesData = { -1.0F, 1.0F, 0.0F, 0.0F, 0.0F,
			-1.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, -1.0F, 0.0F, 1.0F, 1.0F,
			1.0F, 1.0F, 0.0F, 1.0F, 0.0F };
	IntBuffer outputBuffer;
	private int previewFrameHeight = 256;
	private int previewFrameWidth = 256;
	private final ByteBuffer uBuffer;
	byte[] uData;
	private int uTexture;
	private final ByteBuffer vBuffer;
	byte[] vData;
	private int vTexture;
	private final ByteBuffer yBuffer;
	private int yTexture;

	public GLRenderer(Activity paramActivity) {
		short[] arrayOfShort = new short[6];
		arrayOfShort[1] = 1;
		arrayOfShort[2] = 2;
		arrayOfShort[4] = 2;
		arrayOfShort[5] = 3;
		this.mIndicesData = arrayOfShort;
		this.frameData = null;
		this.uData = new byte[38400];
		this.vData = new byte[19200];
		this.outputBuffer = IntBuffer.allocate(76800);
		this.activity = paramActivity;
		this.mVertices = ByteBuffer
				.allocateDirect(4 * this.mVerticesData.length)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.mVertices.put(this.mVerticesData).position(0);
		this.mIndices = ByteBuffer.allocateDirect(2 * this.mIndicesData.length)
				.order(ByteOrder.nativeOrder()).asShortBuffer();
		this.mIndices.put(this.mIndicesData).position(0);
		this.yBuffer = GraphicsUtil.makeByteBuffer(115200);
		this.uBuffer = GraphicsUtil.makeByteBuffer(38400);
		this.vBuffer = GraphicsUtil.makeByteBuffer(19200);
	}

	public static int loadProgram(String vShaderStr, String fShaderStr) {
		int[] linked = new int[1];
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vShaderStr);
		if (vertexShader == 0)
			return 0;
		int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fShaderStr);
		if (fragmentShader == 0) {
			GLES20.glDeleteShader(vertexShader);
			return 0;
		}
		int programObject = GLES20.glCreateProgram();
		if (programObject == 0)
			return 0;
		GLES20.glAttachShader(programObject, vertexShader);
		GLES20.glAttachShader(programObject, fragmentShader);
		GLES20.glLinkProgram(programObject);
		GLES20.glGetProgramiv(programObject, GLES20.GL_LINK_STATUS, linked, 0);
		if (linked[0] == 0) {
			Log.e("ESShader", "Error linking program:");
			Log.e("ESShader", GLES20.glGetProgramInfoLog(programObject));
			GLES20.glDeleteProgram(programObject);
			return 0;
		}
		GLES20.glDeleteShader(vertexShader);
		GLES20.glDeleteShader(fragmentShader);
		return programObject;
	}

	public static int loadShader(int type, String shaderSrc) {
		int shader;
		int[] compiled = new int[1];

		// Create the shader object
		shader = GLES20.glCreateShader(type);

		if (shader == 0)
			return 0;

		// Load the shader source
		GLES20.glShaderSource(shader, shaderSrc);

		// Compile the shader
		GLES20.glCompileShader(shader);

		// Check the compile status
		GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);

		if (compiled[0] == 0) {
			// Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
			GLES20.glDeleteShader(shader);
			return 0;
		}
		return shader;
	}

	public static String readTextFileFromRawResource(Context paramContext,
			int paramInt) {
		BufferedReader localBufferedReader = new BufferedReader(
				new InputStreamReader(paramContext.getResources()
						.openRawResource(paramInt)));
		StringBuilder localStringBuilder = new StringBuilder();
		try {
			String str = localBufferedReader.readLine();
			if (str == null)
				return localStringBuilder.toString();
			localStringBuilder.append(str);
			localStringBuilder.append('\n');
		} catch (IOException localIOException) {
		}
		return null;
	}

	public final void onDrawFrame(GL10 paramGL10) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		GLES20.glUseProgram(this.mProgramObject);
		this.mVertices.position(0);
		GLES20.glVertexAttribPointer(this.mPositionLoc, 3, GLES20.GL_FLOAT,
				false, 20, this.mVertices);
		this.mVertices.position(3);
		GLES20.glVertexAttribPointer(this.mTexCoordLoc, 2, GLES20.GL_FLOAT,
				false, 20, this.mVertices);
		GLES20.glEnableVertexAttribArray(this.mPositionLoc);
		GLES20.glEnableVertexAttribArray(this.mTexCoordLoc);
		GLES20.glActiveTexture(33985);
		GLES20.glActiveTexture(33985);
		GLES20.glBindTexture(3553, this.yTexture);
		GLES20.glUniform1i(this.yTexture, 1);
		GLES20.glTexImage2D(3553, 0, 6409, 320, 240, 0, 6409, 5121,
				this.yBuffer);
		GLES20.glTexParameteri(3553, 10241, 9729);
		GLES20.glTexParameteri(3553, 10240, 9729);
		GLES20.glActiveTexture(33986);
		GLES20.glActiveTexture(33986);
		GLES20.glBindTexture(3553, this.uTexture);
		GLES20.glUniform1i(this.uTexture, 2);
		GLES20.glTexImage2D(3553, 0, 6409, 160, 120, 0, 6409, 5121,
				this.uBuffer);
		GLES20.glTexParameteri(3553, 10241, 9729);
		GLES20.glTexParameteri(3553, 10240, 9729);
		GLES20.glActiveTexture(33987);
		GLES20.glActiveTexture(33987);
		GLES20.glBindTexture(3553, this.vTexture);
		GLES20.glUniform1i(this.vTexture, 3);
		GLES20.glTexImage2D(3553, 0, 6409, 160, 120, 0, 6409, 5121,
				this.vBuffer);
		GLES20.glTexParameteri(3553, 10241, 9729);
		GLES20.glTexParameteri(3553, 10240, 9729);
		GLES20.glDrawElements(4, 6, 5123, this.mIndices);
		this.outputBuffer.rewind();
		GLES20.glReadPixels(0, 0, 320, 240, 6408, 5121, this.outputBuffer);
	}

	public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera) {
		this.yBuffer.put(paramArrayOfByte);
		this.yBuffer.position(0);
		System.arraycopy(paramArrayOfByte, 76800, this.uData, 0, 38400);
		this.uBuffer.put(this.uData);
		this.uBuffer.position(0);
		System.arraycopy(paramArrayOfByte, 96000, this.vData, 0, 19200);
		this.vBuffer.put(this.vData);
		this.vBuffer.position(0);
	}

	public void onSurfaceChanged(GL10 paramGL10, int paramInt1, int paramInt2) {
		GLES20.glViewport(0, 0, paramInt1, paramInt2);
	}

	public void onSurfaceCreated(GL10 paramGL10, EGLConfig paramEGLConfig) {
		mProgramObject = loadProgram(
				readTextFileFromRawResource(activity, R.raw.v_simple),
				readTextFileFromRawResource(activity, R.raw.f_convert));

		mPositionLoc = GLES20.glGetAttribLocation(mProgramObject, "a_position");
		mTexCoordLoc = GLES20.glGetAttribLocation(mProgramObject, "a_texCoord");
		GLES20.glEnable(3553);

		this.yTexture = GLES20.glGetUniformLocation(this.mProgramObject,
				"y_texture");
		int[] arrayOfInt1 = new int[1];
		GLES20.glGenTextures(1, arrayOfInt1, 0);
		int i = arrayOfInt1[0];
		GLES20.glActiveTexture(33985);
		GLES20.glBindTexture(3553, i);
		GLES20.glEnable(3553);
		this.uTexture = GLES20.glGetUniformLocation(this.mProgramObject,
				"u_texture");
		int[] arrayOfInt2 = new int[1];
		GLES20.glGenTextures(1, arrayOfInt2, 0);
		int j = arrayOfInt2[0];
		GLES20.glActiveTexture(33986);
		GLES20.glBindTexture(3553, j);
		GLES20.glEnable(3553);
		this.vTexture = GLES20.glGetUniformLocation(this.mProgramObject,
				"v_texture");
		int[] arrayOfInt3 = new int[1];
		GLES20.glGenTextures(1, arrayOfInt3, 0);
		int k = arrayOfInt3[0];
		GLES20.glActiveTexture(33987);
		GLES20.glBindTexture(3553, k);
		GLES20.glClearColor(1.0F, 0.0F, 0.0F, 0.0F);
	}

	public void setPreviewFrameSize(int paramInt1, int paramInt2) {
		this.previewFrameHeight = paramInt2;
		this.previewFrameWidth = paramInt1;
	}
}