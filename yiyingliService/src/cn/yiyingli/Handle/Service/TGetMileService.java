package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Util.MsgUtil;

public class TGetMileService extends TMsgService {

	@Override
	public void doit() {
		setResMsg(MsgUtil.getSuccessMap().put("mile", getTeacher().getMile().longValue())
				.put("subMile", getTeacher().getSubMile()).finishByJson());
	}

}
