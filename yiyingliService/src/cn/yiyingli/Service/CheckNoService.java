package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.CheckNo;

public interface CheckNoService {
	
	void save(CheckNo checkNo);

	Long saveAndReturnId(CheckNo checkNo);

	void remove(CheckNo checkNo);

	void remove(long id);
	
	void removeByTime(String time);

	void update(CheckNo checkNo);

	CheckNo query(long id);

	CheckNo query(String username);

	List<CheckNo> queryList();
	
}
