package cn.yiyingli.Dao;

import cn.yiyingli.Persistant.BackingComment;

import java.util.List;

public interface BackingCommentDao {
	void save(BackingComment backingComment);

	List<BackingComment> queryListByTeacherIdAndPage(long teacherId, int page, int pageSize);
}
