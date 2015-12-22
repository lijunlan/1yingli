package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.ServicePro;

public interface ServiceProDao {

	void save(ServicePro servicePro);

	Long saveAndReturnId(ServicePro servicePro);

	void remove(ServicePro servicePro);

	void remove(long id);

	void update(ServicePro servicePro);

	ServicePro querySimple(long id);

	List<ServicePro> queryList(long[] ids, long teacherId);
}
