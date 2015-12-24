package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.ServicePro;

public interface ServiceProDao {

	public static final short SHOW_KIND_NONE = 0;

	public static final short SHOW_KIND_OFF = 1;

	public static final short SHOW_KIND_ON = 2;

	void save(ServicePro servicePro);

	Long saveAndReturnId(ServicePro servicePro);

	void remove(ServicePro servicePro);

	void remove(long id);

	void update(ServicePro servicePro);

	ServicePro querySimple(long id);
	
	ServicePro queryByUser(long id);

	ServicePro queryByTeacherIdAndServiceId(long teacherId, long serviceId);

	List<ServicePro> queryList(long[] ids, long teacherId);

	List<ServicePro> queryListByTeacherIdAndShow(long teacherId, short showKind, int page, int pageSize);

	List<ServicePro> queryListByTeacherIdAndShowAndState(long teacherId, short showKind, short state, int page,
			int pageSize);

	List<ServicePro> queryListByTeacherIdAndShowAndStateAndStyle(long teacherId, short showKind, short state,
			short style, int page, int pageSize);
}
