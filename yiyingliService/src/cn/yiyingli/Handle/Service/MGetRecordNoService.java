package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Service.ManagerMarkService;
import cn.yiyingli.Service.RecordService;

public class MGetRecordNoService extends MsgService {

	private ManagerMarkService managerMarkService;

	private RecordService recordService;

	public ManagerMarkService getManagerMarkService() {
		return managerMarkService;
	}

	public void setManagerMarkService(ManagerMarkService managerMarkService) {
		this.managerMarkService = managerMarkService;
	}

	public RecordService getRecordService() {
		return recordService;
	}

	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("mid") || getData().containsKey("kind");
	}

	@Override
	public void doit() {
		// TODO Auto-generated method stub

	}

}
