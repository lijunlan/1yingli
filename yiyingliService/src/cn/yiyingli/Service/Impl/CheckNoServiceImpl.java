package cn.yiyingli.Service.Impl;

import java.util.Calendar;
import java.util.List;

import cn.yiyingli.Dao.CheckNoDao;
import cn.yiyingli.Persistant.CheckNo;
import cn.yiyingli.Service.CheckNoService;

public class CheckNoServiceImpl implements CheckNoService {

	private CheckNoDao checkNoDao;

	public CheckNoDao getCheckNoDao() {
		return checkNoDao;
	}

	public void setCheckNoDao(CheckNoDao checkNoDao) {
		this.checkNoDao = checkNoDao;
	}

	@Override
	public void save(CheckNo checkNo) {
		getCheckNoDao().save(checkNo);
	}

	@Override
	public Long saveAndReturnId(CheckNo checkNo) {
		return getCheckNoDao().saveAndReturnId(checkNo);
	}

	@Override
	public void remove(CheckNo checkNo) {
		getCheckNoDao().remove(checkNo);
	}

	@Override
	public void remove(long id) {
		getCheckNoDao().remove(id);
	}

	@Override
	public void removeByTime(String time) {
		getCheckNoDao().updateFromSql(
				"delete from CheckNo cn where ("
						+ Calendar.getInstance().getTimeInMillis()
						+ "-cn.createTime)>" + time);
	}

	@Override
	public void update(CheckNo checkNo) {
		getCheckNoDao().update(checkNo);
	}

	@Override
	public CheckNo query(long id) {
		return getCheckNoDao().query(id);
	}

	@Override
	public CheckNo query(String username) {
		return getCheckNoDao().query(username);
	}

	@Override
	public List<CheckNo> queryList() {
		return getCheckNoDao().queryList();
	}

}
