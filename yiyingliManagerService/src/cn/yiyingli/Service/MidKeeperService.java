package cn.yiyingli.Service;

import cn.yiyingli.Persistant.MidKeeper;

public interface MidKeeperService {
	
	void save(MidKeeper mk);
	
	String find(String mid);
	
	void remove(String mid);

}
