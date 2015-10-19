package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Task;

public interface TimeTaskService {
	
	void setTask(Task task);
	
	List<Task> patrol(long time);
	
	void finishTask(Task task);
	
	void reSaveTask(Task task);
}
