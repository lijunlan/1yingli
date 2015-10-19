package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.TipDao;
import cn.yiyingli.Persistant.Tip;

public class TipDaoImpl extends HibernateDaoSupport implements TipDao {

	@Override
	public void save(Tip tip) {
		getHibernateTemplate().save(tip);
	}

	@Override
	public Long saveAndReturnId(Tip tip) {
		getHibernateTemplate().save(tip);
		return tip.getId();
	}

	@Override
	public void remove(Tip tip) {
		getHibernateTemplate().delete(tip);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Tip t where t.id=?", id);
	}

	@Override
	public void update(Tip tip) {
		getHibernateTemplate().update(tip);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public Tip query(long id) {
		String hql = "from Tip t where t.id=?";
		@SuppressWarnings("unchecked")
		List<Tip> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tip> queryList() {
		String hql = "from Tip t ORDER BY t.createTime DESC";
		return getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tip> queryNormalList() {
		String hql = "from Tip t where t.id>16 ORDER BY t.name";
		return getHibernateTemplate().find(hql);
	}

}
