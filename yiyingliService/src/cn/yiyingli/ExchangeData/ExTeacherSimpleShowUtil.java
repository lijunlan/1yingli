package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.StudyExperience;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.WorkExperience;

public class ExTeacherSimpleShowUtil {

	public static void getSimpleShowByTip(Teacher teacher, SuperMap map, long tid) {
		map.put("simpleinfo", teacher.getSimpleInfo());
		if (teacher.getStudyExperiences().size() > 0) {
			StudyExperience studyExperience = teacher.getStudyExperiences()
					.get(teacher.getStudyExperiences().size() - 1);
			map.put("schoolName", studyExperience.getSchoolName());
			map.put("major", studyExperience.getMajor());
		} else {
			map.put("schoolName", "");
			map.put("major", "");
		}
		if (teacher.getWorkExperiences().size() > 0) {
			WorkExperience workExperience = teacher.getWorkExperiences().iterator().next();
			map.put("companyName", workExperience.getCompanyName());
			map.put("position", workExperience.getPosition());
		} else {
			map.put("companyName", "");
			map.put("position", "");
		}
		if (tid == 1L || tid == 8L) {
			if (teacher.getStudyExperiences().size() > 0) {
				StudyExperience studyExperience = teacher.getStudyExperiences()
						.get(teacher.getStudyExperiences().size() - 1);
				map.put("simpleShow2", studyExperience.getMajor());
				map.put("simpleShow1", studyExperience.getSchoolName());
			} else if (teacher.getWorkExperiences().size() > 0) {
				WorkExperience workExperience = teacher.getWorkExperiences().iterator().next();
				map.put("simpleShow2", workExperience.getPosition());
				map.put("simpleShow1", workExperience.getCompanyName());
			} else {
				map.put("simpleShow2", "");
				map.put("simpleShow1", "");
			}
		} else if (tid == 2L || tid == 4L) {
			if (teacher.getWorkExperiences().size() > 0) {
				WorkExperience workExperience = teacher.getWorkExperiences().iterator().next();
				map.put("simpleShow2", workExperience.getPosition());
				map.put("simpleShow1", workExperience.getCompanyName());
			} else if (teacher.getStudyExperiences().size() > 0) {
				StudyExperience studyExperience = teacher.getStudyExperiences()
						.get(teacher.getStudyExperiences().size() - 1);
				map.put("simpleShow2", studyExperience.getMajor());
				map.put("simpleShow1", studyExperience.getSchoolName());
			} else {
				map.put("simpleShow2", "");
				map.put("simpleShow1", "");
			}
		} else {
			map.put("simpleShow2", "");
			map.put("simpleShow1", "");
		}
	}

	public static void getSimpleShowByTip(Teacher teacher, SuperMap map) {
		map.put("simpleinfo", teacher.getSimpleInfo());
		if ((teacher.getTipMark() & (2L | 4L)) != 0) {
			if (teacher.getWorkExperiences().size() > 0) {
				WorkExperience workExperience = teacher.getWorkExperiences().iterator().next();
				map.put("simpleShow2", workExperience.getPosition());
				map.put("simpleShow1", workExperience.getCompanyName());
			} else if (teacher.getStudyExperiences().size() > 0) {
				StudyExperience studyExperience = teacher.getStudyExperiences()
						.get(teacher.getStudyExperiences().size() - 1);
				map.put("simpleShow2", studyExperience.getMajor());
				map.put("simpleShow1", studyExperience.getSchoolName());
			} else {
				map.put("simpleShow2", "");
				map.put("simpleShow1", "");
			}
		} else if ((teacher.getTipMark() & (1L | 8L)) != 0) {
			if (teacher.getStudyExperiences().size() > 0) {
				StudyExperience studyExperience = teacher.getStudyExperiences()
						.get(teacher.getStudyExperiences().size() - 1);
				map.put("simpleShow2", studyExperience.getMajor());
				map.put("simpleShow1", studyExperience.getSchoolName());
			} else if (teacher.getWorkExperiences().size() > 0) {
				WorkExperience workExperience = teacher.getWorkExperiences().iterator().next();
				map.put("simpleShow2", workExperience.getPosition());
				map.put("simpleShow1", workExperience.getCompanyName());
			} else {
				map.put("simpleShow2", "");
				map.put("simpleShow1", "");
			}
		} else {
			map.put("simpleShow2", "");
			map.put("simpleShow1", "");
		}
	}
}
