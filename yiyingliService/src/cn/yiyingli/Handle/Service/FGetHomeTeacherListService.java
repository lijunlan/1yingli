package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExContentAndPage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class FGetHomeTeacherListService extends MsgService {

	private ContentAndPageService contentAndPageService;

	public ContentAndPageService getContentAndPageService() {
		return contentAndPageService;
	}

	public void setContentAndPageService(ContentAndPageService contentAndPageService) {
		this.contentAndPageService = contentAndPageService;
	}

	@Override
	protected boolean checkData() {
		return true;
	}

	@Override
	public void doit() {
		List<ContentAndPage> contentAndPages = getContentAndPageService()
				.queryListWithTeacherByKey(PagesService.KEY_HOME_TEACHER, 1, TeacherService.HOME_PAGE_SIZE);
		ExList exTeachers = new ExArrayList();
		for (ContentAndPage contentAndPage : contentAndPages) {
			SuperMap map = new SuperMap();
			ExContentAndPage.assembleTeacherForUser(contentAndPage, map);
			exTeachers.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", exTeachers).finishByJson());
	}

}
