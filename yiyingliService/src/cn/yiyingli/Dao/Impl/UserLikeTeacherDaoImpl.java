package cn.yiyingli.Dao.Impl;

import cn.yiyingli.Dao.UserLikeTeacherDao;
import cn.yiyingli.Persistant.Teacher;
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
				String hql = "from Teacher t left join UserLikeTeacher u on t.user.id = u.user.id where u.teacher.id ="
						+ likedTeacherId;
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Teacher> list = query.list();
				return list;
			}
		});
		return list;
	}

	public long querySumNoByLikedTeacherId(long likeTeacherId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction ts = session.beginTransaction();
		long sum = (long) session.createQuery(
				"select count(*) from Teacher t left join UserLikeTeacher u on t.user.id = u.user.id where u.teacher.id =" + likeTeacherId)
				.uniqueResult();
		ts.commit();
		return sum;
	}
}
