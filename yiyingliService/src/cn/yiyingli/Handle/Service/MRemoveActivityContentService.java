package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Util.MsgUtil;

public class MRemoveActivityContentService extends MMsgService {

	private ContentAndPageService contentAndPageService;

	public ContentAndPageService getContentAndPageService() {
		return contentAndPageService;
	}

	public void setContentAndPageService(ContentAndPageService contentAndPageService) {
		this.contentAndPageService = contentAndPageService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("contentId");
	}

	@Override
	public void doit() {
		getContentAndPageService().remove(getData().getLong("contentId"));
		setResMsg(MsgUtil.getSuccessMsg("remove activity content successfully"));
	}

}
