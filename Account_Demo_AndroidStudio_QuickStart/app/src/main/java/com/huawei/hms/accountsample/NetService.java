package com.huawei.hms.accountsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class NetService extends AsyncTask<String, Integer, Bitmap> {
	private URLPostHandler urlPostHandler;

	public NetService(URLPostHandler urlPostHandler) {
		this.urlPostHandler = urlPostHandler;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		super.onPostExecute(bitmap);
		if (this.urlPostHandler != null && bitmap != null) {
			this.urlPostHandler.PostHandler(bitmap);
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled(Bitmap bitmap) {
		super.onCancelled(bitmap);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	public Bitmap getBitmapFromURL(String src) {
		try {
			java.net.URL url = new java.net.URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(2 * 1000);
			connection.connect();
			connection.setRequestMethod("GET");
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(new BufferedInputStream(input));
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		return getBitmapFromURL(params[0]);
	}

}
