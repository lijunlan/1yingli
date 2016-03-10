package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Dao.ContentAndPageDao;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Persistant.Pages;
import cn.yiyingli.Persistant.Passage;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Service.PagesService;
import cn.yiyingli.Service.PassageService;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;

public class MCreateActivityContentService extends MMsgService {

	private ContentAndPageService contentAndPageService;

	private PassageService passageService;

	private TeacherService teacherService;

	private ServiceProService serviceProService;

	private PagesService pagesService;

	public PassageService getPassageService() {
		return passageService;
	}

	public void setPassageService(PassageService passageService) {
		this.passageService = passageService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public PagesService getPagesService() {
		return pagesService;
	}

	public void setPagesService(PagesService pagesService) {
		this.pagesService = pagesService;
	}

	public ContentAndPageService getContentAndPageService() {
		return contentAndPageService;
	}

	public void setContentAndPageService(ContentAndPageService contentAndPageService) {
		this.contentAndPageService = contentAndPageService;
	}

	@Override
	public boolean checkData() {
		boolean check = super.checkData() && getData().containsKey("weight") && getData().containsKey("contentStyle")
				&& getData().containsKey("activityDes") && getData().containsKey("pagesId");
		int style = getData().getInt("contentStyle");
		switch (style) {
		case ContentAndPageDao.STYLE_PASSAGE:
			return check && getData().containsKey("passageId");
		case ContentAndPageDao.STYLE_SERVICEPRO:
			return check && getData().containsKey("serviceProId");
		case ContentAndPageDao.STYLE_TEACHER:
			return check && getData().containsKey("teacherId");
		default:
			return false;
		}
	}

	@Override
	public void doit() {
		int style = getData().getInt("contentStyle");
		ContentAndPage contentAndPage = new ContentAndPage();
		contentAndPage.setActivityDes(getData().getString("activityDes"));
		contentAndPage.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
		contentAndPage.setWeight(getData().getInt("weight"));
		contentAndPage.setStyle(style);
		Pages pages = getPagesService().query(getData().getLong("pagesId"));
		if (pages == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("32016"));
			return;
		}
		contentAndPage.setPages(pages);
		if (style == ContentAndPageDao.STYLE_PASSAGE) {
			Passage passage = getPassageService().query(getData().getLong("passageId"));
			if (passage == null) {
				setResMsg(MsgUtil.getErrorMsgByCode("12020"));
				return;
			}
			contentAndPage.setPassage(passage);
		} else if (style == ContentAndPageDao.STYLE_SERVICEPRO) {
			ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"), false);
			if(servicePro==null){
				setResMsg(MsgUtil.getErrorMsgByCode("22008"));
				return;
			}
			contentAndPage.setServicePro(servicePro);
		} else if (style == ContentAndPageDao.STYLE_TEACHER) {
			Teacher teacher = getTeacherService().query(getData().getLong("teacherId"));
			if(teacher==null){
				setResMsg(MsgUtil.getErrorMsgByCode("22001"));
				return;
			}
			contentAndPage.setTeacher(teacher);
		}
		getContentAndPageService().save(contentAndPage);
	}

}
