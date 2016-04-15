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
	public boolean checkData() {
		return super.checkData() && (getData().containsKey("page") || getData().containsKey("teacherName"));
	}

	@Override
	public void doit() {
		List<ApplicationForm> applicationForms = null;
		if (getData().containsKey("page")) {
			int page = getData().getInt("page");
			if (getData().containsKey("state")) {
				applicationForms = getApplicationFormService().queryList(page, 10, getData().getInt("state"));
			} else {
				applicationForms = getApplicationFormService().queryList(page, 10);
			}
		} else {
			String teacherName = getData().getString("teacherName");
			ApplicationForm applicationForm = getApplicationFormService().queryByTeacherName(teacherName);
			applicationForms = new ArrayList<ApplicationForm>();
			if (null != applicationForm) {
				applicationForms.add(applicationForm);
			}
		}
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
