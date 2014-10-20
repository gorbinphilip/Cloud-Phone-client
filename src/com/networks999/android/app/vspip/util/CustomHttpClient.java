package com.networks999.android.app.vspip.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class CustomHttpClient {

	/** The time it takes for our client to timeout */
	public static final int HTTP_TIMEOUT = 30 * 2000; // milliseconds
	public static final String BASE_URL = UrlConnections.Base_Url;
	/** Single instance of our HttpClient */
	private static HttpClient mHttpClient;

	/**
	 * Get our single instance of our HttpClient object.
	 * 
	 * @return an HttpClient object with connection parameters set
	 */
	private static HttpClient getHttpClient() {

		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			/*
			 * ClientConnectionManager manager = mHttpClient
			 * .getConnectionManager();
			 */
			final HttpParams params = mHttpClient.getParams().setParameter(
					CoreProtocolPNames.USER_AGENT, "Device");

			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
			// mHttpClient = new DefaultHttpClient(
			// new ThreadSafeClientConnManager(params,
			// manager.getSchemeRegistry()), params);
		}
		return mHttpClient;
	}

	/**
	 * Performs an HTTP Post request to the specified url with the specified
	 * parameters.
	 * 
	 * @param url
	 *            The web address to post the request to
	 * @param postParameters
	 *            The parameters to send via the request
	 * @return The result of the request
	 * @throws Exception
	 */
	public static String executeHttpPost(String url,
			ArrayList<NameValuePair> postParameters) throws Exception {
		BufferedReader in = null;

		try {
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(BASE_URL.trim() + url);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			String result = sb.toString();
			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					return "";
				}
			}
		}
	}

	/**
	 * Performs an HTTP GET request to the specified url.
	 * 
	 * @param url
	 *            The web address to post the request to
	 * @return The result of the request
	 * @throws Exception
	 */
	public static String executeHttpGet(String url) throws Exception {
		BufferedReader in = null;
		try {
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(BASE_URL + url));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			String result = sb.toString();
			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}
	}

	public static String fileUpload(String url,
			HashMap<String, ContentBody> postParameters) {
		BufferedReader in = null;

		try {
			HttpClient client = getHttpClient();
			String postURL = BASE_URL + url;
			Log.e("", postURL);
			HttpPost post = new HttpPost(postURL);
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntity.addPart("uploadFIle", postParameters.get("uploadFIle"));
			//reqEntity.addPart("fileName", postParameters.get("fileName"));
		//	reqEntity.addPart("secretfile", postParameters.get("secretfile"));

			post.setEntity(reqEntity);

			HttpResponse response = client.execute(post);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			String result = sb.toString();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
