package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExApplicationForm;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetApplicationFormListService extends MMsgService {

	private ApplicationFormService applicationFormService;

	public ApplicationFormService getApplicationFormService() {
		return applicationFormService;
	}

	public void setApplicationFormService(ApplicationFormService applicationFormService) {
		this.applicationFormService = applicationFormService;
	}

	@Override
	public void doit() {
		super.doit();
		List<ApplicationForm> applicationForms = getApplicationFormService().queryList();
		List<ExApplicationForm> exApplicationForms = new ArrayList<ExApplicationForm>();
		for (ApplicationForm applicationForm : applicationForms) {
			ExApplicationForm exApplicationForm = new ExApplicationForm();
			exApplicationForm.setUpByPersistant(applicationForm);
			exApplicationForms.add(exApplicationForm);
		}
		try {
			String json = Json.getJsonByEx(exApplicationForms);
			setResMsg(MsgUtil.getSuccessMap().put("data", json).finishByJson());
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("31001"));
		}
	}

}
