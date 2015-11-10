package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Record;

public interface RecordDao {

	void save(Record record);

	void remove(Record record);

	void remove(long id);

	void update(Record record);

	Record query(long id);

	List<Record> queryList();

	List<Record> queryListByTime(int page, int pageSize, String time);

	List<Record> queryListByType(int page, int pageSize, short type);

	List<Record> queryListByKind(int page, int pageSize, short kind);

}
