package com.sdll18.Util;

import java.io.File;
import java.io.IOException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class UploadUtil {

	public static void main(String[] args) throws JSONException,
			ClientProtocolException, IOException {
		postFile("/Users/SDLL18/DownLoads/t12.png", "1");
	}

	@SuppressWarnings("resource")
	public static String postFile(String uploadFile, String userId)
			throws ClientProtocolException, IOException, JSONException {
		HttpClient httpclient = new DefaultHttpClient();
		// 设置通信协议版本
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		System.out.println("chopin:" + uploadFile);

		HttpPost httppost = new HttpPost(
				"http://service.1yingli.cn/yiyingliService/upimage");

		File file = new File(uploadFile);
		MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
		ContentBody cbFile = new FileBody(file);
		mpEntity.addPart("file", cbFile);
		mpEntity.addPart("userId", new StringBody(userId));

		httppost.setEntity(mpEntity);
		System.out.println("executing request " + httppost.getRequestLine());

		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		System.out.println(response.getStatusLine());// 通信Ok
		String json = "";
		String path = "";
		if (resEntity != null) {

			json = EntityUtils.toString(resEntity, "utf-8");
			System.out.println(json);
			JSONObject p = null;
			try {
				p = JSONObject.fromObject(json);
				path = (String) p.get("url");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (resEntity != null) {
			resEntity.consumeContent();
		}

		httpclient.getConnectionManager().shutdown();
		return path;
	}

}
