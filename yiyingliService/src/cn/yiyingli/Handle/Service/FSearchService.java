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
			search.addIndex("yiyingli");
			search.setStartHit((p - 1) * TeacherService.PAGE_SIZE_INT);
			search.setHits(TeacherService.PAGE_SIZE_INT);
			String query = "";
			if (getData().containsKey("word")) {
				String wd = (String) getData().get("word");
				wd = URLDecoder.decode(wd, "utf-8");
				String[] qss = wd.split(",");
				// String tq = "";
				for (int i = 0; i < qss.length; i++) {
					String qs = qss[i];
					// if (i == 0) {
					// tq = tq + "'" + qs + "'";
					// } else {
					// tq = tq + "&'" + qs + "'";
					// }
					// query = "default:" + tq + "^99 OR si:" + tq + "^99 OR n:"
					// + tq + "^99 OR cn:" + tq
					// + "^99 OR sn:" + tq + "^99 OR st:" + tq + "^99 OR sc:" +
					// tq + "^50 OR i:" + tq
					// + "^50";
					if (i == 0) {
						query = query + "(default:'" + qs + "' OR si:'" + qs + "' OR n:'" + qs + "' OR cn:'" + qs
								+ "' OR sn:'" + qs + "' OR st:'" + qs + "' OR sc:'" + qs + "'^50 OR i:'" + qs + "'^50)";
					} else {
						query = query + "AND(default:'" + qs + "' OR si:'" + qs + "' OR n:'" + qs + "' OR cn:'" + qs
								+ "' OR sn:'" + qs + "' OR st:'" + qs + "' OR sc:'" + qs + "'^50 OR i:'" + qs + "'^50)";
					}
				}
				// System.out.println(query);
			}
			search.setQueryString(query);

			if (getData().containsKey("tips")) {
				String tips = (String) getData().get("tips");
				tips = URLDecoder.decode(tips, "utf-8");
				String[] ts = tips.split(",");
				if (ts.length > 1) {
					StringBuffer sb = new StringBuffer("default:'' OR tc:'" + ts[0] + "'");
					for (int i = 1; i < ts.length; i++) {
						sb.append(" OR tc:'" + ts[i] + "'");
					}
					// System.out.println(sb.toString());
					search.setQueryString(sb.toString());
				} else {
					search.addFilter("tipcontent=\"" + ts[0] + "\"");
				}
			}
			search.setFormat("json");

			if (getData().containsKey("filter")) {
				String filter = (String) getData().get("filter");
				if (filter.startsWith("likeno")) {
					if (filter.endsWith("+")) {
						// search.setFirstFormulaName("orderbylikenumbera");
						search.addSort("likeno", "+");
					} else {
						// search.setFirstFormulaName("orderbylikenumberd");
						search.addSort("likeno", "-");
					}
				} else if (filter.startsWith("price")) {
					if (filter.endsWith("+")) {
						search.addSort("serviceprice", "+");
						// search.setFirstFormulaName("orderbypricea");
					} else {
						search.addSort("serviceprice", "-");
						// search.setFirstFormulaName("orderbypriced");
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
