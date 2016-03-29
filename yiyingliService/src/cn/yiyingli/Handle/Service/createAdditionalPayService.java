package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.UMsgService;

public class createAdditionalPayService extends UMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("orderId") && getData().containsKey("money")
				&& getData().containsKey("reason");
	}

	@Override
	public void doit() {
		
	}
}
