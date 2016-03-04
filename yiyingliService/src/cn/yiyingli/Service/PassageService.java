package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.User;

public interface PassageService {

	public static final int PAGE_SIZE_PASSAGE = 3;

	public static final int PAGE_SIZE_MANAGER = 10;

	public static final int PAGE_SIZE_TEACHER = 10;

	public static final int MAX_SALE_COUNT = 10;

	void save(Passage passage, boolean updateToBaidu);

	Long saveAndReturnId(Passage passage);

	void remove(Passage passage);

	void update(Passage passage, boolean stateChange, boolean updateToBaidu);

	void updateAddLookNumber(long passageId, long number);

	boolean updateUserLike(Passage passage, User user);

	Passage query(long id);

	Passage queryWithTeacherByManager(long id);

	Passage queryWithTeacherById(long id);

	Passage queryByUser(long id);

	Passage queryByUserWithTeacher(long id);

	List<Passage> queryList(int page, int pageSize);

	List<Passage> queryListBySale();

	List<Passage> queryListByState(int page, int pageSize, short state);

	List<Passage> queryListByShow(int page, int pageSize, boolean show);

	List<Passage> queryListByTeacherAndState(int page, int pageSize, long teacherId, short state);

	List<Passage> queryList(int page);

	List<Passage> queryListByState(int page, short state);

	List<Passage> queryListByShow(int page, boolean show);

	List<Passage> queryListByTeacherAndState(int page, long teacherId, short state);

	List<Passage> queryListByIds(List<Long> ids);

	List<Passage> queryListByRecommand(int page, int pageSize, short state, boolean show);

	List<Passage> queryListByRecommand(int page, short state, boolean show);

}
