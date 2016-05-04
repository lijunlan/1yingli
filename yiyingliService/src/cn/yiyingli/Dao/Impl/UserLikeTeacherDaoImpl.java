package cn.yiyingli.Dao.Impl;

import cn.yiyingli.Dao.UserLikeTeacherDao;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Util.LogUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.SQLException;
import java.util.List;

public class UserLikeTeacherDaoImpl extends HibernateDaoSupport implements UserLikeTeacherDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> queryTeacherListByLikedTeacherId(final long likedTeacherId, final int page, final int pageSize) {
		List<Teacher> list;
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Teacher>>() {
			@Override
			public List<Teacher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select t from Teacher t left join fetch t.user u1, UserLikeTeacher ult " +
						"left join ult.teacher t2 left join ult.user u2 where t.onService = true and " +
						"u1.id = u2.id and t.id != "+ likedTeacherId + " and t2.id =" + likedTeacherId;
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

	public long querySumNoByLikedTeacherId(long likedTeacherId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		long sum = (long) session.createQuery(
				"select count(t) from Teacher t left join t.user u1, UserLikeTeacher ult " +
						"left join ult.teacher t2 left join ult.user u2 where t.onService = true and " +
						"u1.id = u2.id and t.id != "+ likedTeacherId + " and t2.id =" + likedTeacherId)
				.uniqueResult();
		return sum;
	}
}
