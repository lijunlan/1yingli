package cn.yiyingli.Handle;

import cn.yiyingli.Service.DistributorMarkService;
import cn.yiyingli.Service.DistributorService;

public abstract class DMsgService extends MsgService {

	private DistributorService distributorService;

	private DistributorMarkService distributorMarkService;

	public DistributorService getDistributorService() {
		return distributorService;
	}

	public void setDistributorService(DistributorService distributorService) {
		this.distributorService = distributorService;
	}

	public DistributorMarkService getDistributorMarkService() {
		return distributorMarkService;
	}

	public void setDistributorMarkService(DistributorMarkService distributorMarkService) {
		this.distributorMarkService = distributorMarkService;
	}

}
