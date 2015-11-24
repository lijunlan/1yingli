package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Passage;

public interface PassageDao {
	
	public static final Short PASSAGE_STATE_CHECKING = 0;

	public static final Short PASSAGE_STATE_REFUSE = 1;

	public static final Short PASSAGE_STATE_OK = 2;

	void save(Passage passage);

	Long saveAndReturnId(Passage passage);

	void remove(Passage passage);

	void remove(long id);

	void update(Passage passage);

	Passage query(long id);

	Passage queryByUser(long id);

	Passage queryByUserWithTeacher(long id);

	Boolean queryCheckLikeUser(long passageId, long userId);

	List<Passage> queryList(int page, int pageSize);

	List<Passage> queryListByState(int page, int pageSize, short state);

	List<Passage> queryListByShow(int page, int pageSize, boolean show);

	List<Passage> queryListByTeacherAndState(int page, int pageSize, long teacherId, short state);

}