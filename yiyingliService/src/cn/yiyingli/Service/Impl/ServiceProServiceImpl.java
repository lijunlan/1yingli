package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.ServiceProDao;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.ServiceProService;

public class ServiceProServiceImpl implements ServiceProService {

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
	public ServicePro queryByTeacherIdAndServiceId(long teacherId, long serviceId) {
		return getServiceProDao().queryByTeacherIdAndServiceId(teacherId, serviceId);
	}

	@Override
	public List<ServicePro> queryList(long[] ids, long teacherId) {
		return getServiceProDao().queryList(ids, teacherId);
	}

}
