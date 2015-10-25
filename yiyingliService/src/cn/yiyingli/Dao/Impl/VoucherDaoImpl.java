package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.VoucherDao;
import cn.yiyingli.Persistant.Voucher;

public class VoucherDaoImpl extends HibernateDaoSupport implements VoucherDao {

	@Override
	public void save(Voucher voucher) {
		getHibernateTemplate().save(voucher);
	}

	@Override
	public Long saveAndReturnId(Voucher voucher) {
		getHibernateTemplate().save(voucher);
		return voucher.getId();
	}

	@Override
	public void remove(Voucher voucher) {
		getHibernateTemplate().delete(voucher);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Voucher v where v.id=?", id);
	}

	@Override
	public void update(Voucher voucher) {
		getHibernateTemplate().update(voucher);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public Voucher query(long id, boolean lazy) {
		String hql = "from Voucher v where v.id=?";
		if (lazy) {
			hql = "from Voucher v left join fetch v.useOrder left join fetch v.ownUser where v.id=?";
		}
		@SuppressWarnings("unchecked")
		List<Voucher> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Voucher query(String vno, boolean lazy) {
		String hql = "from Voucher v where v.number=?";
		if (lazy) {
			hql = "from Voucher v left join fetch v.useOrder left join fetch v.ownUser where v.number=?";
		}
		@SuppressWarnings("unchecked")
		List<Voucher> list = getHibernateTemplate().find(hql, vno);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Voucher> queryList(final int page, final int pageSize, final boolean lazy) {
		List<Voucher> list = new ArrayList<Voucher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Voucher>>() {

			@Override
			public List<Voucher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Voucher v";
				if (lazy) {
					hql = "from Voucher v left join fetch v.useOrder left join fetch v.ownUser ORDER BY v.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Voucher> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Voucher> queryListByUserId(final long userId, final int page, final int pageSize, final boolean lazy) {
		List<Voucher> list = new ArrayList<Voucher>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Voucher>>() {

			@Override
			public List<Voucher> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Voucher v where v.ownUser.id=" + userId + " ORDER BY v.createTime DESC";
				if (lazy) {
					hql = "from Voucher v left join fetch v.useOrder left join fetch v.ownUser where v.ownUser.id=" + userId
							+ " ORDER BY v.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Voucher> list = query.list();
				return list;
			}
		});
		return list;
	}

}
