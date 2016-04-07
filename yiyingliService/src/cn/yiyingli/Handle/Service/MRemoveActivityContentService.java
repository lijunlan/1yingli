package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

public class MRemoveActivityContentService extends MMsgService {

	private ContentAndPageService contentAndPageService;

	private PagesService pagesService;

	public ContentAndPageService getContentAndPageService() {
		return contentAndPageService;
	}

	public void setContentAndPageService(ContentAndPageService contentAndPageService) {
		this.contentAndPageService = contentAndPageService;
	}

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("contentId");
	}

	@Override
	public void doit() {
		ContentAndPage contentAndPage = getContentAndPageService().query(getData().getLong("contentId"));
		if (contentAndPage != null && contentAndPage.getTeacher() != null) {
			Pages pages = contentAndPage.getPages();
			float mile = pages.getMile() - contentAndPage.getTeacher().getMile();
			if (mile < 0) {
				mile = 0;
			}
			pages.setMile(mile);
			getPagesService().update(pages);
		}
		getContentAndPageService().remove(getData().getLong("contentId"));
		setResMsg(MsgUtil.getSuccessMsg("remove activity content successfully"));
	}

}
