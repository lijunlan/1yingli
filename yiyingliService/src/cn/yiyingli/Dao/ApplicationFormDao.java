package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.ApplicationForm;

public interface ApplicationFormDao {

	void save(ApplicationForm checkApplication);

	Long saveAndReturnId(ApplicationForm checkApplication);

	void remove(ApplicationForm checkApplication);

	void remove(long id);

	void update(ApplicationForm checkApplication);

	void updateFromSql(String sql);

	ApplicationForm query(long id);

	ApplicationForm queryByTeacherName(String name);

	ApplicationForm query(String userId);

	List<ApplicationForm> queryList();

	List<ApplicationForm> queryList(int page, int pageSize);

}
