package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.PassageDao;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;

public class PassageDaoImpl extends HibernateDaoSupport implements PassageDao {

	@Override
	public void saveAndCount(Passage passage, Teacher teacher) {
		getHibernateTemplate().save(passage);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update teacher set teacher.CHECKPASSAGENUMBER=(select count(*) from passage where passage.remove="
						+ false + " and passage.TEACHER_ID='" + teacher.getId() + "' and passage.state="
						+ PassageDao.PASSAGE_STATE_CHECKING + ") where teacher.TEACHER_ID=" + teacher.getId());
		query.executeUpdate();
	}

	@Override
	public Long saveAndReturnId(Passage passage) {
		getHibernateTemplate().save(passage);
		return passage.getId();
	}

	@Override
	public void remove(Passage passage) {
		getHibernateTemplate().delete(passage);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Passage p where p.id=?", id);
	}

	@Override
	public void update(Passage passage) {
		getHibernateTemplate().update(passage);
	}

	@Override
	public void updateAddLookNumber(long passageId, long number) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("update passage set passage.LOOKNUMBER=passage.LOOKNUMBER+" + number
				+ " where passage.PASSAGE_ID=" + passageId);
		query.executeUpdate();
	}

	@Override
	public void updateAndCount(Passage passage, Teacher teacher) {
		getHibernateTemplate().update(passage);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update teacher set teacher.PASSAGENUMBER=(select count(*) from passage where passage.remove=" + false
						+ " and passage.TEACHER_ID='" + teacher.getId() + "' and passage.state="
						+ PassageDao.PASSAGE_STATE_OK + ") where teacher.TEACHER_ID=" + teacher.getId());
		query.executeUpdate();
		session.flush();
		query = session.createSQLQuery(
				"update teacher set teacher.CHECKPASSAGENUMBER=(select count(*) from passage where passage.remove="
						+ false + " and passage.TEACHER_ID='" + teacher.getId() + "' and passage.state="
						+ PassageDao.PASSAGE_STATE_CHECKING + ") where teacher.TEACHER_ID=" + teacher.getId());
		query.executeUpdate();
		session.flush();
		query = session.createSQLQuery(
				"update teacher set teacher.REFUSEPASSAGENUMBER=(select count(*) from passage where passage.remove="
						+ false + " and passage.TEACHER_ID='" + teacher.getId() + "' and passage.state="
						+ PassageDao.PASSAGE_STATE_REFUSE + ") where teacher.TEACHER_ID=" + teacher.getId());
		query.executeUpdate();
	}

	@Override
	public Passage query(long id) {
		String hql = "from Passage p left join fetch p.ownTeacher where p.remove=" + false
				+ " and p.id=? and p.onshow=?";
		@SuppressWarnings("unchecked")
		List<Passage> list = getHibernateTemplate().find(hql, id, true);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Passage queryWithTeacherById(long id) {
		String hql = "from Passage p left join fetch p.ownTeacher where p.remove=" + false
				+ " and p.id=? and p.onshow=?";
		@SuppressWarnings("unchecked")
		List<Passage> list = getHibernateTemplate().find(hql, id, true);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Passage queryByUser(long id) {
		String hql = "from Passage p  where p.remove=" + false + " and p.id=? and p.state=? and p.onshow=?";
		@SuppressWarnings("unchecked")
		List<Passage> list = getHibernateTemplate().find(hql, id, PASSAGE_STATE_OK, true);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Passage queryByUserWithTeacher(long id) {
		String hql = "from Passage p left join fetch p.ownTeacher where p.remove=" + false
				+ " and p.id=? and p.state=? and p.onshow=?";
		@SuppressWarnings("unchecked")
		List<Passage> list = getHibernateTemplate().find(hql, id, PASSAGE_STATE_OK, true);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Boolean queryCheckLikeUser(long passageId, long userId) {
		String hql = "from UserLikePassage ulp where ulp.user.id=?  and ulp.passage.id=?";
		@SuppressWarnings("unchecked")
		List<Teacher> list = getHibernateTemplate().find(hql, userId, passageId);
		if (list.isEmpty())
			return false;
		else
			return true;

	}

	@Override
	public List<Passage> queryListByIds(long[] ids) {
		if (ids.length <= 0)
			return new ArrayList<Passage>();
		String hql = "from Passage p where p.id=" + ids[0];
		if (ids.length > 1) {
			for (int i = 1; i < ids.length; i++) {
				hql = hql + " or p.id=" + ids[i];
			}
		}
		@SuppressWarnings("unchecked")
		List<Passage> list = getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Passage> queryList(final int page, final int pageSize) {
		List<Passage> list = new ArrayList<Passage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Passage>>() {

			@Override
			public List<Passage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Passage p left join fetch p.ownTeacher where p.remove=" + false
						+ " ORDER BY p.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Passage> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Passage> queryListByTeacherAndState(final int page, final int pageSize, final long teacherId,
			final short state) {
		List<Passage> list = new ArrayList<Passage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Passage>>() {

			@Override
			public List<Passage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Passage p left join fetch p.ownTeacher where p.remove=" + false
						+ " and p.ownTeacher.id=" + teacherId + " and p.state=" + state + " and p.onshow=" + true
						+ " ORDER BY p.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Passage> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Passage> queryListByState(final int page, final int pageSize, final short state) {
		List<Passage> list = new ArrayList<Passage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Passage>>() {

			@Override
			public List<Passage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Passage p left join fetch p.ownTeacher where p.remove=" + false + " and p.state="
						+ state + " and p.onshow=" + true + " ORDER BY p.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Passage> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Passage> queryListByShow(final int page, final int pageSize, final boolean show) {
		List<Passage> list = new ArrayList<Passage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Passage>>() {

			@Override
			public List<Passage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Passage p left join fetch p.ownTeacher where p.remove=" + false + " and p.onshow="
						+ show + " ORDER BY p.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Passage> list = query.list();
				return list;
			}
		});
		return list;
	}

}
