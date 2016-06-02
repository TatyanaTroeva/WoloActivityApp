package com.woloactivityapp.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

public class HttpConnectionUtil {

	public InputStream getInputStream(String path) throws IOException {
		Log.i(this.getClass().getName(), path);
		URL url = new URL(path);
		URLConnection conn = url.openConnection();
		return conn.getInputStream();
	}

	public static boolean checkUriExist(Context context, String uri) {
		HttpClient httpClient = new DefaultHttpClient();
		boolean uriExist = false;
		HttpGet get = new HttpGet(uri);
		try {
			HttpResponse httpResponse = httpClient.execute(get);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				uriExist = true;
			}
			httpResponse = null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			get = null;
			httpClient = null;
		}
		return uriExist;
	}
}
