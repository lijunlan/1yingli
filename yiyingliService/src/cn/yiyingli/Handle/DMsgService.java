package cn.yiyingli.Handle;

import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Service.DistributorMarkService;
import cn.yiyingli.Util.MsgUtil;

/**
 * 分销用户专用抽象类</b>已经实现did鉴权，并且已经获取distributor实例
 * 
 * @author sdll18
 *
 */
public abstract class DMsgService extends MsgService {

	private DistributorMarkService distributorMarkService;

	private Distributor distributor;

	public DistributorMarkService getDistributorMarkService() {
		return distributorMarkService;
	}

	public void setDistributorMarkService(DistributorMarkService distributorMarkService) {
		this.distributorMarkService = distributorMarkService;
	}

	protected Distributor getDistributor() {
		return distributor;
	}

	private void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("did");
	}

	@Override
	public boolean validate() {
		String did = (String) getData().get("did");
		Distributor distributor = getDistributorMarkService().queryDistributor(did);
		if (distributor == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("64001"));
			return false;
		}
		setDistributor(distributor);
		return true;
	}

}
