package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.Dao.ContentAndPageDao;
import cn.yiyingli.ExchangeData.ExContentAndPage;
import cn.yiyingli.ExchangeData.ExPages;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

public class FGetActivityService extends MsgService {

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
	protected boolean checkData() {
		return getData().containsKey("key") && getData().containsKey("contentStyle") && getData().containsKey("page")
				&& getData().containsKey("pageSize");
	}

	@Override
	public void doit() {
		String key = getData().getString("key");
		int style = getData().getInt("contentStyle");
		int page = getData().getInt("page");
		int pageSize = getData().getInt("pageSize");
		Pages pages = getPagesService().queryByKey(key);
		if (pages == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32016"));
			return;
		}
		ExList toSend = new ExArrayList();
		switch (style) {
			case ContentAndPageDao.STYLE_PASSAGE:
				List<ContentAndPage> passages = getContentAndPageService().queryListWithPassageByKey(key, page, pageSize);
				for (ContentAndPage contentAndPage : passages) {
					SuperMap map = new SuperMap();
					ExContentAndPage.assemblePassageForUser(contentAndPage, map);
					toSend.add(map.finish());
				}
				break;
			case ContentAndPageDao.STYLE_SERVICEPRO:
				List<ContentAndPage> servicePros = getContentAndPageService().queryListWithServiceProByKey(key, page,
						pageSize);
				for (ContentAndPage contentAndPage : servicePros) {
					SuperMap map = new SuperMap();
					ExContentAndPage.assembleServiceProForUser(contentAndPage, map);
					toSend.add(map.finish());
				}
				break;
			case ContentAndPageDao.STYLE_TEACHER:
				List<ContentAndPage> teachers = getContentAndPageService().queryListWithTeacherByKey(key, page, pageSize);
				for (ContentAndPage contentAndPage : teachers) {
					SuperMap map = new SuperMap();
					ExContentAndPage.assembleTeacherForUser(contentAndPage, map);
					toSend.add(map.finish());
				}
				break;
			default:
				setResMsg(MsgUtil.getErrorMsgByCode("53006"));
				return;
		}
		SuperMap pageMap = new SuperMap();
		ExPages.assembleSimple(pages, pageMap);
		setResMsg(MsgUtil.getSuccessMap().put("data", toSend).put("detail", pageMap).finishByJson());
	}

}
