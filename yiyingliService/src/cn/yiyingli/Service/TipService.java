package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Tip;

public interface TipService {
	void save(Tip tip);

	Long saveAndReturnId(Tip tip);

	void remove(Tip tip);

	void remove(long id);

	void update(Tip tip);

	Tip query(long id);

	List<Tip> queryList();
	
	List<Tip> queryNormalList();
}
