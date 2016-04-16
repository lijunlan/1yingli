package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.Dao.ContentAndPageDao;
import cn.yiyingli.Dao.PassageDao;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.Teacher;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.PagesDao;
import cn.yiyingli.Persistant.Pages;

public class PagesDaoImpl extends HibernateDaoSupport implements PagesDao {

	@Override
	public void save(Pages pages) {
		getHibernateTemplate().save(pages);
	}

	@Override
	public void remove(Pages pages) {
		getHibernateTemplate().delete(pages);
	}

	@Override
	public void update(Pages pages) {
		getHibernateTemplate().update(pages);
	}

	@Override
	public Pages query(Long id) {
		String hql = "from Pages p where p.remove=" + false + " and p.id=?";
		@SuppressWarnings("unchecked")
		List<Pages> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Pages queryByKey(String activityKey) {
		String hql = "from Pages p where p.remove=" + false + " and p.pagesKey=?";
		@SuppressWarnings("unchecked")
		List<Pages> list = getHibernateTemplate().find(hql, activityKey);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public long queryTeamSum() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		long sum = (long) session.createQuery("select count(*) from Pages p where p.remove = "
				+ false + " and  p.weight > 0").uniqueResult();
		return sum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pages> queryList(final int page, final int pageSize) {
		List<Pages> list = new ArrayList<Pages>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Pages>>() {
			@Override
			public List<Pages> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Pages p where p.remove=" + false + " ORDER BY p.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Pages> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pages> queryListOrderByWeight(final int page, final int pageSize) {
		List<Pages> list = new ArrayList<Pages>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Pages>>() {
			@Override
			public List<Pages> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Pages p where p.remove=" + false + " and p.weight > 0 ORDER BY p.weight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Pages> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pages> queryListOrderByMile() {
		List<Pages> list = new ArrayList<Pages>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Pages>>() {
			@Override
			public List<Pages> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Pages p where p.remove=" + false + " and p.weight > 0 ORDER BY p.mile DESC";
				Query query = session.createQuery(hql);
				List<Pages> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Passage> queryTeacherPassageListById(final long id, final int page, final int pageSize) {
		List<Passage> list = new ArrayList<>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Passage>>() {
			@Override
			public List<Passage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Passage p left join fetch p.ownTeacher inner join fetch p.ownTeacher.contentAndPages as cap" +
						" left join fetch cap.pages pa where pa.id = " + id + " and cap.style = " +
						ContentAndPageDao.STYLE_TEACHER + " and p.state=" + PassageDao.PASSAGE_STATE_OK +
						" and p.onshow=" + true + " and p.remove=" + false;
//				String hql = "from ContentAndPage cap left join fetch cap.teacher" +
//						" left join cap.pages as pa" +
//						" left join fetch cap.teacher.passages" +
//						" where pa.id = " + id + " and cap.style = " + ContentAndPageDao.STYLE_TEACHER;
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
	public List<Passage> queryPassageListById(final long id, final int page, final int pageSize) {
		List<Passage> list = new ArrayList<>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Passage>>() {
			@Override
			public List<Passage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Passage p left join fetch p.ownTeacher left join fetch p.contentAndPages as" +
						" cap left join fetch cap.pages as pa where pa.id = " + id + " and cap.style = "
						+ ContentAndPageDao.STYLE_PASSAGE + " and p.state=" + PassageDao.PASSAGE_STATE_OK +
						" and p.onshow=" + true + " and p.remove=" + false;
//				String hql = "select cap.passage from ContentAndPage cap left join fetch cap.passage as p" +
//						" left join cap.pages as pa where pa.id = " + id + " and cap.style = " +
//						ContentAndPageDao.STYLE_TEACHER;
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
