package cn.yiyingli.Handle.Service;

import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Util.MsgUtil;

public class TEditSpecialTipService extends TMsgService {

	private TipService tipService;

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("tips");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		List<Object> tips = (List<Object>) getData().get("tips");
		teacher.getTips().clear();
		for (Object t : tips) {
			Long tid = Long.valueOf((String) ((Map<String, Object>) t).get("id"));
			Tip mT = getTipService().query(tid);
			teacher.getTips().add(mT);
		}
		getTeacherService().update(teacher,false);
		setResMsg(MsgUtil.getSuccessMsg("tips has changed"));
	}

}
