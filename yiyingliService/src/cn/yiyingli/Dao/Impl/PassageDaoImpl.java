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
	public void save(Passage passage) {
		getHibernateTemplate().save(passage);
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
	public Passage query(long id) {
		String hql = "from Passage p left join fetch p.ownTeacher where p.id=?";
		@SuppressWarnings("unchecked")
		List<Passage> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Passage queryByUser(long id) {
		String hql = "from Passage p  where p.id=? and p.state=" + PASSAGE_STATE_OK;
		@SuppressWarnings("unchecked")
		List<Passage> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Passage queryByUserWithTeacher(long id) {
		String hql = "from Passage p left join fetch p.ownTeacher where p.id=? and p.state=" + PASSAGE_STATE_OK;
		@SuppressWarnings("unchecked")
		List<Passage> list = getHibernateTemplate().find(hql, id);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Passage> queryList(final int page, final int pageSize) {
		List<Passage> list = new ArrayList<Passage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Passage>>() {

			@Override
			public List<Passage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Passage p left join fetch p.ownTeacher ORDER BY p.createTime DESC";
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
				String hql = "from Passage p left join fetch p.ownTeacher where p.ownTeacher.id=" + teacherId
						+ " and p.state=" + state + " ORDER BY p.createTime DESC";
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
				String hql = "from Passage p left join fetch p.ownTeacher where p.state=" + state
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
	public List<Passage> queryListByShow(final int page, final int pageSize, final boolean show) {
		List<Passage> list = new ArrayList<Passage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Passage>>() {

			@Override
			public List<Passage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Passage p left join fetch p.ownTeacher where p.show=" + show
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

}
