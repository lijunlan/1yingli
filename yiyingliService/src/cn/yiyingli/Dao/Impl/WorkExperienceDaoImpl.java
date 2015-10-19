package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.WorkExperienceDao;
import cn.yiyingli.Persistant.WorkExperience;

public class WorkExperienceDaoImpl extends HibernateDaoSupport implements WorkExperienceDao {

	@Override
	public void save(WorkExperience workExperience) {
		getHibernateTemplate().save(workExperience);
	}

	@Override
	public Long saveAndReturnId(WorkExperience workExperience) {
		getHibernateTemplate().save(workExperience);
		return workExperience.getId();
	}

	@Override
	public void remove(WorkExperience workExperience) {
		getHibernateTemplate().delete(workExperience);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from WorkExperience we where we.id=?", id);
	}

	@Override
	public void removeByTeacherId(long teacherId) {
		getHibernateTemplate().bulkUpdate("delete from WorkExperience we where we.ownTeacher.id=?", teacherId);
	}

	@Override
	public void update(WorkExperience workExperience) {
		getHibernateTemplate().update(workExperience);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public WorkExperience query(long id, boolean lazy) {
		String hql = "from WorkExperience we where we.id=?";
		if (lazy) {
			hql = "from WorkExperience we left join fetch we.ownTeacher where we.id=?";
		}
		@SuppressWarnings("unchecked")
		List<WorkExperience> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<WorkExperience> queryListByTeahcerId(long teacherId, boolean lazy) {
		String hql = "from WorkExperience we where we.ownTeacher.id=?";
		if (lazy) {
			hql = "from WorkExperience we left join fetch we.ownTeacher where we.ownTeacher.id=?";
		}
		@SuppressWarnings("unchecked")
		List<WorkExperience> list = getHibernateTemplate().find(hql, teacherId);
		return list;
	}

}
