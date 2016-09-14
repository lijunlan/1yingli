package cn.yiyingli.Handle.Service;

import java.util.Calendar;
import java.util.List;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Record;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.RecordService;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.MsgUtil;

public class FGetServiceProListByKindService extends MsgService {

	private ServiceProService serviceProService;

	private RecordService recordService;

	private UserMarkService userMarkService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public RecordService getRecordService() {
		return recordService;
	}

	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("kind");
	}

	@Override
	public void doit() {
		int kind = getData().getInt("kind");
		List<ServicePro> servicePros = getServiceProService().queryListByActivity("serviceProShow" + kind, 1,
				ServiceProService.KIND_PAGE_SIZE);
		ExList toSend = new ExArrayList();
		for (ServicePro servicePro : servicePros) {
			SuperMap map = new SuperMap();
			ExServicePro.assembleSimpleServiceProForUser(servicePro, map);
			toSend.add(map.finish());
		}
		saveRecord(kind);
		setResMsg(MsgUtil.getSuccessMap().put("data", toSend).finishByJson());
	}

	/**
	 * 为浏览添加记录
	 * 
	 * @param teacher
	 */
	private void saveRecord(Integer kind) {

		Record r = new Record();
		r.setKind(RecordService.RECORD_KIND_SEE_TIP);
		r.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		r.setIp((String) getData().get("IP"));

		if (getData().containsKey("uid")) {
			String uid = (String) getData().get("uid");
			User user = getUserMarkService().queryUser(uid);
			if (user != null) {
				if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
					r.setType(RecordService.RECORD_TYPE_TEACHER);
					r.setData("kind=" + kind + ",userId=" + user.getId());
				} else {
					r.setType(RecordService.RECORD_TYPE_USER);
					r.setData("kind=" + kind + ",userId=" + user.getId());
				}
			} else {
				r.setType(RecordService.RECORD_TYPE_GUEST);
				r.setData("kind=" + kind);
			}
		} else {
			r.setType(RecordService.RECORD_TYPE_GUEST);
			r.setData("kind=" + kind);
		}

		getRecordService().save(r);
	}

}
