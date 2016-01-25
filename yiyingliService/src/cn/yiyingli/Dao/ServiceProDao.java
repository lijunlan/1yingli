package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.ServicePro;

public interface ServiceProDao {

	public static final short SHOW_KIND_NONE = 0;

	public static final short SHOW_KIND_OFF = 1;

	public static final short SHOW_KIND_ON = 2;

	void save(ServicePro servicePro);

	void saveAndPlusNumber(ServicePro servicePro, boolean byManager);

	Long saveAndReturnId(ServicePro servicePro);

	void remove(ServicePro servicePro);

	void remove(long id);

	void removeUserLike(long serviceProId, long userId);

	void update(ServicePro servicePro);

	Boolean queryCheckLikeUser(long serviceProId, long userId);

	void updateAndPlusNumber(ServicePro servicePro, boolean remove);

	ServicePro querySimple(long id);

	ServicePro queryByUser(long id);

	ServicePro queryByTeacherIdAndServiceId(long teacherId, long serviceId);

	List<ServicePro> queryList(int page, int pageSize);

	List<ServicePro> queryList(long[] ids, long teacherId);

	List<ServicePro> queryListByTeacherIdAndShow(long teacherId, short showKind, int page, int pageSize);

	List<ServicePro> queryListByTeacherIdAndShowAndState(long teacherId, short showKind, short state, int page,
			int pageSize);

	List<ServicePro> queryListByHomePage(int pageSize);

	List<ServicePro> queryListBySale(int page, int pageSize);

	List<ServicePro> queryListByKind(int kind, int page, int pageSize);

	List<ServicePro> queryLikeListByUserId(long userid, int page, int pageSize);

	List<ServicePro> queryListOtherByTeacher(long serviceProId, long teacherId, int page, int pageSize);

	long queryListBySaleNo();

}
