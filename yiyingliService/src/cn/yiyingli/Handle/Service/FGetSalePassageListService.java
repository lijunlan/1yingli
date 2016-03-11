package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExPassage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Util.MsgUtil;

public class FGetSalePassageListService extends MsgService {

	private PassageService passageService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	@Override
	protected boolean checkData() {
		return true;
	}

	@Override
	public void doit() {
		SuperMap toSend = MsgUtil.getSuccessMap();
		List<Passage> passages = getPassageService().queryListByActivity(PagesService.KEY_SALE_PASSAGE, 1,
				PassageService.MAX_SALE_COUNT);
		ExList exPassages = new ExArrayList();
		for (Passage passage : passages) {
			SuperMap map = new SuperMap();
			ExPassage.assembleSimple(passage, map);
			exPassages.add(map.finish());
		}
		setResMsg(toSend.put("data", exPassages).finishByJson());

	}

}
