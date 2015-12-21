package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExApplicationForm;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Service.ApplicationFormService;
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
		List<ApplicationForm> applicationForms = getApplicationFormService().queryList();
		List<ExApplicationForm> exApplicationForms = new ArrayList<ExApplicationForm>();
		for (ApplicationForm applicationForm : applicationForms) {
			ExApplicationForm exApplicationForm = new ExApplicationForm();
			exApplicationForm.setUpByPersistant(applicationForm);
			exApplicationForms.add(exApplicationForm);
		}
		try {
			ExList toSend = new ExArrayList();
			for (ExApplicationForm exApplicationForm : exApplicationForms) {
				toSend.add(exApplicationForm.finish());
			}
			setResMsg(MsgUtil.getSuccessMap().put("data", toSend).finishByJson());
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("31001"));
		}
	}

}
