package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.BackgroundDao;
import cn.yiyingli.Persistant.Background;

public class BackgroundDaoImpl extends HibernateDaoSupport implements BackgroundDao {

	@Override
	public void save(Background background) {
		getHibernateTemplate().save(background);
	}

	@Override
	public Long saveAndReturnId(Background background) {
		getHibernateTemplate().save(background);
		return background.getId();
	}

	@Override
	public void remove(Background background) {
		getHibernateTemplate().delete(background);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Background bg where bg.id=?", id);
	}

	@Override
	public void update(Background background) {
		getHibernateTemplate().update(background);
	}

	@Override
	public Background query(long id) {
		String hql = "from Background bg where bg.id=?";
		@SuppressWarnings("unchecked")
		List<Background> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<Background> queryList() {
		String hql = "from Background bg ";
		@SuppressWarnings("unchecked")
		List<Background> list = getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Background> queryList(final int page, final int pageSize) {
		List<Background> list = new ArrayList<Background>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Background>>() {
			@Override
			public List<Background> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Background bg ";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Background> list = query.list();
				return list;
			}
		});
		return list;
	}

}
