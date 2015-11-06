package cn.yiyingli.Handle.Service;

import org.springframework.web.util.HtmlUtils;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.MsgUtil;

public class TChangeBGService extends TMsgService {

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("bgurl");
	}

	@Override
	public void doit() {
		super.doit();
		Teacher teacher = getTeacher();
		String url = (String) getData().get("bgurl");
		url = HtmlUtils.htmlEscape(url);
		teacher.setBgUrl(url);
		getTeacherService().update(teacher);
		setResMsg(MsgUtil.getSuccessMsg("background url has changed"));
	}

}
