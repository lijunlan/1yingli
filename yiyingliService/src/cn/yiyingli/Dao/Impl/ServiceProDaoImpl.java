package cn.yiyingli.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.ServiceProDao;
import cn.yiyingli.Persistant.ServicePro;

public class ServiceProDaoImpl extends HibernateDaoSupport implements ServiceProDao {

	@Override
	public void save(ServicePro servicePro) {
		getHibernateTemplate().save(servicePro);
	}

	@Override
	public Long saveAndReturnId(ServicePro servicePro) {
		getHibernateTemplate().save(servicePro);
		return servicePro.getId();
	}

	@Override
	public void remove(ServicePro servicePro) {
		getHibernateTemplate().delete(servicePro);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from ServicePro sp where sp.id=?", id);
	}

	@Override
	public void update(ServicePro servicePro) {
		getHibernateTemplate().update(servicePro);
	}

	@Override
	public ServicePro querySimple(long id) {
		String hql = "from ServicePro sp where sp.id=?";
		@SuppressWarnings("unchecked")
		List<ServicePro> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<ServicePro> queryList(long[] ids, long teacherId) {
		if (ids.length <= 0)
			return new ArrayList<ServicePro>();
		String hql = "from ServicePro sp left fetch join sp.teacher where sp.teacher.id=" + teacherId + " and (sp.id="
				+ ids[0];
		if (ids.length > 1) {
			for (int i = 1; i < ids.length; i++) {
				hql = hql + " or sp.id=" + ids[i];
			}
		}
		hql = hql + ")";
		@SuppressWarnings("unchecked")
		List<ServicePro> list = getHibernateTemplate().find(hql);
		return list;
	}

}
