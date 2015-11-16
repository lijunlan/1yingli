package cn.yiyingli.AliyunUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class GetOSSImageUtil {
	//1e_300w_300h_1c_0i_1o_90Q_1x.jpg
	public static HttpEntity getImage(String key) {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://image.1yingli.cn/" + key + "@!icon");
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				return response.getEntity();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
