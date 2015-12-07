package cn.yiyingli.Dao;

import cn.yiyingli.Persistant.MidKeeper;

public interface MidKeeperDao {
	
	void save(MidKeeper mk);
	
	void remove(String mid);
	
	String find(String mid);
	
}
