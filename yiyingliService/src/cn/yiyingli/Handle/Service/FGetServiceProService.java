package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.ExchangeData.ExServicePro;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Record;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.RecordService;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMsgToBaiduUtil;

public class FGetServiceProService extends MsgService {

	private ServiceProService serviceProService;

	private UserMarkService userMarkService;

	private RecordService recordService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public RecordService getRecordService() {
		return recordService;
	}

	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("serviceProId");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().queryByUser(getData().getLong("serviceProId"));
		if (servicePro == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22008"));
			return;
		}
		SuperMap map = MsgUtil.getSuccessMap();
		ExServicePro.assembleDetailServiceForUser(servicePro, map);

		saveRecord(servicePro);

		setResMsg(map.finishByJson());
	}

	/**
	 * 为浏览添加记录
	 * 
	 * @param servicePro
	 */
	private void saveRecord(ServicePro servicePro) {
		getServiceProService().updateAddLookNumber(servicePro.getId(), 1L);
		Record r = new Record();
		r.setKind(RecordService.RECORD_KIND_SEE_PASSAGE);
		r.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		r.setIp((String) getData().get("IP"));

		if (getData().containsKey("uid")) {
			String uid = (String) getData().get("uid");
			User user = getUserMarkService().queryUser(uid);
			if (user != null) {
				if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
					r.setType(RecordService.RECORD_TYPE_TEACHER);
					r.setData("serviceProId=" + servicePro.getId() + ",userId=" + user.getId());
				} else {
					r.setType(RecordService.RECORD_TYPE_USER);
					r.setData("serviceProId=" + servicePro.getId() + ",userId=" + user.getId());
				}
				if ("false".equals(ConfigurationXmlUtil.getInstance().getSettingData().get("debug"))) {
					SendMsgToBaiduUtil.updateServiceProUserTrainDataRead(user.getId() + "", servicePro.getId() + "",
							Calendar.getInstance().getTimeInMillis() + "");
				}
			} else {
				r.setType(RecordService.RECORD_TYPE_GUEST);
				r.setData("serviceProId=" + servicePro.getId());
			}
		} else {
			r.setType(RecordService.RECORD_TYPE_GUEST);
			r.setData("serviceProId=" + servicePro.getId());
		}

		getRecordService().save(r);
	}

}
