package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.TService;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.TServiceService;
import cn.yiyingli.Service.TipService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.toPersistant.PTServiceUtil;
import cn.yiyingli.toPersistant.PTeacherUtil;
import net.sf.json.JSONObject;

public class TEditDetailInfoService extends TMsgService {

	private TServiceService tServiceService;

	private TipService tipService;

	public TServiceService gettServiceService() {
		return tServiceService;
	}

	public void settServiceService(TServiceService tServiceService) {
		this.tServiceService = tServiceService;
	}

	public TipService getTipService() {
		return tipService;
	}

	public void setTipService(TipService tipService) {
		this.tipService = tipService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("bgUrl") && getData().containsKey("simpleinfo")
				&& getData().containsKey("address") && getData().containsKey("timeperweek")
				&& getData().containsKey("price") && getData().containsKey("pricetemp") && getData().containsKey("time")
				&& getData().containsKey("onsale") && getData().containsKey("talkWay")
				&& getData().containsKey("servicetitle") && getData().containsKey("servicecontent")
				&& getData().containsKey("introduce") && getData().containsKey("studyExperiences")
				&& getData().containsKey("workExperiences") && getData().containsKey("tips");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		PTeacherUtil.editTeacherByTeacherDetail(getData(), teacher, getTipService());

		JSONObject map = getData();
		String timeperweek = map.getString("timeperweek");
		String price = map.getString("price");
		String pricetemp = map.getString("pricetemp");
		String time = map.getString("time");
		String onsale = map.getString("onsale");
		String servicetitle = map.getString("servicetitle");
		String servicecontent = map.getString("servicecontent");

		TService tService = teacher.gettService();
		PTServiceUtil.assembleByTeacherEdit(timeperweek, price, pricetemp, time, onsale, servicetitle, servicecontent,
				tService);

		getTeacherService().updateWithDetailInfo(teacher, true);
		setResMsg(MsgUtil.getSuccessMsg("edit teacher info successfully"));
	}

}
