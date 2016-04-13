package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.ContentAndPageDao;
import cn.yiyingli.Persistant.ContentAndPage;

public class ContentAndPageDaoImpl extends HibernateDaoSupport implements ContentAndPageDao {

	@Override
	public void save(ContentAndPage contentAndPage) {
		getHibernateTemplate().save(contentAndPage);
	}

	@Override
	public void remove(long contentAndPageId) {
		getHibernateTemplate().bulkUpdate("delete from ContentAndPage cap where cap.id=?", contentAndPageId);
	}

	@Override
	public void update(ContentAndPage contentAndPage) {
		getHibernateTemplate().update(contentAndPage);
	}

	@Override
	public ContentAndPage query(long contentAndPageId) {
		String hql = "from ContentAndPage cap left join fetch cap.servicePro" +
				" left join fetch cap.teacher left join fetch cap.passage" +
				" left join fetch cap.pages where cap.id=?";
		@SuppressWarnings("unchecked")
		List<ContentAndPage> list = getHibernateTemplate().find(hql, contentAndPageId);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public long queryTeamTeacherSum() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		long sum = (long) session.createQuery("select count(*) from ContentAndPage cap left join cap.pages"
				+ " where cap.style=" + STYLE_TEACHER
				+ " and cap.pages.weight > 0").uniqueResult();
		return sum;
	}

	@Override
	public List<ContentAndPage> queryListByPages(long pagesId) {
		String hql = "from ContentAndPage cap left join fetch cap.pages cp left join fetch cap.teacher"
				+ " left join fetch cap.passage left join fetch cap.servicePro where cp.id=" + pagesId
				+ " ORDER BY cap.weight DESC";
		@SuppressWarnings("unchecked")
		List<ContentAndPage> list = getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContentAndPage> queryListWithPassageByKey(final String activityKey, final int page,
														  final int pageSize) {
		List<ContentAndPage> list = new ArrayList<ContentAndPage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ContentAndPage>>() {
			@Override
			public List<ContentAndPage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ContentAndPage cap left join fetch cap.pages capp left join fetch cap.passage cappp" +
						" left join fetch cappp.ownTeacher"
						+ " where capp.pagesKey='" + activityKey + "' and cap.style=" + STYLE_PASSAGE
						+ " ORDER BY cap.weight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ContentAndPage> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContentAndPage> queryListWithTeacherByKey(final String activityKey, final int page,
														  final int pageSize) {
		List<ContentAndPage> list = new ArrayList<ContentAndPage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ContentAndPage>>() {
			@Override
			public List<ContentAndPage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ContentAndPage cap left join fetch cap.pages capp left join fetch cap.teacher"
						+ " where capp.pagesKey='" + activityKey + "' and cap.style=" + STYLE_TEACHER
						+ " ORDER BY cap.weight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ContentAndPage> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContentAndPage> queryListWithServiceProByKey(final String activityKey, final int page,
															 final int pageSize) {
		List<ContentAndPage> list = new ArrayList<ContentAndPage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ContentAndPage>>() {
			@Override
			public List<ContentAndPage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ContentAndPage cap left join fetch cap.pages capp left join fetch cap.servicePro caps"
						+ " left join fetch caps.teacher"
						+ " where capp.pagesKey='" + activityKey + "' and cap.style=" + STYLE_SERVICEPRO
						+ " ORDER BY cap.weight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ContentAndPage> list = query.list();
				return list;
			}
		});
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ContentAndPage> queryListWithTeacher(final int page, final int pageSize) {
		List<ContentAndPage> list = new ArrayList<ContentAndPage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ContentAndPage>>() {
			@Override
			public List<ContentAndPage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ContentAndPage cap left join fetch cap.pages left join fetch cap.teacher"
						+ " where  cap.style=" + STYLE_TEACHER
						+ " and cap.pages.weight > 0"
						+ " group by cap.teacher.id"
						+ " ORDER BY cap.weight DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ContentAndPage> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContentAndPage> queryListWithTeacher() {
		List<ContentAndPage> list = new ArrayList<ContentAndPage>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<ContentAndPage>>() {
			@Override
			public List<ContentAndPage> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ContentAndPage cap left join fetch cap.pages left join fetch cap.teacher capt "
						+ " where  cap.style=" + STYLE_TEACHER
						+ " and cap.pages.weight > 0"
						+ " ORDER BY capt.mile DESC";
				Query query = session.createQuery(hql);
				List<ContentAndPage> list = query.list();
				return list;
			}
		});
		return list;
	}
}
