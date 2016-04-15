package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Util.MsgUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MGetActivityTeacherPassage extends MMsgService {

	private PagesService pagesService;

	private ContentAndPageService contentAndPageService;

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("key") && getData().containsKey("state")
				&& getData().containsKey("page");
	}

	@Override
	public void doit() {
		Pages pages = getPagesService().queryByKey(getData().getString("key"));
		int state = getData().getInt("state");
		int page = getData().getInt("page");
		List<Passage> passageList;
		List<Passage> all = getPagesService().queryTeacherPassageListById(pages.getId(), page, 10);
		List<Passage> minus = getPagesService().queryPassageListById(pages.getId(), page, 10);
		Set<Long> set = new HashSet<>();
		for (Passage passage : minus) {
			set.add(passage.getId());
		}
		if (state == 0) {
			passageList = getPagesService().queryTeacherPassageListById(pages.getId(), page, 10);
		} else if (state == 1) {
			passageList = getPagesService().queryPassageListById(pages.getId(), page, 10);
		} else {
			passageList = new ArrayList<>();
			for (Passage passage : all) {
				if (!set.contains(passage.getId())) {
					passageList.add(passage);
				}
			}
		}
		ExList sends = new ExArrayList();
		for (Passage p : passageList) {
			SuperMap map = new SuperMap();
			ExPassage.assembleForManager(p, map);
			if (set.contains(p.getId())) {
				map.put("isStage", 1);
			} else {
				map.put("isStage", 0);
			}
			sends.add(map.finish());
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("data", sends);
		setResMsg(toSend.finishByJson());
	}
}
