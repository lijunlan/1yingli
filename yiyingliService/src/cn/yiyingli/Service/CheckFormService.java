package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.CheckForm;
import cn.yiyingli.Persistant.Manager;

public interface CheckFormService {

	public static final short CHECK_STATE_SUCCESS_SHORT = 0;

	public static final short CHECK_STATE_FAILED_SHORT = 1;

	public static final short CHECK_STATE_CHECKING_SHORT = 2;

	public static final short CHECK_KIND_IDENTITY_SHORT = 0;

	public static final short CHECK_KIND_SCHOOL_SHORT = 1;

	public static final short CHECK_KIND_COMPANY_SHORT = 2;

	void save(CheckForm checkForm);

	Long saveAndReturnId(CheckForm checkForm);

	void remove(CheckForm checkForm);

	void remove(long id);

	void update(CheckForm checkForm);

	void finishCheck(long id, boolean accept, String description, Manager manager);

	CheckForm query(long id);

	CheckForm query(String teacherId);

	List<CheckForm> queryList();

}
