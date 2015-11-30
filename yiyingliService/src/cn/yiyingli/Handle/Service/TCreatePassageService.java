package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PPassageUtil;

public class TCreatePassageService extends TMsgService {

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("title") && getData().containsKey("tag")
				&& getData().containsKey("content") && getData().containsKey("imageUrl")
				&& getData().containsKey("summary");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		String title = (String) getData().get("title");
		String tag = (String) getData().get("tag");
		String content = (String) getData().get("content");
		String imageUrl = (String) getData().get("imageUrl");
		String summary = getData().getString("summary");

		Passage passage = new Passage();
		PPassageUtil.toSavePassage(teacher, title, tag, summary, content, imageUrl, passage);

		getPassageService().save(passage);
		setResMsg(MsgUtil.getSuccessMsg("create passage successfully"));
	}

}
