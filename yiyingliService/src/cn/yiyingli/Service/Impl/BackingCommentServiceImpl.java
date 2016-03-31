package cn.yiyingli.Service.Impl;

import cn.yiyingli.Dao.BackingCommentDao;
import cn.yiyingli.Persistant.BackingComment;
import cn.yiyingli.Service.BackingCommentService;


public class BackingCommentServiceImpl implements BackingCommentService {
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
}
