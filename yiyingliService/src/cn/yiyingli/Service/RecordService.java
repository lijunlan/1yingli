package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Record;

public interface RecordService {

	// 记录产生对象类型
	public static final short RECORD_TYPE_USER = 0;

	public static final short RECORD_TYPE_TEACHER = 1;

	public static final short RECORD_TYPE_GUEST = 2;

	// 事件类型

	public static final short RECORD_KIND_SEE_TEACHER = 0;

	public static final short RECORD_KIND_SEE_TIP = 1;

	//
	public static final int PAGE_SIZE = 12;

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
