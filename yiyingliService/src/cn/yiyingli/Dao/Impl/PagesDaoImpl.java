package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

}
