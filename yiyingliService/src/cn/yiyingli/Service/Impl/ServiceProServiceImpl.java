package cn.yiyingli.Service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.yiyingli.Dao.ServiceProDao;
import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserLikeServicePro;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.SendMsgToBaiduUtil;

public class ServiceProServiceImpl implements ServiceProService {

	public static short B2S(boolean show) {
		return show ? ServiceProDao.SHOW_KIND_ON : ServiceProDao.SHOW_KIND_OFF;
	}

	private ServiceProDao serviceProDao;

	private UserDao userDao;

	public ServiceProDao getServiceProDao() {
		return serviceProDao;
	}

	public void setServiceProDao(ServiceProDao serviceProDao) {
		this.serviceProDao = serviceProDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void save(ServicePro servicePro) {
		getServiceProDao().save(servicePro);
	}

	@Override
	public Long saveAndReturnId(ServicePro servicePro) {
		getServiceProDao().save(servicePro);
		return servicePro.getId();
	}

	@Override
	public void saveAndPlusNumber(ServicePro servicePro, boolean byManager, boolean updateToBaidu) {
		getServiceProDao().saveAndPlusNumber(servicePro, byManager);
	}

	@Override
	public void remove(ServicePro servicePro) {
		getServiceProDao().remove(servicePro);
	}

	@Override
	public void remove(long id) {
		getServiceProDao().remove(id);
	}

	@Override
	public void update(ServicePro servicePro) {
		getServiceProDao().update(servicePro);
	}

	@Override
	public void updateAddLookNumber(long serviceProId, long number) {
		getServiceProDao().updateAddLookNumber(serviceProId, number);
	}

	@Override
	public void updateAndPlusNumber(ServicePro servicePro, boolean remove, boolean updateToBaidu) {
		getServiceProDao().updateAndPlusNumber(servicePro, remove);
		if (updateToBaidu) {
			SendMsgToBaiduUtil.updateServiceProData(servicePro);
		}
	}

	@Override
	public void updateUserUnlike(long serviceProId, long userId) {
		if (!getServiceProDao().queryCheckLikeUser(serviceProId, userId)) {
			return;
		} else {
			getServiceProDao().removeUserLike(serviceProId, userId);
		}
	}

	@Override
	public boolean updateUserLike(ServicePro servicePro, User user) {
		if (getServiceProDao().queryCheckLikeUser(servicePro.getId(), user.getId())) {
			return false;
		} else {
			UserLikeServicePro userLikeServicePro = new UserLikeServicePro();
			userLikeServicePro.setServicePro(servicePro);
			userLikeServicePro.setUser(user);
			userLikeServicePro.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
			getUserDao().updateLikeServicePro(userLikeServicePro);
			return true;
		}
	}

	@Override
	public ServicePro query(long id, boolean withTeacher) {
		if (!withTeacher) {
			return getServiceProDao().querySimple(id);
		} else {
			return getServiceProDao().queryDetail(id);
		}
	}

	@Override
	public ServicePro queryByUser(long id) {
		return getServiceProDao().queryByUser(id);
	}

	@Override
	public ServicePro queryByTeacherIdAndServiceId(long teacherId, long serviceId) {
		return getServiceProDao().queryByTeacherIdAndServiceId(teacherId, serviceId);
	}

	@Override
	public Boolean queryCheckLikeUser(long serviceProId, long userId) {
		return getServiceProDao().queryCheckLikeUser(serviceProId, userId);
	}

	@Override
	public List<ServicePro> queryList(int page) {
		return getServiceProDao().queryList(page, PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryList(int page, int pageSize) {
		return getServiceProDao().queryList(page, pageSize);
	}

	@Override
	public List<ServicePro> queryList(long[] ids, long teacherId) {
		return getServiceProDao().queryList(ids, teacherId);
	}

	@Override
	public List<ServicePro> queryListByIds(List<Long> ids) {
		long[] idarray = new long[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			idarray[i] = ids.get(i);
		}
		List<ServicePro> servicePros = getServiceProDao().queryListByIds(idarray);
		List<ServicePro> results = new ArrayList<ServicePro>();
		for (int i = 0; i < ids.size(); i++) {
			for (int j = 0; j < servicePros.size(); j++) {
				if (servicePros.get(j).getId().longValue() == ids.get(i).longValue()) {
					results.add(servicePros.get(j));
					break;
				}
			}
		}
		return results;
	}

	@Override
	public List<ServicePro> queryListByRecommand(int page, short state, boolean show) {
		return getServiceProDao().queryListByStateAndShow(page, PAGE_SIZE_RECOMMAND, state, show);
	}

	@Override
	public List<ServicePro> queryListByRecommand(int page, int pageSize, short state, boolean show) {
		return getServiceProDao().queryListByStateAndShow(page, pageSize, state, show);
	}

	@Override
	public List<ServicePro> queryListByState(short state, int page) {
		return getServiceProDao().queryListByState(state, page, MANAGER_PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryListByState(short state, int page, int pageSize) {
		return getServiceProDao().queryListByState(state, page, pageSize);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdAndShow(long teacherId, boolean show, int page, int pageSize) {
		return getServiceProDao().queryListByTeacherIdAndShow(teacherId, B2S(show), page, pageSize);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdAndShow(long teacherId, boolean show, int page) {
		return getServiceProDao().queryListByTeacherIdAndShow(teacherId, B2S(show), page, PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdForUser(long teacherId, int page, int pageSize) {
		return getServiceProDao().queryListByTeacherIdAndShowAndState(teacherId, ServiceProDao.SHOW_KIND_ON,
				ServiceProService.STATE_OK, page, pageSize);
	}

	@Override
	public List<ServicePro> queryListByTeacherId(long teacherId, int page, int pageSize) {
		return getServiceProDao().queryListByTeacherIdAndShow(teacherId, ServiceProDao.SHOW_KIND_NONE, page, pageSize);
	}

	@Override
	public List<ServicePro> queryListByTeacherId(long teacherId, int page) {
		return getServiceProDao().queryListByTeacherIdAndShow(teacherId, ServiceProDao.SHOW_KIND_NONE, page, PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdAndShowAndState(long teacherId, boolean show, short state, int page,
			int pageSize) {
		return getServiceProDao().queryListByTeacherIdAndShowAndState(teacherId, B2S(show), state, page, pageSize);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdAndShowAndState(long teacherId, boolean show, short state, int page) {
		return getServiceProDao().queryListByTeacherIdAndShowAndState(teacherId, B2S(show), state, page, PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdAndState(long teacherId, short state, int page, int pageSize) {
		return getServiceProDao().queryListByTeacherIdAndShowAndState(teacherId, ServiceProDao.SHOW_KIND_NONE, state,
				page, pageSize);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdAndState(long teacherId, short state, int page) {
		return getServiceProDao().queryListByTeacherIdAndShowAndState(teacherId, ServiceProDao.SHOW_KIND_NONE, state,
				page, PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryListByHomePage(int pageSize) {
		return getServiceProDao().queryListByHomePage(pageSize);
	}

	@Override
	public List<ServicePro> queryListByHomePage() {
		return getServiceProDao().queryListByHomePage(HOMEPAGE_PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryListBySale(int page, int pageSize) {
		return getServiceProDao().queryListBySale(page, pageSize);
	}

	@Override
	public List<ServicePro> queryListBySale(int page) {
		return getServiceProDao().queryListBySale(page, SALEPAGE_PAGE_SIZE);
	}

	@Override
	public long queryListBySaleNo() {
		return getServiceProDao().queryListBySaleNo();
	}

	@Override
	public List<ServicePro> queryListByKind(int kind, int page, int pageSize) {
		return getServiceProDao().queryListByKind(kind, page, pageSize);
	}

	@Override
	public List<ServicePro> queryListByKind(int kind) {
		return getServiceProDao().queryListByKind(kind, 1, KIND_PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryLikeListByUserId(long userId, int page, int pageSize) {
		return getServiceProDao().queryLikeListByUserId(userId, page, pageSize);
	}

	@Override
	public List<ServicePro> queryLikeListByUserId(long userId, int page) {
		return getServiceProDao().queryLikeListByUserId(userId, page, USER_PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryListOtherByTeacher(long serviceProId, long teacherId, int page, int pageSize) {
		return getServiceProDao().queryListOtherByTeacher(serviceProId, teacherId, page, pageSize);
	}

	@Override
	public List<ServicePro> queryListOtherByTeacher(long serviceProId, long teacherId, int page) {
		return getServiceProDao().queryListOtherByTeacher(serviceProId, teacherId, page, ABOUT_PAGE_SIZE);
	}
}
