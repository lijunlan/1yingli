package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExApplicationForm;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Util.MsgUtil;

public class MGetApplicationFormService extends MMsgService {

	private ApplicationFormService applicationFormService;

	public ApplicationFormService getApplicationFormService() {
		return applicationFormService;
	}

	public void setApplicationFormService(ApplicationFormService applicationFormService) {
		this.applicationFormService = applicationFormService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("afId");
	}

	@Override
	public void doit() {
		super.doit();
		String afId = (String) getData().get("afId");
		ApplicationForm ca = getApplicationFormService().query(Long.valueOf(afId));
		if (ca == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32003"));
			return;
		}
		ExApplicationForm exApplicationForm = new ExApplicationForm();
		exApplicationForm.setUpByPersistant(ca);
		setResMsg(exApplicationForm.toJson());
	}

}
