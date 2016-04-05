package cn.yiyingli.Dao.Impl;

import cn.yiyingli.Dao.BackingCommentDao;
import cn.yiyingli.Persistant.BackingComment;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.SQLException;
import java.util.List;

public class BackingCommentDaoImpl extends HibernateDaoSupport implements BackingCommentDao {

	@Override
	public void save(BackingComment backingComment) {
		getHibernateTemplate().save(backingComment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BackingComment> queryListByTeacherIdAndPage(final long teacherId,final int page,final int pageSize) {
		List<BackingComment> list;
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<BackingComment>>() {
			@Override
			public List<BackingComment> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from BackingComment bc left join bc.teacher left join bc.user where bc.teacher.id = "
						+ teacherId + " order by bc.weight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<BackingComment> list = query.list();
				return list;
			}
		});
		return list;
	}
}
