package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.RecordDao;
import cn.yiyingli.Persistant.Record;
import cn.yiyingli.Service.RecordService;

public class RecordServiceImpl implements RecordService {

	private RecordDao recordDao;

	public RecordDao getRecordDao() {
		return recordDao;
	}

	public void setRecordDao(RecordDao recordDao) {
		this.recordDao = recordDao;
	}

	@Override
	public void save(Record record) {
		getRecordDao().save(record);
	}

	@Override
	public void remove(Record record) {
		getRecordDao().remove(record);
	}

	@Override
	public void remove(long id) {
		getRecordDao().remove(id);
	}

	@Override
	public void update(Record record) {
		getRecordDao().update(record);
	}

	@Override
	public Record query(long id) {
		return getRecordDao().query(id);
	}

	@Override
	public List<Record> queryList() {
		return getRecordDao().queryList();
	}

	@Override
	public List<Record> queryListByTime(int page, int pageSize, String time) {
		return getRecordDao().queryListByTime(page, pageSize, time);
	}

	@Override
	public List<Record> queryListByType(int page, int pageSize, short type) {
		return getRecordDao().queryListByType(page, pageSize, type);
	}

	@Override
	public List<Record> queryListByKind(int page, int pageSize, short kind) {
		return getRecordDao().queryListByKind(page, pageSize, kind);
	}

}
