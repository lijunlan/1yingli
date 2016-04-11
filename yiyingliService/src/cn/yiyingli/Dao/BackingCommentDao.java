package cn.yiyingli.Dao;

import cn.yiyingli.Persistant.BackingComment;

import java.util.List;

public interface BackingCommentDao {
	void save(BackingComment backingComment);

	long querySumByTeacherId(long teacherId, boolean display);

	List<BackingComment> queryListByTeacherIdAndPage(long teacherId, int page, int pageSize);
}
