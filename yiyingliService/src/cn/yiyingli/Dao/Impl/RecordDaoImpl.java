package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.RecordDao;
import cn.yiyingli.Persistant.Record;

public class RecordDaoImpl extends HibernateDaoSupport implements RecordDao {

	@Override
	public void save(Record record) {
		getHibernateTemplate().save(record);
	}

	@Override
	public void remove(Record record) {
		getHibernateTemplate().delete(record);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Record r where r.id=?", id);
	}

	@Override
	public void update(Record record) {
		getHibernateTemplate().update(record);
	}

	@Override
	public Record query(long id) {
		String hql = "from Record r where r.id=?";
		@SuppressWarnings("unchecked")
		List<Record> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<Record> queryList() {
		String hql = "from Record r";
		@SuppressWarnings("unchecked")
		List<Record> list = getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Record> queryListByTime(final int page, final int pageSize, final String time) {
		List<Record> list = new ArrayList<Record>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Record>>() {

			@Override
			public List<Record> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Record r where r.createTime>" + time + " ORDER BY r.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Record> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Record> queryListByType(final int page, final int pageSize, final short type) {
		List<Record> list = new ArrayList<Record>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Record>>() {

			@Override
			public List<Record> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Record r where r.type=" + type + " ORDER BY r.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Record> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Record> queryListByKind(final int page, final int pageSize, final short kind) {
		List<Record> list = new ArrayList<Record>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Record>>() {

			@Override
			public List<Record> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Record r where r.kind=" + kind + " ORDER BY r.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Record> list = query.list();
				return list;
			}
		});
		return list;
	}
	
}
