package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.MidKeeperDao;
import cn.yiyingli.Persistant.MidKeeper;

public class MidKeeperDaoImpl extends HibernateDaoSupport implements MidKeeperDao {

	@Override
	public void save(MidKeeper mk) {
		getHibernateTemplate().save(mk);
	}

	@Override
	public String find(String mid) {
		String hql = "from MidKeeper m where m.id = ?";
		@SuppressWarnings("unchecked")
		List<MidKeeper> list = getHibernateTemplate().find(hql, mid);
		if(list.size()==0)
			return null;
		else {
			return list.get(0).getName();
		}
	}

	@Override
	public void remove(String mid) {
		getHibernateTemplate().bulkUpdate("delete from MidKeeper m where m.id = ?", mid);
	}

}
