package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExApplicationForm;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Persistant.Manager;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Util.MsgUtil;

public class MGetApplicationFormService extends MsgService {

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
		return getData().containsKey("mid") && getData().containsKey("afId");
	}

	@Override
	public void doit() {
		String mid = (String) getData().get("mid");
		String afId = (String) getData().get("afId");
		Manager manager = getManagerMarkService().queryManager(mid);
		if (manager == null) {
			setResMsg(MsgUtil.getErrorMsg("manager is not existed"));
			return;
		}
		ApplicationForm ca = getApplicationFormService().query(Long.valueOf(afId));
		if (ca == null) {
			setResMsg(MsgUtil.getErrorMsg("application is not existed"));
			return;
		}
		ExApplicationForm exApplicationForm = new ExApplicationForm();
		exApplicationForm.setUpByPersistant(ca);
		setResMsg(exApplicationForm.toJson());
	}

}
