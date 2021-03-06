package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

public class MCreateActivityService extends MMsgService {

	private PagesService pagesService;

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	public boolean checkData() {
		return super.checkData() && getData().containsKey("description") && getData().containsKey("key");
	}

	@Override
	public void doit() {
		Pages pages = new Pages();
		pages.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		pages.setDescription(getData().getString("description"));
		pages.setPagesKey(getData().getString("key"));
		pages.setPassageCount(0);
		pages.setServiceProCount(0);
		pages.setTeacherCount(0);
		pages.setRemove(false);
		getPagesService().save(pages);
		setResMsg(MsgUtil.getSuccessMsg("create activity successfully"));
	}

}
