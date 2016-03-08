package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

public class MGetActivityListService extends MMsgService {

	private PagesService pagesService;

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	public boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		int p = getData().getInt("page");
		List<Pages> pages = getPagesService().queryList(p);
		ExList jsonPages = new ExArrayList();
		for (Pages page : pages) {
			SuperMap map = new SuperMap();
			map.put("pagesId", page.getId());
			map.put("createTime", page.getCreateTime());
			map.put("passageCount", page.getPassageCount());
			map.put("serviceProCount", page.getServiceProCount());
			map.put("teacherCount", page.getTeacherCount());
			map.put("description", page.getDescription());
			map.put("key", page.getKey());
			jsonPages.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", jsonPages).finishByJson());
	}

}
