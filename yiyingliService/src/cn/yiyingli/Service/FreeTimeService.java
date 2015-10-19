package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.FreeTime;

public interface FreeTimeService {

	void save(FreeTime freeTime);

	Long saveAndReturnId(FreeTime freeTime);

	void remove(FreeTime freeTime);

	void remove(long id);

	void removeAll(long teacherId);

	void update(FreeTime freeTime);

	FreeTime query(long id, boolean lazy);

	List<FreeTime> queryListByTeacherId(long teacherId, boolean lazy);

	List<FreeTime> queryListByTServiceId(long tServiceId, boolean lazy);
}
