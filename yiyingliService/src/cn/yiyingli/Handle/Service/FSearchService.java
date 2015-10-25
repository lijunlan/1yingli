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
			search.addIndex("yiyinglitest");
			search.setStartHit((p - 1) * TeacherService.PAGE_SIZE_INT);
			search.setHits(TeacherService.PAGE_SIZE_INT);
			if (getData().containsKey("word")) {
				String wd = (String) getData().get("word");
				wd = URLDecoder.decode(wd, "utf-8");
				String[] qss = wd.split(",");
				String query = "";
				for (int i = 0; i < qss.length; i++) {
					String qs = qss[i];
					if (i == 0) {
						query = query + "(default:'" + qs + "'^99 OR simpleinfo:'" + qs + "'^99 OR name:'" + qs
								+ "'^99 OR companyname:'" + qs + "'^99 OR schoolname:'" + qs + "'^99 OR servicetitle:'"
								+ qs + "'^99 OR servicecontent:'" + qs + "'^60 OR introduce:'" + qs + "'^60)";
					} else {
						query = query + "AND (default:'" + qs + "'^99 OR simpleinfo:'" + qs + "'^99 OR name:'" + qs
								+ "'^99 OR companyname:'" + qs + "'^99 OR schoolname:'" + qs + "'^99 OR servicetitle:'"
								+ qs + "'^99 OR servicecontent:'" + qs + "'^60 OR introduce:'" + qs + "'^60)";
					}
				}
				// System.out.println(query);
				search.setQueryString(query);
			} else if (getData().containsKey("tips")) {
				String tips = (String) getData().get("tips");
				tips = URLDecoder.decode(tips, "utf-8");
				String[] ts = tips.split(",");
				StringBuffer sb = new StringBuffer("default:'' OR tipcontent:'" + ts[0] + "'");
				for (int i = 1; i < ts.length; i++) {
					sb.append(" OR tipcontent:'" + ts[i] + "'");
				}
				// System.out.println(sb.toString());
				search.setQueryString(sb.toString());
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
				setResMsg(MsgUtil.getErrorMsg("service error"));
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg("input data is wrong"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg("service error"));
		} catch (IOException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg("service error"));
		}
	}

}
