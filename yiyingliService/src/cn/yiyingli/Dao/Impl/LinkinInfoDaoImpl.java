package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.LinkinInfoDao;
import cn.yiyingli.Persistant.LinkinInfo;

public class LinkinInfoDaoImpl extends HibernateDaoSupport implements
		LinkinInfoDao {

	@Override
	public void save(LinkinInfo linkinInfo) {
		getHibernateTemplate().save(linkinInfo);
	}

	@Override
	public Long saveAndReturnId(LinkinInfo linkinInfo) {
		getHibernateTemplate().save(linkinInfo);
		return linkinInfo.getId();
	}

	@Override
	public void remove(LinkinInfo linkinInfo) {
		getHibernateTemplate().delete(linkinInfo);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate(
				"delete from LinkinInfo li where li.id=?", id);
	}

	@Override
	public void update(LinkinInfo linkinInfo) {
		getHibernateTemplate().update(linkinInfo);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public LinkinInfo query(long id, boolean lazy) {
		String hql = "from LinkinInfo li where li.id=?";
		if (lazy) {
			hql = "from LinkinInfo li left join fetch li.ownUser where li.id=?";
		}
		@SuppressWarnings("unchecked")
		List<LinkinInfo> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LinkinInfo> queryList(final int page, final int pageSize,
			final boolean lazy) {
		List<LinkinInfo> list = new ArrayList<LinkinInfo>();
		list = getHibernateTemplate().executeFind(
				new HibernateCallback<List<LinkinInfo>>() {

					@Override
					public List<LinkinInfo> doInHibernate(Session session)
							throws HibernateException, SQLException {
						String hql = "from LinkinInfo li ORDER BY li.id DESC";
						if (lazy) {
							hql = "from LinkinInfo li left join fetch li.ownUser ORDER BY li.id DESC";
						}
						Query query = session.createQuery(hql);
						query.setFirstResult((page - 1) * pageSize);
						query.setMaxResults(pageSize);
						List<LinkinInfo> list = query.list();
						return list;
					}
				});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LinkinInfo> queryListByUserId(long userId, boolean lazy) {
		String hql = "from LinkinInfo li where li.ownUser.id=?";
		if (lazy) {
			hql = "from LinkinInfo li left join fetch li.ownUser where li.ownUser.id=?";
		}
		return getHibernateTemplate().find(hql, userId);
	}

}
