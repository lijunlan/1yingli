package cn.yiyingli.Handle.Service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.aliyun.opensearch.CloudsearchClient;
import com.aliyun.opensearch.CloudsearchSuggest;
import com.aliyun.opensearch.object.KeyTypeEnum;

import cn.yiyingli.AliyunUtil.AliyunConfiguration;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FGetSearchServiceProHintService extends MsgService {

	@Override
	protected boolean checkData() {
		return getData().containsKey("word");
	}

	@Override
	public void doit() {
		try {
			Map<String, Object> opts = new HashMap<String, Object>();
			String accessKeyId = AliyunConfiguration.ACCESS_ID;
			String accessKeySecret = AliyunConfiguration.ACCESS_KEY;
			String host = ConfigurationXmlUtil.getInstance().getSettingData().get("searchHintUrl");
			CloudsearchClient client = new CloudsearchClient(accessKeyId, accessKeySecret, host, opts,
					KeyTypeEnum.ALIYUN);

			String indexName = "yiyingli2_1use";
			String suggestName = "yiyingli2_1hint";
			CloudsearchSuggest suggest = new CloudsearchSuggest(indexName, suggestName, client);

			suggest.setHit(10);
			String word = (String) getData().get("word");
			suggest.setQuery(word);
			String result = suggest.search();

			JSONObject jsonResult = JSONObject.fromObject(result);
			List<String> suggestions = new ArrayList<String>();

			if (!jsonResult.has("errors")) {
				JSONArray itemsJsonArray = (JSONArray) jsonResult.get("suggestions");
				for (int i = 0; i < itemsJsonArray.size(); i++) {
					JSONObject item = (JSONObject) itemsJsonArray.get(i);
					suggestions.add(item.getString("suggestion"));
				}
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("result", suggestions);
				ret.put("status", "OK");
				setResMsg(JSONObject.fromObject(ret).toString());
			} else {
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("errors", "service error");
				ret.put("status", "FAIL");
				setResMsg(JSONObject.fromObject(ret).toString());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("53002"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("53003"));
		} catch (IOException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("53002"));
		}
	}

}
