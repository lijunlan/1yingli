package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PServiceProUtil;
import cn.yiyingli.toPersistant.PTeacherUtil;

public class TEditDetailInfoService extends TMsgService {

	private ServiceProService serviceProService;

	private TipService tipService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("bgUrl") && getData().containsKey("simpleinfo")
				&& getData().containsKey("address") && getData().containsKey("count") && getData().containsKey("price")
				&& getData().containsKey("priceTemp") && getData().containsKey("numeral")
				&& getData().containsKey("freeTime") && getData().containsKey("onsale")
				&& getData().containsKey("servicetitle") && getData().containsKey("tip")
				&& getData().containsKey("onshow") && getData().containsKey("servicecontent")
				&& getData().containsKey("quantifier") && getData().containsKey("introduce")
				&& getData().containsKey("studyExperiences") && getData().containsKey("workExperiences")
				&& getData().containsKey("tips");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		PTeacherUtil.editTeacherByTeacherDetail(getData(), teacher, getTipService());

		String onsale = getData().getString("onsale");
		String servicetitle = getData().getString("servicetitle");
		String servicecontent = getData().getString("servicecontent");

		int count = getData().getInt("count");
		float numeral = Float.valueOf(getData().getString("numeral"));
		String freeTime = getData().getString("freeTime");
		String tip = getData().getString("tip");
		String onshow = getData().getString("onshow");
		String quantifier = getData().getString("quantifier");
		float price = Float.valueOf(getData().getString("price"));
		float priceTemp = Float.valueOf(getData().getString("priceTemp"));

		ServicePro servicePro = teacher.getServicePros().get(0);
		PServiceProUtil.assembleByTeacherEdit(count, price, priceTemp, numeral, servicePro.getKind(), freeTime, tip,
				onshow, onsale, quantifier, servicetitle, servicecontent, servicePro);

		getTeacherService().updateWithDetailInfo(teacher, true);
		setResMsg(MsgUtil.getSuccessMsg("edit teacher info successfully"));
	}

}
