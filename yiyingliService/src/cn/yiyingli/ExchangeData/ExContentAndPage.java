package cn.yiyingli.ExchangeData;

import cn.yiyingli.Dao.ContentAndPageDao;
import cn.yiyingli.Persistant.ContentAndPage;

public class ExContentAndPage {

	public static void assembleForManager(ContentAndPage contentAndPage, SuperMap map) {
		map.put("contentId", contentAndPage.getId());
		map.put("activityDes", contentAndPage.getActivityDes());
		map.put("weight", contentAndPage.getWeight());
		map.put("contentStyle", contentAndPage.getStyle());
		switch (contentAndPage.getStyle()) {
		case ContentAndPageDao.STYLE_PASSAGE:
			map.put("passageId", contentAndPage.getPassage().getId());
			map.put("passageTitle", contentAndPage.getPassage().getTitle());
			break;
		case ContentAndPageDao.STYLE_SERVICEPRO:
			map.put("serviceProId", contentAndPage.getServicePro().getId());
			map.put("serviceProTitle", contentAndPage.getServicePro().getTitle());
			break;
		case ContentAndPageDao.STYLE_TEACHER:
			map.put("teacherId", contentAndPage.getTeacher().getId());
			map.put("teacherName", contentAndPage.getTeacher().getName());
			break;
		default:
			break;
		}
	}

	public static void assemblePassageForUser(ContentAndPage contentAndPage, SuperMap map) {
		map.put("activityDes", contentAndPage.getActivityDes());
		ExPassage.assembleSimple(contentAndPage.getPassage(), map);
		map.put("icon", contentAndPage.getPassage().getOwnTeacher().getIconUrl());
	}

	public static void assembleServiceProForUser(ContentAndPage contentAndPage, SuperMap map) {
		map.put("activityDes", contentAndPage.getActivityDes());
		ExServicePro.assembleSimpleServiceProForUser(contentAndPage.getServicePro(), map);
	}

	public static void assembleTeacherForUser(ContentAndPage contentAndPage, SuperMap map) {
		map.put("activityDes", contentAndPage.getActivityDes());
		ExTeacher.assembleSimpleForUser(contentAndPage.getTeacher(), map);
		map.put("mile", contentAndPage.getTeacher().getMile());
	}

}
