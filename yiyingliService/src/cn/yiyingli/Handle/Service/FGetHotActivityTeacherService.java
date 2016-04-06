package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExContentAndPage;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.ContentAndPage;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.ContentAndPageService;
import cn.yiyingli.Util.MsgUtil;

import java.util.List;

public class FGetHotActivityTeacherService extends MsgService {
	private ContentAndPageService contentAndPageService;

	@Override
	protected boolean checkData() {
		return getData().containsKey("page") && getData().containsKey("pageSize");
	}

	public ContentAndPageService getContentAndPageService() {
		return contentAndPageService;
	}

	public void setContentAndPageService(ContentAndPageService contentAndPageService) {
		this.contentAndPageService = contentAndPageService;
	}

	@Override
	public void doit() {
		int p = getData().getInt("page");
		int size = getData().getInt("pageSize");
		List<ContentAndPage> teachers = getContentAndPageService().queryListWithTeacherOrderByTeacherMile(p,size);
		ExList toSend = new ExArrayList();
		for (ContentAndPage contentAndPage : teachers) {
			SuperMap map = new SuperMap();
			ExContentAndPage.assembleTeacherForUser(contentAndPage, map);
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", toSend).finishByJson());
	}
}
