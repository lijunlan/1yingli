package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;
import cn.yiyingli.toPersistant.PTeacherUtil;
import net.sf.json.JSONObject;

public class TEditSimpleInfoService extends TMsgService {

	private ServiceProService serviceProService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("simpleinfo") && getData().containsKey("address")
				&& getData().containsKey("count") && getData().containsKey("price")
				&& getData().containsKey("priceTemp") && getData().containsKey("numeral")
				&& getData().containsKey("onsale") && getData().containsKey("onshow") && getData().containsKey("kind")
				&& getData().containsKey("freeTime") && getData().containsKey("tip")
				&& getData().containsKey("quantifier") && getData().containsKey("servicetitle");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		PTeacherUtil.editTeacherByTeacherSimple(getData(), teacher);

		JSONObject map = getData();
		int count = map.getInt("count");
		float price = Float.valueOf(map.getString("price"));
		float priceTemp = Float.valueOf(map.getString("priceTemp"));
		float numeral = Float.valueOf(map.getString("numeral"));
		int kind = map.getInt("kind");
		String freeTime = map.getString("freeTime");
		String tip = map.getString("tip");
		String quantifier = map.getString("quantifier");
		String onsale = map.getString("onsale");
		String onshow = map.getString("onshow");
		String servicetitle = map.getString("servicetitle");

		ServicePro servicePro = teacher.getServicePros().get(0);
		PServiceProUtil.assembleByTeacherEdit(count, price, priceTemp, numeral, kind, freeTime, tip, onshow, onsale,
				quantifier, servicetitle, servicePro.getContent(), servicePro);

		getTeacherService().updateWithDetailInfo(teacher, true);
		setResMsg(MsgUtil.getSuccessMsg("edit teacher info successfully"));
	}
}
