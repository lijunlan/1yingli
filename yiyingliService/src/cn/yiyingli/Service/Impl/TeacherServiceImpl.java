package cn.yiyingli.Service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.yiyingli.Dao.*;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Persistant.UserLikeTeacher;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.SendMsgToBaiduUtil;

public class TeacherServiceImpl implements TeacherService {

	private TeacherDao teacherDao;

	private UserDao userDao;

	private ServiceProDao serviceProDao;

	private StudyExperienceDao studyExperienceDao;

	private WorkExperienceDao workExperienceDao;

	private UserLikeTeacherDao userLikeTeacherDao;

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

	public ServiceProDao getServiceProDao() {
		return serviceProDao;
	}

	public void setServiceProDao(ServiceProDao serviceProDao) {
		this.serviceProDao = serviceProDao;
	}

	public UserLikeTeacherDao getUserLikeTeacherDao() {
		return userLikeTeacherDao;
	}

	public void setUserLikeTeacherDao(UserLikeTeacherDao userLikeTeacherDao) {
		this.userLikeTeacherDao = userLikeTeacherDao;
	}

	@Override
	public void save(Teacher teacher) {
		getTeacherDao().save(teacher);
		if (teacher.getOnService()) {
			SendMsgToBaiduUtil.updateTeacherData(teacher);
		}
	}

	@Override
	public Long saveAndReturnId(Teacher teacher) {
		if (teacher.getOnService()) {
			SendMsgToBaiduUtil.updateTeacherData(teacher);
		}
		return getTeacherDao().saveAndReturnId(teacher);
	}

	@Override
	public void saveWithDetailInfo(Teacher teacher) {
		getTeacherDao().save(teacher);
		getUserDao().update(teacher.getUser());
		if (teacher.getOnService()) {
			SendMsgToBaiduUtil.updateTeacherData(teacher);
		}
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
	public void update(Teacher teacher, boolean refreshRecommend) {
		getTeacherDao().update(teacher);
		if (refreshRecommend && teacher.getOnService()) {
			SendMsgToBaiduUtil.updateTeacherData(teacher);
		}
	}

	@Override
	public void updateAddLookNumber(long teacherId, long number) {
		getTeacherDao().updateAddLookNumber(teacherId, 1L);
	}

	@Override
	public void updateAddMile(long teacherId, long mile) {
		getTeacherDao().updateAddMile(teacherId, mile);
	}

	@Override
	public boolean updateAddSubMile(long teacherId, long subMile) {
		if (getTeacherDao().queryCheckMile(teacherId, subMile)) {
			getTeacherDao().updateAddSubMile(teacherId, subMile);
			return true;
		}
		return false;
	}

	@Override
	public void updateWithUser(Teacher teacher, long userId, boolean refreshRecommend) {
		User user = getUserDao().query(userId, false);
		user.setEmail(teacher.getEmail());
		user.setPhone(teacher.getPhone());
		user.setName(teacher.getName());
		user.setAddress(teacher.getAddress());
		getUserDao().merge(user);
		getTeacherDao().merge(teacher);
		if (refreshRecommend && teacher.getOnService()) {
			SendMsgToBaiduUtil.updateTeacherData(teacher);
		}
	}

	@Override
	public void updateWithDetailInfo(Teacher teacher, boolean refreshRecommend) {
		getTeacherDao().update(teacher);
		if (refreshRecommend && teacher.getOnService()) {
			SendMsgToBaiduUtil.updateTeacherData(teacher);
		}
	}

	@Override
	public Teacher query(long id) {
		return getTeacherDao().query(id);
	}

	@Override
	public Teacher queryWithOutStatue(long id) {
		return getTeacherDao().queryWithOutStatue(id);
	}

	@Override
	public Teacher queryWithUser(long id) {
		return getTeacherDao().queryWithUser(id);
	}

	@Override
	public List<Teacher> queryByNameOrUsername(String word) {
		return getTeacherDao().queryByNameOrUsername(word);
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
	public Teacher queryWithServiceProList(long id) {
		return getTeacherDao().queryWithServiceProList(id);
	}

	@Override
	public Teacher queryForTeacher(long id) {
		return getTeacherDao().queryForTeacher(id);
	}

	@Override
	public Teacher queryWithLikeUser(long teacherId) {
		return getTeacherDao().queryWithLikeUser(teacherId);
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
	public List<Teacher> queryListOnservice(int page, int pageSize, boolean lazy) {
		return getTeacherDao().queryListOnservice(page, pageSize, lazy);
	}

	@Override
	public List<Teacher> queryList(int page, boolean lazy) {
		return queryList(page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public Teacher queryByUserId(long userid) {
		return getTeacherDao().queryByUserId(userid);
	}

	@Override
	public Teacher queryByUserIdWithServicePro(long userid) {
		return getTeacherDao().queryByUserIdWithServicePro(userid);
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
	public void updateStudyExp(Teacher teacher, boolean refreshRecommend) {
		getStudyExperienceDao().removeByTeacherId(teacher.getId());
		if (refreshRecommend && teacher.getOnService()) {
			getTeacherDao().update(teacher);
		}
	}

	@Override
	public void updateWorkExp(Teacher teacher, boolean refreshRecommend) {
		getWorkExperienceDao().removeByTeacherId(teacher.getId());
		if (refreshRecommend && teacher.getOnService()) {
			getTeacherDao().update(teacher);
		}
	}

	@Override
	public long querySumNoByLikedTeacherId(long likedTeacherId) {
		return getUserLikeTeacherDao().querySumNoByLikedTeacherId(likedTeacherId);
	}

	@Override
	public Teacher queryAll(long id) {
		return getTeacherDao().queryAll(id);
	}

	@Override
	public Teacher queryForUser(long id) {
		return getTeacherDao().queryForUser(id);
	}

	@Override
	public List<Teacher> queryListByActivity(String activityKey, int page, int pageSize) {
		return getTeacherDao().queryListByActivity(activityKey, page, pageSize);
	}

	@Override
	public List<Teacher> queryListByLikedTeacherId(long likedTeacherId, int page) {
		return queryListByLikedTeacherId(likedTeacherId, page, PAGE_SIZE_INT);
	}

	@Override
	public List<Teacher> queryListByLikedTeacherId(long likedTeacherId, int page, int pageSize) {
		return getUserLikeTeacherDao().queryTeacherListByLikedTeacherId(likedTeacherId, page, pageSize);
	}
}
