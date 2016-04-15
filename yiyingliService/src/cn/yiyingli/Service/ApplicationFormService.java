package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.ApplicationForm;

public interface ApplicationFormService {

	public static final short APPLICATION_STATE_SUCCESS_SHORT = 0;

	public static final short APPLICATION_STATE_FAILED_SHORT = 1;

	public static final short APPLICATION_STATE_CHECKING_SHORT = 2;

	void save(ApplicationForm applicationForm);

	Long saveAndReturnId(ApplicationForm applicationForm);

	void remove(ApplicationForm applicationForm);

	void remove(long id);

	void update(ApplicationForm applicationForm);

	ApplicationForm query(long id);

	ApplicationForm queryByTeacherName(String name);

	ApplicationForm query(String userId);

	List<ApplicationForm> queryList();

	List<ApplicationForm> queryList(int page, int pageSize);

	List<ApplicationForm> queryList(int page, int pageSize, int state);
}
