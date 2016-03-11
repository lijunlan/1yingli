package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExContentAndPage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Util.MsgUtil;

public class MGetActivityService extends MMsgService {

	private ContentAndPageService contentAndPageService;

	public ContentAndPageService getContentAndPageService() {
		return contentAndPageService;
	}

	public void setContentAndPageService(ContentAndPageService contentAndPageService) {
		this.contentAndPageService = contentAndPageService;
	}

	@Override
	public boolean checkData() {
		return super.checkData() && getData().containsKey("pagesId");
	}

	@Override
	public void doit() {
		List<ContentAndPage> contentAndPages = getContentAndPageService()
				.queryListByPages(getData().getLong("pagesId"));
		ExList jsonContentAndPage = new ExArrayList();
		for (ContentAndPage contentAndPage : contentAndPages) {
			SuperMap map = new SuperMap();
			ExContentAndPage.assembleForManager(contentAndPage, map);
			jsonContentAndPage.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", jsonContentAndPage).finishByJson());
	}

}
