package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.SuggestionDao;
import cn.yiyingli.Persistant.Suggestion;

public class SuggestionDaoImpl extends HibernateDaoSupport implements SuggestionDao {

	@Override
	public void save(Suggestion suggestion) {
		getHibernateTemplate().save(suggestion);
	}

	@Override
	public void remove(Suggestion suggestion) {
		getHibernateTemplate().delete(suggestion);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Suggestion s where s.id=?", id);
	}

	@Override
	public void update(Suggestion suggestion) {
		getHibernateTemplate().update(suggestion);
	}

	@Override
	public Suggestion query(long id) {
		String hql = "from Suggestion s where s.id=?";
		@SuppressWarnings("unchecked")
		List<Suggestion> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<Suggestion> queryList() {
		String hql = "from Suggestion s";
		@SuppressWarnings("unchecked")
		List<Suggestion> list = getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Suggestion> queryListByTime(final int page, final int pageSize) {
		List<Suggestion> list = new ArrayList<Suggestion>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Suggestion>>() {

			@Override
			public List<Suggestion> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Suggestion s ORDER BY s.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Suggestion> list = query.list();
				return list;
			}
		});
		return list;
	}

}
