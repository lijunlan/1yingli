package cn.yiyingli.Service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import cn.yiyingli.Dao.StudyExperienceDao;
import cn.yiyingli.Dao.TServiceDao;
import cn.yiyingli.Dao.TeacherDao;
import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.Dao.WorkExperienceDao;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserLikeTeacher;
import cn.yiyingli.Service.TeacherService;

public class TeacherServiceImpl implements TeacherService {

	private TeacherDao teacherDao;

	private UserDao userDao;

	private TServiceDao tServiceDao;

	private StudyExperienceDao studyExperienceDao;

	private WorkExperienceDao workExperienceDao;

	public TeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public StudyExperienceDao getStudyExperienceDao() {
		return studyExperienceDao;
	}

	public void setStudyExperienceDao(StudyExperienceDao studyExperienceDao) {
		this.studyExperienceDao = studyExperienceDao;
	}

	public WorkExperienceDao getWorkExperienceDao() {
		return workExperienceDao;
	}

	public void setWorkExperienceDao(WorkExperienceDao workExperienceDao) {
		this.workExperienceDao = workExperienceDao;
	}

	public TServiceDao gettServiceDao() {
		return tServiceDao;
	}

	public void settServiceDao(TServiceDao tServiceDao) {
		this.tServiceDao = tServiceDao;
	}

	@Override
	public void save(Teacher teacher) {
		getTeacherDao().save(teacher);
	}

	@Override
	public Long saveAndReturnId(Teacher teacher) {
		return getTeacherDao().saveAndReturnId(teacher);
	}

	@Override
	public void saveWithDetailInfo(Teacher teacher) {
		getUserDao().update(teacher.getUser());
		getTeacherDao().save(teacher);
		gettServiceDao().save(teacher.gettService());

	}

	@Override
	public void remove(Teacher teacher) {
		getTeacherDao().remove(teacher);
	}

	@Override
	public void remove(long id) {
		getTeacherDao().remove(id);
	}

	@Override
	public void removeAllTip(long teacherId) {
		getTeacherDao().removeAllTip(teacherId);
	}

	@Override
	public void update(Teacher teacher) {
		getTeacherDao().update(teacher);
	}

	@Override
	public void updateWithUser(Teacher teacher, long userId) {
		User user = getUserDao().query(userId, false);
		user.setEmail(teacher.getEmail());
		user.setPhone(teacher.getPhone());
		user.setName(teacher.getName());
		user.setAddress(teacher.getAddress());
		getUserDao().merge(user);
		getTeacherDao().merge(teacher);
	}

	@Override
	public void updateWithDetailInfo(Teacher teacher) {
		getUserDao().update(teacher.getUser());
		getTeacherDao().update(teacher);
		gettServiceDao().update(teacher.gettService());
	}

	@Override
	public Teacher query(long id, boolean lazy) {
		return getTeacherDao().query(id, lazy);
	}

	@Override
	public List<Teacher> queryByIds(List<Long> ids) {
		long[] idarray = new long[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			idarray[i] = ids.get(i);
		}
		List<Teacher> teachers = getTeacherDao().queryByIds(idarray);
		List<Teacher> results = new ArrayList<Teacher>();
		for (int i = 0; i < ids.size(); i++) {
			for (int j = 0; j < teachers.size(); j++) {
				if (teachers.get(j).getId().longValue() == ids.get(i).longValue()) {
					results.add(teachers.get(j));
					break;
				}
			}
		}
		return results;
	}

	@Override
	public Boolean queryCheckLikeUser(long teacherId, long userId) {
		return getTeacherDao().queryCheckLikeUser(teacherId, userId);
	}

	@Override
	public Teacher queryWithTips(long id, boolean lazy) {
		return getTeacherDao().queryWithTips(id, lazy);
	}

	@Override
	public Teacher queryWithLikeUser(long teacherId, boolean lazy) {
		return getTeacherDao().queryWithLikeUser(teacherId, lazy);
	}

	@Override
	public List<Teacher> queryList(int page, int pageSize, boolean lazy) {
		return getTeacherDao().queryList(page, pageSize, lazy);
	}

	@Override
	public List<Teacher> queryListByKeyWord(String keyword, int page, int pageSize, boolean lazy) {
		return getTeacherDao().queryListByKeyWord(keyword, page, pageSize, lazy);
	}

	@Override
	public List<Teacher> queryListByKeyWord(String keyword, int page, boolean lazy) {
		return queryListByKeyWord(keyword, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Teacher> queryListByTip(int page, int pageSize, long tipMark, boolean lazy) {
		return getTeacherDao().queryListByTip(page, pageSize, tipMark, lazy);
	}

	@Override
	public List<Teacher> queryListByLevel(short level, int page, int pageSize, boolean lazy) {
		return getTeacherDao().queryListByLevel(level, page, pageSize, lazy);
	}

	@Override
	public List<Teacher> queryListOnservice(int page, int pageSize, boolean lazy) {
		return getTeacherDao().queryListOnservice(page, pageSize, lazy);
	}

	@Override
	public List<Teacher> queryList(int page, boolean lazy) {
		return queryList(page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Teacher> queryListByTip(int page, long tipMark, boolean lazy) {
		return queryListByTip(page, PAGE_SIZE_INT, tipMark, lazy);
	}

	@Override
	public List<Teacher> queryListByLevel(short level, int page, boolean lazy) {
		return queryListByLevel(level, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Teacher> queryListByTipAndLevel(int page, long tipMark, short level, boolean lazy) {
		return queryListByTipAndLevel(page, PAGE_SIZE_INT, tipMark, level, lazy);
	}

	@Override
	public List<Teacher> queryListByTipAndLevel(int page, int pageSize, long tipMark, short level, boolean lazy) {
		return getTeacherDao().queryListByTipAndLevel(page, pageSize, tipMark, level, lazy);
	}

	@Override
	public Teacher queryByUserId(long userid, boolean lazy) {
		return getTeacherDao().queryByUserId(userid, lazy);
	}

	@Override
	public Teacher queryByUserIdWithTService(long userid, boolean lazy) {
		return getTeacherDao().queryByUserIdWithTService(userid, lazy);
	}

	@Override
	public List<Teacher> queryLikeListByUserId(long userid, int page, boolean lazy) {
		return queryLikeListByUserId(userid, page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Teacher> queryLikeListByUserId(long userid, int page, int pageSize, boolean lazy) {
		return getTeacherDao().queryLikeListByUserId(userid, page, pageSize, lazy);
	}

	@Override
	public void updateUserLike(Teacher teacher, User user) {
		if (getTeacherDao().queryCheckLikeUser(teacher.getId(), user.getId())) {
			return;
		} else {
			UserLikeTeacher userLikeTeacher = new UserLikeTeacher();
			userLikeTeacher.setTeacher(teacher);
			userLikeTeacher.setUser(user);
			userLikeTeacher.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
			getUserDao().updateLikeTeacher(userLikeTeacher);
		}
	}

	@Override
	public void updateUserUnlike(long teacherId, long userId) {
		if (!getTeacherDao().queryCheckLikeUser(teacherId, userId)) {
			return;
		} else {
			getTeacherDao().removeUserLike(teacherId, userId);
		}
	}

	@Override
	public void updateStudyExp(Teacher teacher) {
		getStudyExperienceDao().removeByTeacherId(teacher.getId());
		update(teacher);
	}

	@Override
	public void updateWorkExp(Teacher teacher) {
		getWorkExperienceDao().removeByTeacherId(teacher.getId());
		update(teacher);
	}

	@Override
	public List<Teacher> queryListByTipAndLastId(long lastId, int size, long tipMark, boolean lazy) {
		return getTeacherDao().queryListByTipAndLastId(lastId, size, tipMark, lazy);
	}

	@Override
	public List<Teacher> queryListByTipAndLastId(long lastId, long tipMark, boolean lazy) {
		return queryListByTipAndLastId(lastId, PAGE_SIZE_INT, tipMark, lazy);
	}

	@Override
	public List<Teacher> queryListByTipAndFirstId(long firstId, long tipMark, boolean lazy) {
		return queryListByTipAndFirstId(firstId, PAGE_SIZE_INT, tipMark, lazy);
	}

	@Override
	public List<Teacher> queryListByTipAndFirstId(long firstId, int size, long tipMark, boolean lazy) {
		return getTeacherDao().queryListByTipAndFirstId(firstId, size, tipMark, lazy);
	}

	@Override
	public Teacher queryAll(long id) {
		return getTeacherDao().queryAll(id);
	}

	@Override
	public List<Teacher> queryByTipOrderByShow(int size, long tipMark, boolean lazy) {
		return getTeacherDao().queryByTipOrderByShow(size, tipMark, lazy);
	}

	@Override
	public List<Teacher> queryByTipOrderByShow(long tipMark, boolean lazy) {
		return getTeacherDao().queryByTipOrderByShow(TeacherService.PAGE_SIZE_INT, tipMark, lazy);
	}

	@Override
	public List<Teacher> queryListByHomePage(int pageSize) {
		return getTeacherDao().queryListByHomePage(pageSize);
	}

	@Override
	public List<Teacher> queryListBySale(int page, int pageSize) {
		return getTeacherDao().queryListBySale(page, pageSize);
	}

	@Override
	public List<Teacher> queryListByHomePage() {
		return getTeacherDao().queryListByHomePage(HOME_PAGE_SIZE);
	}

	@Override
	public List<Teacher> queryListBySale(int page) {
		return getTeacherDao().queryListBySale(page, SALE_PAGE_SIZE);
	}

}
