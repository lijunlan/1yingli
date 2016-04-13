package cn.yiyingli.Service;

import cn.yiyingli.Persistant.BackingComment;

import java.util.List;

public interface BackingCommentService {
	void save(BackingComment backingComment);

	void update(BackingComment backingComment);

	BackingComment query(long id);

	long querySumByTeacherId(long teacherId, boolean display);

	List<BackingComment> queryListByTeacherIdAndPage(long teacherId, int page);
}
