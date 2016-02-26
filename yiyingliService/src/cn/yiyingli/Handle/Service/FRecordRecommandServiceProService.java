package cn.yiyingli.Handle.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FRecordRecommandServiceProService extends MsgService {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private UserMarkService userMarkService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("uid") && getData().containsKey("now_sid") && getData().containsKey("to_sid")
				&& getData().containsKey("recommend_list");
	}

	@Override
	public void doit() {
		String uid = getData().getString("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("14001"));
			return;
		}
		String now_sid = getData().getString("now_sid");
		String to_sid = getData().getString("to_sid");
		JSONObject toBaidu = new JSONObject();
		toBaidu.put("UserId", user.getId() + "");
		toBaidu.put("ClickItemId", to_sid);
		toBaidu.put("ShowItemId", now_sid);
		toBaidu.put("Action", "recommend");
		JSONObject jsonContext = new JSONObject();
		jsonContext.put("RecommendList", getData().get("recommend_list"));
		jsonContext.put("LogId", uid + to_sid + now_sid);
		toBaidu.put("Context", jsonContext);
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		toBaidu.put("Timestamp", formatter.format(new Date()));
		JSONArray send = new JSONArray();
		send.add(toBaidu);
		String r = SendMsgToBaiduUtil.updataUserClickData(send.toString(),
				"http://ds.recsys.baidu.com/s/142407/276909?token=a4c5f22d60e79cf2779e4d4cff18e5e3");
		if (!(r == null || r.equals(""))) {
			JSONObject ro = JSONObject.fromObject(r);
			if (ro.getInt("Code") != 100) {
				setResMsg(MsgUtil.getErrorMsgByCode("53004"));
				return;
			}
		}
		setResMsg(MsgUtil.getSuccessMsg("Submit data to recommendation engine successfully"));
	}

}
