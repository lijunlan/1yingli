package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TServiceService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PTServiceUtil;

public class TChangeTitlePriceService extends TMsgService {

	private TServiceService tServiceService;

	public TServiceService gettServiceService() {
		return tServiceService;
	}

	public void settServiceService(TServiceService tServiceService) {
		this.tServiceService = tServiceService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("time") && getData().containsKey("price")
				&& getData().containsKey("title")
				|| getData().containsKey("onsale") && getData().containsKey("pricetemp");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		try {
			Float price = Float.valueOf((String) getData().get("price"));
			if (price < 0.01)
				price = 0.01F;
			Float time = Float.valueOf((String) getData().get("time"));
			String title = (String) getData().get("title");
			TService tService = teacher.gettService();
			if (getData().containsKey("onsale") && getData().containsKey("pricetemp")) {
				PTServiceUtil.editPriceByTeacher(price, time, title, Boolean.valueOf((String) getData().get("onsale")),
						Float.valueOf((String) getData().get("pricetemp")), tService);
			} else {
				PTServiceUtil.editPriceByTeacher(price, time, title, false, time, tService);
			}
			gettServiceService().update(tService);
			setResMsg(MsgUtil.getSuccessMsg("TService has changed"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("21001"));
		}
	}

}
