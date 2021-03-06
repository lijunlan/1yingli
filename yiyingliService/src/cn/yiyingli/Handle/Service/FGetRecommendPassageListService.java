package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.Dao.PassageDao;
import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FGetRecommendPassageListService extends MsgService {

	private UserMarkService userMarkService;

	private PassageService passageService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("passageId");
	}

	@Override
	public void doit() {
		String passageId =  getData().getString("passageId");
		String result = SendMsgToBaiduUtil.getRecommendPassageListAbout(passageId);
		getPassageInfo(result);
	}

	private void getPassageInfo(String result) {
		JSONObject r = JSONObject.fromObject(result);
		if (r.getInt("Code") != 300 && r.getInt("Code") != 301 && r.getInt("Code") != 302 && r.getInt("Code") != 303
				&& r.getInt("Code") != 304) {
			setResMsg(MsgUtil.getErrorMsgByCode("53001"));
			return;
		}
		JSONArray ra = r.getJSONArray("Results");
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < ra.size(); i++) {
			JSONObject jobj = ra.getJSONObject(i);
			String pid = jobj.getString("ItemId");
			ids.add(Long.valueOf(pid));
		}
		List<Passage> passages = null;
		if (ids.size() > 0) {
			passages = getPassageService().queryListByIds(ids);
			if (passages.size() == 0) {
				passages = getPassageService().queryListByRecommand(0, PassageDao.PASSAGE_STATE_OK, true);
			}
		} else {
			passages = getPassageService().queryListByRecommand(0, PassageDao.PASSAGE_STATE_OK, true);
		}
		SuperMap map = MsgUtil.getSuccessMap();
		JSONArray jsonPassages = new JSONArray();
		for (Passage passage : passages) {
			if (passage.getId().longValue() == getData().getLong("passageId")) {
				continue;
			}
			SuperMap m = new SuperMap();
			ExPassage.assembleSimple(passage, m);
			jsonPassages.add(m.finish());
		}
		map.put("data", jsonPassages.toString());
		setResMsg(map.finishByJson());
	}
}
