package cn.yiyingli.Handle.Service;


import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;

public class MRebuildTeamOrderService extends MMsgService {

	private PagesService pagesService;

	private ContentAndPageService contentAndPageService;

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	public ContentAndPageService getContentAndPageService() {
		return contentAndPageService;
	}

	public void setContentAndPageService(ContentAndPageService contentAndPageService) {
		this.contentAndPageService = contentAndPageService;
	}

	@Override
	public void doit() {
		List<Pages> pagesList = getPagesService().queryListOrderByMile();
		long i = pagesList.size() + 1;
		for (Pages pages : pagesList) {
			pages.setWeight(i);
			getPagesService().update(pages);
			i = i - 1;
		}
		List<ContentAndPage> contentAndPageList = getContentAndPageService().queryListWithTeacherOrderByTeacherMile();
		int j = contentAndPageList.size() + 1;
		for (ContentAndPage contentAndPage : contentAndPageList) {
			contentAndPage.setWeight(j);
			getContentAndPageService().update(contentAndPage);
			j = j - 1;
		}
		setResMsg(MsgUtil.getSuccessMsg("reOrder success"));
	}
}
