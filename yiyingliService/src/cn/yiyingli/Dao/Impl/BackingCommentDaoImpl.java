package cn.yiyingli.Dao.Impl;

import cn.yiyingli.Dao.BackingCommentDao;
import cn.yiyingli.Persistant.BackingComment;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BackingCommentDaoImpl extends HibernateDaoSupport implements BackingCommentDao {

	@Override
	public void save(BackingComment backingComment) {
		getHibernateTemplate().save(backingComment);
	}
}
