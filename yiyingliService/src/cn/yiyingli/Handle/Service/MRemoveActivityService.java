package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

public class MRemoveActivityService extends MMsgService {

	private PagesService pagesService;

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("pagesId");
	}

	@Override
	public void doit() {
		Pages page = getPagesService().query(getData().getLong("pagesId"));
		if (page == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32016"));
			return;
		}
		page.setRemove(true);
		getPagesService().update(page);
		setResMsg(MsgUtil.getSuccessMsg("remove activity successfully"));
	}

}
