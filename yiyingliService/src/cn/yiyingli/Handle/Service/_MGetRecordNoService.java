package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Service.RecordService;

public class _MGetRecordNoService extends MMsgService {

	private RecordService recordService;

	public RecordService getRecordService() {
		return recordService;
	}

	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData();// getData().containsKey("kind");
	}

	@Override
	public void doit() {
		// TODO Auto-generated method stub

	}

}
