package cn.yiyingli.Handle.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.aliyun.opensearch.CloudsearchClient;
import com.aliyun.opensearch.CloudsearchSearch;
import com.aliyun.opensearch.object.KeyTypeEnum;

import cn.yiyingli.AliyunUtil.AliyunConfiguration;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FSearchService extends MsgService {

	@Override
	protected boolean checkData() {
		return getData().containsKey("page") && (getData().containsKey("word") || getData().containsKey("tips"))
				|| getData().containsKey("filter");
	}

	@Override
	public void doit() {
		try {
			String page = (String) getData().get("page");

			int p = Integer.valueOf(page);
			Map<String, Object> opts = new HashMap<String, Object>();
			String accessKeyId = AliyunConfiguration.ACCESS_ID;
			String accessKeySecret = AliyunConfiguration.ACCESS_KEY;
			String host = ConfigurationXmlUtil.getInstance().getSettingData().get("searchUrl");
			CloudsearchClient client = new CloudsearchClient(accessKeyId, accessKeySecret, host, opts,
					KeyTypeEnum.ALIYUN);
			CloudsearchSearch search = new CloudsearchSearch(client);
			search.addIndex("yiyingli2test");
			search.setStartHit((p - 1) * TeacherService.PAGE_SIZE_INT);
			search.setHits(TeacherService.PAGE_SIZE_INT);
			search.addFilter("onservice=1");
			String query = "";
			if (getData().containsKey("word")) {
				String wd = (String) getData().get("word");
				wd = URLDecoder.decode(wd, "utf-8");
				if (!"".equals(wd)) {
					// String[] qss = wd.split(",");
					query = "(si:'" + wd + "' OR n:'" + wd + "' OR cn:'" + wd + "'^70 OR sn:'" + wd + "'^70 OR sc:'"
							+ wd + "'^75 OR i:'" + wd + "'^40 OR t:'" + wd + "' OR ta:'" + wd + "' OR p:'" + wd
							+ "'^70 OR d:'" + wd + "'^70 OR m:'" + wd + "'^70)";
					// for (int i = 0; i < qss.length; i++) {
					// String qs = qss[i];
					// if (i == 0) {// default:'" + qs + "' OR
					// query = query + "(si:'" + qs + "' OR n:'" + qs + "' OR
					// cn:'" + qs + "'^70 OR sn:'" + qs
					// + "'^70 OR sc:'" + qs + "'^75 OR i:'" + qs + "'^40 OR
					// t:'" + qs + "' OR ta:'" + qs
					// + "' OR p:'" + qs + "'^70 OR d:'" + qs + "'^70 OR m:'" +
					// qs + "'^70)";
					// } else {
					// query = query + "AND(si:'" + qs + "' OR n:'" + qs + "' OR
					// cn:'" + qs + "'^70 OR sn:'" + qs
					// + "'^70 OR sc:'" + qs + "'^75 OR i:'" + qs + "'^40 OR
					// t:'" + qs + "' OR ta:'" + qs
					// + "' OR p:'" + qs + "'^70 OR d:'" + qs + "'^70 OR m:'" +
					// qs + "'^70)";
					// }
					// }
				}
			}
			search.setQueryString(query);

			if (getData().containsKey("tips")) {
				String tips = (String) getData().get("tips");
				tips = URLDecoder.decode(tips, "utf-8");
				String[] ts = tips.split(",");
				if (ts.length > 1) {
					// StringBuffer sb = new StringBuffer("tc:'" + ts[0] + "'");
					// for (int i = 1; i < ts.length; i++) {
					// sb.append(" OR tc:'" + ts[i] + "'");
					// }
					// search.setQueryString("");
				} else {
					search.addFilter("tipcontent=\"" + ts[0] + "\"");
				}
			}
			search.setFormat("json");

			if (getData().containsKey("filter")) {
				String filter = (String) getData().get("filter");
				if (filter.startsWith("likeno")) {
					if (filter.endsWith("+")) {
						search.addSort("likeno", "+");
					} else {
						search.addSort("likeno", "-");
					}
				} else if (filter.startsWith("price")) {
					if (filter.endsWith("+")) {
						search.addSort("serviceprice", "+");
					} else {
						search.addSort("serviceprice", "-");
					}
				} else if (filter.startsWith("finishno")) {
					if (filter.endsWith("+")) {
						search.addSort("finishno", "+");
					} else {
						search.addSort("finishno", "-");
					}
				}
			}

			search.addSort("RANK");
			String receive = search.search();

			JSONObject jsonReceive = JSONObject.fromObject(receive);
			String status = jsonReceive.getString("status");

			if ("OK".equals(status)) {
				JSONObject result = (JSONObject) jsonReceive.get("result");
				int viewtotal = result.getInt("viewtotal");
				JSONArray items = (JSONArray) result.get("items");
				setResMsg(MsgUtil.getSuccessMap().put("result", items.toString()).put("viewtotal", viewtotal)
						.finishByJson());
			} else {
				setResMsg(MsgUtil.getErrorMsgByCode("53005"));
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("51001"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("53003"));
		} catch (IOException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("53002"));
		}
	}

}
