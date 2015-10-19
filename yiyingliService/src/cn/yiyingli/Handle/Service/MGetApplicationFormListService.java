package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.ExchangeData.ExApplicationForm;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class MGetApplicationFormListService extends MsgService {

	private ApplicationFormService applicationFormService;

	private ManagerMarkService managerMarkService;

	public ApplicationFormService getApplicationFormService() {
		return applicationFormService;
	}

	public void setApplicationFormService(ApplicationFormService applicationFormService) {
		this.applicationFormService = applicationFormService;
	}

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		List<ApplicationForm> applicationForms = getApplicationFormService().queryList();
		List<ExApplicationForm> exApplicationForms = new ArrayList<ExApplicationForm>();
		for (ApplicationForm applicationForm : applicationForms) {
			ExApplicationForm exApplicationForm = new ExApplicationForm();
			exApplicationForm.setUpByPersistant(applicationForm);
			exApplicationForms.add(exApplicationForm);
		}
		try {
			String json = Json.getJsonByEx(exApplicationForms);
			setResMsg(MsgUtil.getSuccessMap().put("data",json).finishByJson());
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg(e.getMessage()));
		}
	}

}
