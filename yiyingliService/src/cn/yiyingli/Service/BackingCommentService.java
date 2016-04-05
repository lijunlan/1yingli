package cn.yiyingli.Service;

import cn.yiyingli.Persistant.BackingComment;

import java.util.List;

public interface BackingCommentService {
	void save(BackingComment backingComment);

	List<BackingComment> queryListByTeacherIdAndPage(long teacherId, int page);
}
