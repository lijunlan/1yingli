package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;

public class FSearchServiceProService extends MsgService {

	@Override
	protected boolean checkData() {
		return getData().containsKey("page") && (getData().containsKey("word") || getData().containsKey("tips"))
				|| getData().containsKey("filter");
	}

	@Override
	public void doit() {
		// TODO Auto-generated method stub

	}

}
