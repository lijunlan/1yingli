package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.OperateDao;
import cn.yiyingli.Persistant.OperateRecord;

public class OperateDaoImpl extends HibernateDaoSupport implements OperateDao {

	@Override
	public void save(OperateRecord or) {
		getHibernateTemplate().save(or);
	}

	@Override
	public OperateRecord find(Long id) {
		String hql = "from OperateRecord o where o.id = ?";
		@SuppressWarnings("unchecked")
		List<OperateRecord> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<OperateRecord> find(String name) {
		String hql = "from OperateRecord o where o.name = '" + name + "'";
		@SuppressWarnings("unchecked")
		List<OperateRecord> list = getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<OperateRecord> find(String name, long beginTime, long endTime) {
		String hql = "from OperateRecord o where o.name = '" + name + "' and o.timeMills > " + beginTime
				+ " and o.timeMills < " + endTime;
		@SuppressWarnings("unchecked")
		List<OperateRecord> list = getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<OperateRecord> find(long begintime, long endTime) {
		String hql = "from OperateRecord o where o.timeMills > " + begintime + " and o.timeMills < " + endTime;
		@SuppressWarnings("unchecked")
		List<OperateRecord> list = getHibernateTemplate().find(hql);
		return list;
	}

}
