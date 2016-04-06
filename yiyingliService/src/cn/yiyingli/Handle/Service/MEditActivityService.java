package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

public class MEditActivityService extends MMsgService{

	private PagesService pagesService;

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("pagesId") && getData().containsKey("description")
				&& getData().containsKey("key") && getData().containsKey("weight") && getData().containsKey("content")
				&& getData().containsKey("contact") && getData().containsKey("email");
	}

	@Override
	public void doit() {
		Pages pages = getPagesService().query(getData().getLong("pagesId"));
		pages.setDescription(getData().getString("description"));
		pages.setPagesKey(getData().getString("key"));
		pages.setWeight(getData().getLong("weight"));
		pages.setContent(getData().getString("content"));
		pages.setContact(getData().getString("contact"));
		pages.setEmail(getData().getString("email"));
		getPagesService().save(pages);
		setResMsg(MsgUtil.getSuccessMsg("edit activity successfully"));
	}
}
