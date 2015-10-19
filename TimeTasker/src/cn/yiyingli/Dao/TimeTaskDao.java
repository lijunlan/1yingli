package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Task;

public interface TimeTaskDao {
	void save(Task task);

	List<Task> getTask(long time);
	
	void remove(Task task);
	
	void update(Task task);
}
