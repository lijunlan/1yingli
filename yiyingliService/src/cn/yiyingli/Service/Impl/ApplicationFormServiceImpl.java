package cn.yiyingli.Service.Impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import cn.yiyingli.Dao.ApplicationFormDao;
import cn.yiyingli.Dao.NotificationDao;
import cn.yiyingli.Dao.ServiceProDao;
import cn.yiyingli.Dao.StudyExperienceDao;
import cn.yiyingli.Dao.TeacherDao;
import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.Dao.UserMarkDao;
import cn.yiyingli.Dao.WorkExperienceDao;
import cn.yiyingli.Persistant.ApplicationForm;
import cn.yiyingli.Persistant.Notification;
import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.UserMark;
import cn.yiyingli.Persistant.WorkExperience;
import cn.yiyingli.Service.ApplicationFormService;
import cn.yiyingli.Util.NotifyUtil;
import cn.yiyingli.Util.WebNotificationUtil;

public class ApplicationFormServiceImpl implements ApplicationFormService {

	private ApplicationFormDao applicationFormDao;

	private NotificationDao notificationDao;

	private ServiceProDao serviceProDao;

	private WorkExperienceDao workExperienceDao;

	private UserDao userDao;

	private StudyExperienceDao studyExperienceDao;

	private TeacherDao teacherDao;

	private UserMarkDao userMarkDao;

	public ApplicationFormDao getApplicationFormDao() {
		return applicationFormDao;
	}

	public void setApplicationFormDao(ApplicationFormDao applicationFormDao) {
		this.applicationFormDao = applicationFormDao;
	}

	public NotificationDao getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}

	public ServiceProDao getServiceProDao() {
		return serviceProDao;
	}

	public void setServiceProDao(ServiceProDao serviceProDao) {
		this.serviceProDao = serviceProDao;
	}

	public WorkExperienceDao getWorkExperienceDao() {
		return workExperienceDao;
	}

	public void setWorkExperienceDao(WorkExperienceDao workExperienceDao) {
		this.workExperienceDao = workExperienceDao;
	}

	public StudyExperienceDao getStudyExperienceDao() {
		return studyExperienceDao;
	}

	public void setStudyExperienceDao(StudyExperienceDao studyExperienceDao) {
		this.studyExperienceDao = studyExperienceDao;
	}

	public TeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserMarkDao getUserMarkDao() {
		return userMarkDao;
	}

	public void setUserMarkDao(UserMarkDao userMarkDao) {
		this.userMarkDao = userMarkDao;
	}

	@Override
	public void save(ApplicationForm applicationForm) {
		// TODO 加入存储默认服务的逻辑
		getApplicationFormDao().save(applicationForm);
		getUserDao().update(applicationForm.getUser());
		// gettServiceDao().save(applicationForm.getTeacher().gettService());
		List<StudyExperience> ses = applicationForm.getTeacher().getStudyExperiences();
		List<WorkExperience> wes = applicationForm.getTeacher().getWorkExperiences();
		for (StudyExperience se : ses) {
			getStudyExperienceDao().save(se);
		}
		for (WorkExperience we : wes) {
			getWorkExperienceDao().save(we);
		}
		Notification notification = NotifyUtil.sendNotification(applicationForm.getUser(), notificationDao,
				"您的入驻申请已经提交，请等待工作人员为您审核。");
		long userId = notification.getToUser().getId();
		UserMark userMark = getUserMarkDao().queryUUID(userId);
		if (userMark != null) {
			try {
				String data = "{'title':'" + notification.getTitle() + "','url':'" + notification.getUrl() + "'}";
				WebNotificationUtil.sendMsgBySingle(userMark.getUuid(), data);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Long saveAndReturnId(ApplicationForm applicationForm) {
		getApplicationFormDao().saveAndReturnId(applicationForm);
		return applicationForm.getId();
	}

	@Override
	public void remove(ApplicationForm applicationForm) {
		getApplicationFormDao().remove(applicationForm);
	}

	@Override
	public void remove(long id) {
		getApplicationFormDao().remove(id);
	}

	@Override
	public void update(ApplicationForm applicationForm) {
		getApplicationFormDao().update(applicationForm);
		getTeacherDao().update(applicationForm.getTeacher());
		getUserDao().update(applicationForm.getUser());
		String msg = "";
		if (applicationForm.getState() == ApplicationFormService.APPLICATION_STATE_SUCCESS_SHORT) {
			msg = "您的入驻申请已经审核完毕,审核结果:通过。请重新登陆，以使用更多功能";
		} else {
			msg = "您的入驻申请已经审核完毕,审核结果:未通过.原因:" + applicationForm.getCheckInfo();
		}
		Notification notification = NotifyUtil.sendNotification(applicationForm.getUser(), notificationDao, msg);
		long userId = notification.getToUser().getId();
		UserMark userMark = getUserMarkDao().queryUUID(userId);
		if (userMark != null) {
			try {
				String data = "{'title':'" + notification.getTitle() + "','url':'" + notification.getUrl() + "'}";
				WebNotificationUtil.sendMsgBySingle(userMark.getUuid(), data);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ApplicationForm query(long id) {
		return getApplicationFormDao().query(id);
	}

	@Override
	public ApplicationForm query(String userId) {
		return getApplicationFormDao().query(userId);
	}

	@Override
	public List<ApplicationForm> queryList() {
		return getApplicationFormDao().queryList();
	}

	@Override
	public ApplicationForm queryByTeacherName(String name) {
		return getApplicationFormDao().queryByTeacherName(name);
	}

	@Override
	public List<ApplicationForm> queryList(int page, int pageSize) {
		return getApplicationFormDao().queryList(page, pageSize);
	}

}
