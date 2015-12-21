package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.OperateRecord;

public interface OperateDao {
	public void save(OperateRecord or);

	public OperateRecord find(Long id);

	public List<OperateRecord> find(String name);

	public List<OperateRecord> find(String name, long beginTime, long endTime);

	public List<OperateRecord> find(long begintime, long endTime);
}
