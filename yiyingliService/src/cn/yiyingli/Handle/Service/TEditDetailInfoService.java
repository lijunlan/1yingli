package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Util.MsgUtil;
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
				&& getData().containsKey("address") && getData().containsKey("price") && getData().containsKey("topic")
				&& getData().containsKey("introduce") && getData().containsKey("studyExperiences")
				&& getData().containsKey("workExperiences") && getData().containsKey("tips");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		PTeacherUtil.editTeacherByTeacherDetail(getData(), teacher, getTipService());

		// String onsale = getData().getString("onsale");
		// String topic = getData().getString("topic");
		// String servicecontent = getData().getString("servicecontent");
		// String introduce = getData().getString("introduce");
		//
		// int count = getData().getInt("count");
		// float numeral = Float.valueOf(getData().getString("numeral"));
		// String freeTime = getData().getString("freeTime");
		// String tip = getData().getString("tip");
		// String onshow = getData().getString("onshow");
		// String quantifier = getData().getString("quantifier");
		// float price = Float.valueOf(getData().getString("price"));
		// float priceTemp = Float.valueOf(getData().getString("priceTemp"));

		// ServicePro servicePro = teacher.getServicePros().get(0);
		// PServiceProUtil.assembleByTeacherEdit(count, price, priceTemp,
		// numeral, servicePro.getKind(), freeTime, tip,
		// onshow, onsale, quantifier, servicetitle, servicecontent,
		// servicePro);

		getTeacherService().updateWithDetailInfo(teacher, true);
		setResMsg(MsgUtil.getSuccessMsg("edit teacher info successfully"));
	}

}
