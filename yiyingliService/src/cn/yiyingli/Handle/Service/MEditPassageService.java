package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PPassageUtil;

public class MEditPassageService extends MMsgService {

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("passageId") && getData().containsKey("title")
				&& getData().containsKey("tag") && getData().containsKey("content") && getData().containsKey("imageUrl")
				&& getData().containsKey("summary") && getData().containsKey("onshow");
	}

	@Override
	public void doit() {
		Passage passage = getPassageService().queryWithTeacherByManager(getData().getLong("passageId"));
		if (passage == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22006"));
			return;
		}

		String title = getData().getString("title");
		String tag = getData().getString("tag");
		String content = getData().getString("content");
		String imageUrl = getData().getString("imageUrl");
		String summary = getData().getString("summary");
		boolean onshow = getData().getBoolean("onshow");

		PPassageUtil.toEditPassageByManager(title, tag, summary, content, imageUrl, passage, onshow);

		getPassageService().update(passage, false, true);

		setResMsg(MsgUtil.getSuccessMsg("edit passage successfully"));
	}

}
