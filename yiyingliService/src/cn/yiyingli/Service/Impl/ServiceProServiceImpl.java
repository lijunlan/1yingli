package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.ServiceProDao;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;

public class ServiceProServiceImpl implements ServiceProService {

	public static short B2S(boolean show) {
		return show ? ServiceProDao.SHOW_KIND_ON : ServiceProDao.SHOW_KIND_OFF;
	}

	private ServiceProDao serviceProDao;

	public ServiceProDao getServiceProDao() {
		return serviceProDao;
	}

	public void setServiceProDao(ServiceProDao serviceProDao) {
		this.serviceProDao = serviceProDao;
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
	public ServicePro query(long id) {
		return getServiceProDao().querySimple(id);
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
	public List<ServicePro> queryList(long[] ids, long teacherId) {
		return getServiceProDao().queryList(ids, teacherId);
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
	public List<ServicePro> queryListByTeacherIdAndShowAndStateAndStyle(long teacherId, boolean show, short state,
			short style, int page, int pageSize) {
		return getServiceProDao().queryListByTeacherIdAndShowAndStateAndStyle(teacherId, B2S(show), state, style, page,
				pageSize);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdAndShowAndStateAndStyle(long teacherId, boolean show, short state,
			short style, int page) {
		return getServiceProDao().queryListByTeacherIdAndShowAndStateAndStyle(teacherId, B2S(show), state, style, page,
				PAGE_SIZE);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdAndStateAndStyle(long teacherId, short state, short style, int page,
			int pageSize) {
		return getServiceProDao().queryListByTeacherIdAndShowAndStateAndStyle(teacherId, ServiceProDao.SHOW_KIND_NONE,
				state, style, page, pageSize);
	}

	@Override
	public List<ServicePro> queryListByTeacherIdAndStateAndStyle(long teacherId, short state, short style, int page) {
		return getServiceProDao().queryListByTeacherIdAndShowAndStateAndStyle(teacherId, ServiceProDao.SHOW_KIND_NONE,
				state, style, page, PAGE_SIZE);
	}

}
