package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;

public interface PassageDao {

	public static final short PASSAGE_STATE_CHECKING = 0;

	public static final short PASSAGE_STATE_REFUSE = 1;

	public static final short PASSAGE_STATE_OK = 2;

	void saveAndCount(Passage passage, Teacher teacher);

	Long saveAndReturnId(Passage passage);

	void remove(Passage passage);

	void remove(long id, short state, long teacherId);

	void updateAddLookNumber(long passageId, long number);

	void update(Passage passage);

	void updateAndCount(Passage passage, Teacher teacher);

	Passage query(long id);

	Passage queryWithTeacherByManager(long id);

	Passage queryWithTeacherById(long id);

	Passage queryByUser(long id);

	Passage queryByUserWithTeacher(long id);

	Boolean queryCheckLikeUser(long passageId, long userId);

	List<Passage> queryList(int page, int pageSize);

	List<Passage> queryListByStateAndShow(int page, int pageSize, short state, boolean show);

	List<Passage> queryListByState(int page, int pageSize, short state);

	List<Passage> queryListByShow(int page, int pageSize, boolean show);

	List<Passage> queryListByTeacherAndState(int page, int pageSize, long teacherId, short state);

	List<Passage> queryListByIds(long[] ids);

	List<Passage> queryListByActivity(String activityKey, int page, int pageSize);
}
