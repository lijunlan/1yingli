package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

public class MEditActivityService extends MMsgService {

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
				&& getData().containsKey("key") && getData().containsKey("content")
				&& getData().containsKey("contact") && getData().containsKey("email") && getData().containsKey("bgImg");
	}

	@Override
	public void doit() {
		Pages pages = getPagesService().query(getData().getLong("pagesId"));
		pages.setDescription(getData().getString("description"));
		pages.setPagesKey(getData().getString("key"));
		pages.setContent(getData().getString("content"));
		pages.setContact(getData().getString("contact"));
		pages.setEmail(getData().getString("email"));
		pages.setImg(getData().getString("img"));
		pages.setBgImg(getData().getString("bgImg"));
		if(getData().containsKey("slogan")) {
			pages.setSlogan(getData().getString("slogan"));
		}
		if(getData().containsKey("weight")) {
			pages.setWeight(getData().getLong("weight"));
		}
		getPagesService().update(pages);
		setResMsg(MsgUtil.getSuccessMsg("edit activity successfully"));
	}
}
