package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.User;

public interface PassageService {

	public static final int PAGE_SIZE = 3;

	public static final int PAGE_SIZE_MANAGER = 10;

	public static final int PAGE_SIZE_TEACHER = 10;

	void save(Passage passage);

	Long saveAndReturnId(Passage passage);

	void remove(Passage passage);

	void remove(long id);

	void update(Passage passage, boolean stateChange);

	void updateUserLike(Passage passage, User user);

	Passage query(long id);

	Passage queryByUser(long id);

	Passage queryByUserWithTeacher(long id);

	List<Passage> queryList(int page, int pageSize);

	List<Passage> queryListByState(int page, int pageSize, short state);

	List<Passage> queryListByShow(int page, int pageSize, boolean show);

	List<Passage> queryListByTeacherAndState(int page, int pageSize, long teacherId, short state);

	List<Passage> queryList(int page);

	List<Passage> queryListByState(int page, short state);

	List<Passage> queryListByShow(int page, boolean show);

	List<Passage> queryListByTeacherAndState(int page, long teacherId, short state);

}
