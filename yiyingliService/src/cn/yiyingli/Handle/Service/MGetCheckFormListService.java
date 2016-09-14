package cn.yiyingli.Handle.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.CheckForm;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CheckFormService;
import cn.yiyingli.Util.MsgUtil;

public class MGetCheckFormListService extends MMsgService {

	private CheckFormService checkFormService;

	public CheckFormService getCheckFormService() {
		return checkFormService;
	}

	public void setCheckFormService(CheckFormService checkFormService) {
		this.checkFormService = checkFormService;
	}

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	@Override
	public void doit() {
		List<CheckForm> checkForms = getCheckFormService().queryList();
		ExList exCheckForms = new ExArrayList();
		for (CheckForm checkForm : checkForms) {
			SuperMap map = new SuperMap();
			map.put("createTime", SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(checkForm.getCreateTime()))));
			map.put("checkInfo", checkForm.getCheckInfo());
			map.put("detail", checkForm.getDetail());
			map.put("endTime", checkForm.getEndTime() == null ? ""
					: SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(checkForm.getEndTime()))));
			switch (checkForm.getKind()) {
			case CheckFormService.CHECK_KIND_COMPANY_SHORT:
				map.put("kind", "职位认证");
				break;
			case CheckFormService.CHECK_KIND_IDENTITY_SHORT:
				map.put("kind", "身份认证");
				break;
			case CheckFormService.CHECK_KIND_SCHOOL_SHORT:
				map.put("kind", "学位认证");
				break;
			default:
				map.put("kind", checkForm.getKind());
				break;
			}
			switch (checkForm.getState()) {
			case CheckFormService.CHECK_STATE_CHECKING_SHORT:
				map.put("state", "等待审核");
				break;
			case CheckFormService.CHECK_STATE_FAILED_SHORT:
				map.put("state", "审核未通过");
				break;
			case CheckFormService.CHECK_STATE_SUCCESS_SHORT:
				map.put("state", "审核通过");
				break;
			default:
				map.put("state", checkForm.getState());
				break;
			}
			Teacher teacher = checkForm.getTeacher();
			map.put("teacherId", teacher.getId());
			map.put("name", teacher.getName());
			map.put("phone", teacher.getPhone());
			map.put("email", teacher.getEmail());
			exCheckForms.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", exCheckForms).finishByJson());
	}

}
