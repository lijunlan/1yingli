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
	public List<ContentAndPage> queryListByPages(long pagesId) {
		String hql = "from ContentAndPage cap left join fetch cap.pages cp left join fetch cap.teacher"
				+ " left join fetch cap.passage left join fetch cap.servicePro where cp.id=" + pagesId
				+ " ORDER BY cap.createTime DESC";
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
				String hql = "from ContentAndPage cap left join fetch cap.pages capp left join fetch cap.passage"
						+ " where capp.key=" + activityKey + " and cap.style=" + STYLE_PASSAGE
						+ " ORDER BY cap.createTime DESC";
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
						+ " where capp.key=" + activityKey + " and cap.style=" + STYLE_TEACHER
						+ " ORDER BY cap.createTime DESC";
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
				String hql = "from ContentAndPage cap left join fetch cap.pages capp left join fetch cap.servicePro"
						+ " where capp.key=" + activityKey + " and cap.style=" + STYLE_SERVICEPRO
						+ " ORDER BY cap.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<ContentAndPage> list = query.list();
				return list;
			}
		});
		return list;
	}

}
