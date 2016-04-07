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
		pages.setMile(0F);
		pages.setServiceProCount(0);
		pages.setTeacherCount(0);
		if(getData().containsKey("weight")) {
			pages.setWeight(getData().getLong("weight"));
		} else {
			pages.setWeight(0L);
		}
		if(getData().containsKey("content")) {
			pages.setContent(getData().getString("content"));
		} else {
			pages.setContent("");
		}
		if(getData().containsKey("contact")) {
			pages.setContact(getData().getString("contact"));
		} else {
			pages.setContact("");
		}
		if(getData().containsKey("email")) {
			pages.setEmail(getData().getString("email"));
		} else {
			pages.setEmail("");
		}
		if(getData().containsKey("bgImg")) {
			pages.setBgImg(getData().getString("bgImg"));
		} else {
			pages.setBgImg("");
		}
		pages.setRemove(false);
		getPagesService().save(pages);
		setResMsg(MsgUtil.getSuccessMsg("create activity successfully"));
	}

}
