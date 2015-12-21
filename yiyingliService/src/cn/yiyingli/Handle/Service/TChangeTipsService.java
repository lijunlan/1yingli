package cn.yiyingli.Handle.Service;

import java.util.List;
import java.util.Map;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Util.MsgUtil;

public class TChangeTipsService extends TMsgService {

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
		long tipMark = 0;
		for (Object t : tips) {
			Long tid = Long.valueOf((String) ((Map<String, Object>) t).get("id"));
			tipMark = tipMark | tid;
			Tip mT = getTipService().query(tid);
			teacher.getTips().add(mT);
		}
		teacher.setTipMark(tipMark);
		getTeacherService().update(teacher, false);
		setResMsg(MsgUtil.getSuccessMsg("tips have changed"));
	}

}
