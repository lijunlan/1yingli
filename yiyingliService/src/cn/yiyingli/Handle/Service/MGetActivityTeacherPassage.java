package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Service.PagesService;

import java.util.List;

public class MGetActivityTeacherPassage extends MMsgService {

	private PagesService pagesService;

	private ContentAndPageService contentAndPageService;

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("key");
	}

	@Override
	public void doit() {
		Pages pages = getPagesService().queryByKey(getData().getString("key"));
		List<Passage> allPassage = getPagesService().queryTeacherPassageListById(pages.getId(), 1, 10);
	}
}
