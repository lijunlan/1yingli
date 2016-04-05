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
	public List<BackingComment> queryListByTeacherIdAndPage(long teacherId, int page) {
		return getBackingCommentDao().queryListByTeacherIdAndPage(teacherId, page, BACKINGCOMMENTPAGESIZE);
	}
}
