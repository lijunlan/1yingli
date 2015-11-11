package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.DistributorDao;
import cn.yiyingli.Persistant.Distributor;

public class DistributorDaoImpl extends HibernateDaoSupport implements DistributorDao {

	@Override
	public void save(Distributor distributor) {
		getHibernateTemplate().save(distributor);
	}

	@Override
	public Long saveAndReturnId(Distributor distributor) {
		getHibernateTemplate().save(distributor);
		return distributor.getId();
	}

	@Override
	public void remove(Distributor distributor) {
		getHibernateTemplate().delete(distributor);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Distributor d where d.id=?", id);
	}

	@Override
	public void merge(Distributor distributor) {
		getHibernateTemplate().merge(distributor);
	}

	@Override
	public void update(Distributor distributor) {
		getHibernateTemplate().update(distributor);
	}

	@Override
	public Distributor queryByNo(String no) {
		String hql = "from Distributor d where d.number=?";
		@SuppressWarnings("unchecked")
		List<Distributor> list = getHibernateTemplate().find(hql, no);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Distributor query(long id) {
		String hql = "from Distributor d where d.id=?";
		@SuppressWarnings("unchecked")
		List<Distributor> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public long queryCount() {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Distributor d").uniqueResult();
		ts.commit();
		session.close();
		return sum;
	}

	@Override
	public Distributor queryByUsername(String username) {
		String hql = "from Distributor d where d.username=?";
		@SuppressWarnings("unchecked")
		List<Distributor> list = getHibernateTemplate().find(hql, username);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Distributor queryByName(String name) {
		String hql = "from Distributor d where d.name=?";
		@SuppressWarnings("unchecked")
		List<Distributor> list = getHibernateTemplate().find(hql, name);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Distributor> queryList(final int page, final int pageSize) {
		List<Distributor> list = new ArrayList<Distributor>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Distributor>>() {
			@Override
			public List<Distributor> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Distributor d ORDER BY d.createTime DESC";

				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Distributor> list = query.list();
				return list;
			}
		});
		return list;
	}

}
