package cn.yiyingli.Service.Impl;

import cn.yiyingli.Dao.BackingCommentDao;
import cn.yiyingli.Persistant.BackingComment;
import cn.yiyingli.Service.BackingCommentService;

import java.util.List;


public class BackingCommentServiceImpl implements BackingCommentService {
	public static final int BACKINGCOMMENTPAGESIZE = 10;

	private BackingCommentDao backingCommentDao;

	public BackingCommentDao getBackingCommentDao() {
		return backingCommentDao;
	}

	public void setBackingCommentDao(BackingCommentDao backingCommentDao) {
		this.backingCommentDao = backingCommentDao;
	}

	@Override
	public void save(BackingComment backingComment) {
		getBackingCommentDao().save(backingComment);
	}

	@Override
	public void update(BackingComment backingComment) {
		getBackingCommentDao().update(backingComment);
	}

	@Override
	public BackingComment query(long id) {
		return getBackingCommentDao().query(id);
	}

	@Override
	public long querySumByTeacherId(long teacherId, boolean display) {
		return getBackingCommentDao().querySumByTeacherId(teacherId,display);
	}

	@Override
	public List<BackingComment> queryListByTeacherIdAndPage(long teacherId, int page) {
		return getBackingCommentDao().queryListByTeacherIdAndPage(teacherId, page, BACKINGCOMMENTPAGESIZE);
	}
}
