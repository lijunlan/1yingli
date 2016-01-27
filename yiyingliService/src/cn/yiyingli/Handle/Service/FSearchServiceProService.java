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
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FSearchServiceProService extends MsgService {

	@Override
	protected boolean checkData() {
		return getData().containsKey("page") && (getData().containsKey("word") || getData().containsKey("tips"))
				|| getData().containsKey("sort");
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
			search.addIndex("yiyingli2_1use");
			search.setStartHit((p - 1) * TeacherService.PAGE_SIZE_INT);
			search.setHits(TeacherService.PAGE_SIZE_INT);
			search.addFilter("onservice=1");
			String query = "";
			if (getData().containsKey("word")) {
				String wd = (String) getData().get("word");
				wd = URLDecoder.decode(wd, "utf-8");
				if (!"".equals(wd)) {
					query = "n:'" + wd + "'^80 OR co:'" + wd + "'^70 OR mw:'" + wd + "' OR st:'" + wd + "'";
				}
			}
			search.setQueryString(query);

			if (getData().containsKey("tips") && !getData().getString("tips").equals("")) {
				String tips = (String) getData().get("tips");
				tips = URLDecoder.decode(tips, "utf-8");
				String[] ts = tips.split(",");
				if (ts.length == 1) {
					search.addFilter("tip=" + Integer.valueOf(ts[0]));
				}
			}
			search.setFormat("json");

			if (getData().containsKey("sort")) {
				String sort = (String) getData().get("sort");
				if (sort.startsWith("likeno")) {
					if (sort.endsWith("+")) {
						search.addSort("servicelikeno", "+");
					} else {
						search.addSort("servicelikeno", "-");
					}
				} else if (sort.startsWith("price")) {
					if (sort.endsWith("+")) {
						search.setFormulaName("pricerankasc");
						// search.addSort("serviceprice", "+");
					} else {
						search.setFormulaName("pricerankdesc");
						// search.addSort("serviceprice", "-");
					}
				} else if (sort.startsWith("finishno")) {
					if (sort.endsWith("+")) {
						search.addSort("servicefinishno", "+");
					} else {
						search.addSort("servicefinishno", "-");
					}
				}
			}

			search.addSort("RANK");
			LogUtil.info("query-->>" + query, getClass());
			String receive = search.search();
			LogUtil.info("search result>>" + receive, getClass());

			JSONObject jsonReceive = JSONObject.fromObject(receive);
			String status = jsonReceive.getString("status");

			if ("OK".equals(status)) {
				JSONObject result = jsonReceive.getJSONObject("result");
				int viewtotal = result.getInt("viewtotal");
				JSONArray items = result.getJSONArray("items");
				setResMsg(MsgUtil.getSuccessMap().put("result", items).put("viewtotal", viewtotal).finishByJson());
			} else {
				setResMsg(MsgUtil.getErrorMsgByCode("53005"));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("51001"));
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
