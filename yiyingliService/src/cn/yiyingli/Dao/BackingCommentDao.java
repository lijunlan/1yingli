package cn.yiyingli.Dao;

import cn.yiyingli.Persistant.BackingComment;

import java.util.List;

public interface BackingCommentDao {
	void save(BackingComment backingComment);

	void update(BackingComment backingComment);

	BackingComment query(long id);

	long querySumByTeacherId(long teacherId, boolean display);

	Long querySumByUserId(long userId);

	List<BackingComment> queryListByTeacherIdAndPage(long teacherId, int page, int pageSize);

	List<BackingComment> queryListByUserIdAndPage(long userId, int page, int pageSize);
}
