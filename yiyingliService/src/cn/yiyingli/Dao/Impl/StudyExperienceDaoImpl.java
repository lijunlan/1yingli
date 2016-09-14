package cn.yiyingli.Dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.StudyExperienceDao;
import cn.yiyingli.Persistant.StudyExperience;

public class StudyExperienceDaoImpl extends HibernateDaoSupport implements StudyExperienceDao {

	@Override
	public void save(StudyExperience studyExperience) {
		getHibernateTemplate().save(studyExperience);
	}

	@Override
	public Long saveAndReturnId(StudyExperience studyExperience) {
		getHibernateTemplate().save(studyExperience);
		return studyExperience.getId();
	}

	@Override
	public void remove(StudyExperience studyExperience) {
		getHibernateTemplate().delete(studyExperience);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from StudyExperience se where se.id=?", id);
	}

	@Override
	public void removeByTeacherId(long teacherId) {
		getHibernateTemplate().bulkUpdate("delete from StudyExperience se where se.ownTeacher.id=?", teacherId);
	}

	@Override
	public void update(StudyExperience studyExperience) {
		getHibernateTemplate().update(studyExperience);
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public StudyExperience query(long id, boolean lazy) {
		String hql = "from StudyExperience se where se.id=?";
		if (lazy) {
			hql = "from StudyExperience se left join fetch se.ownTeacher where se.id=?";
		}
		@SuppressWarnings("unchecked")
		List<StudyExperience> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<StudyExperience> queryListByTeahcerId(long teacherId, boolean lazy) {
		String hql = "from StudyExperience se where se.ownTeacher.id=?";
		if (lazy) {
			hql = "from StudyExperience se left join fetch se.ownTeacher where se.ownTeacher.id=?";
		}
		@SuppressWarnings("unchecked")
		List<StudyExperience> list = getHibernateTemplate().find(hql, teacherId);
		return list;
	}

}
