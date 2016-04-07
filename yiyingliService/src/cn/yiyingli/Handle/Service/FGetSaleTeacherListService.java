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

public class FGetSaleTeacherListService extends MsgService {

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
	protected boolean checkData() {
		return getData().containsKey("page");
	}

	@Override
	public void doit() {
		String page = (String) getData().get("page");
		int p = 0;
		try {
			p = Integer.parseInt(page);
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("51001"));
			return;
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		List<ContentAndPage> contentAndPages = getContentAndPageService()
				.queryListWithTeacherByKey(PagesService.KEY_SALE_TEACHER, p, TeacherService.SALE_PAGE_SIZE);
		long sum = getPagesService().queryByKey(PagesService.KEY_SALE_TEACHER).getTeacherCount();
		toSend.put("count", sum);
		ExList exTeachers = new ExArrayList();
		for (ContentAndPage contentAndPage : contentAndPages) {
			SuperMap map = new SuperMap();
			ExContentAndPage.assembleTeacherForUser(contentAndPage,map);
			exTeachers.add(map.finish());
		}
		if (exTeachers.size() == TeacherService.SALE_PAGE_SIZE) {
			setResMsg(toSend.put("data", exTeachers).finishByJson());
		} else {
			setResMsg(toSend.put("data", exTeachers).put("page", "max").finishByJson());
		}
	}

}
