package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Util.MsgUtil;

public class MEditActivityContentService extends MMsgService {
	private ContentAndPageService contentAndPageService;

	public ContentAndPageService getContentAndPageService() {
		return contentAndPageService;
	}

	public void setContentAndPageService(ContentAndPageService contentAndPageService) {
		this.contentAndPageService = contentAndPageService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("contentAndPageId");
	}

	@Override
	public void doit() {
		long contentAndPageId = getData().getInt("contentAndPageId");
		ContentAndPage contentAndPage = getContentAndPageService().query(contentAndPageId);
		if (getData().containsKey("activityDes")) {
			contentAndPage.setActivityDes(getData().getString("activityDes"));
		}
		if (getData().containsKey("img")) {
			contentAndPage.setImg(getData().getString("img"));
		}
		getContentAndPageService().update(contentAndPage);
		setResMsg(MsgUtil.getSuccessMsg("edit content success"));
	}
}
