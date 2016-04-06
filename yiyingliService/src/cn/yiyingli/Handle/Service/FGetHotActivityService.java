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

	@Override
	protected boolean checkData() {
		return getData().containsKey("page");
	}

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	@Override
	public void doit() {
		int p = getData().getInt("page");
		List<Pages> pages = getPagesService().queryListOrderByWeight(p);
		ExList jsonPages = new ExArrayList();
		for (Pages page : pages) {
			SuperMap map = new SuperMap();
			ExPages.assembleSimple(page,map);
			jsonPages.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", jsonPages).finishByJson());
	}
}
