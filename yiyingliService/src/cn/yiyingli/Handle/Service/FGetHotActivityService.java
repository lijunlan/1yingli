package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExPages;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;

public class FGetHotActivityService extends MsgService {
	private PagesService pagesService;

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("page") && getData().containsKey("pageSize");
	}

	@Override
	public void doit() {
		int p = 0;
		int pageSize;
		try {
			p = getData().getInt("page");
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("52003"));
			return;
		}

		try {
			pageSize = getData().getInt("pageSize");
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("52004"));
			return;
		}

		List<Pages> pages = getPagesService().queryListOrderByWeight(p, pageSize);
		ExList jsonPages = new ExArrayList();
		for (Pages page : pages) {
			SuperMap map = new SuperMap();
			ExPages.assembleSimple(page, map);
			jsonPages.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", jsonPages).put("count", getPagesService().queryTeamSum()).finishByJson());
	}
}
