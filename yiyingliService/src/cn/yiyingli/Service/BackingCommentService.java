package cn.yiyingli.Service;

import cn.yiyingli.Persistant.BackingComment;

import java.util.List;

public interface BackingCommentService {

	public static final int BACKINGCOMMENTPAGESIZE = 10;

	void save(BackingComment backingComment);

	void update(BackingComment backingComment);

	BackingComment query(long id);

	long querySumByTeacherId(long teacherId, boolean display);

	Long querySumByUserId(long userId);

	List<BackingComment> queryListByTeacherIdAndPage(long teacherId, int page, boolean display);

	List<BackingComment> queryListByTeacherIdAndPage(long teacherId, int page, int pageSize, boolean display);

	List<BackingComment> queryListByUserIdAndPage(long userId, int page);
}
