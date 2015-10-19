package cn.yiyingli.Handle.Service;

import java.util.ArrayList;
import java.util.List;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Background;
import cn.yiyingli.Service.BackgroundService;
import cn.yiyingli.Util.MsgUtil;

public class TGetBackgroundsService extends MsgService {

	private BackgroundService backgroundService;

	public BackgroundService getBackgroundService() {
		return backgroundService;
	}

	public void setBackgroundService(BackgroundService backgroundService) {
		this.backgroundService = backgroundService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("page") || true;
	}

	@Override
	public void doit() {
		String page = (String) getData().get("page");
		if (page != null) {
			try {
				int p = Integer.valueOf(page);
				List<Background> backgrounds = getBackgroundService().queryList(p, 12);
				List<String> urls = new ArrayList<String>();
				for (Background b : backgrounds) {
					urls.add(b.getUrl());
				}
				setResMsg(MsgUtil.getSuccessMap().put("urls", urls).finishByJson());
			} catch (Exception e) {
				setResMsg(MsgUtil.getErrorMsg("input data is wrong"));
				return;
			}
		} else {
			List<Background> backgrounds = getBackgroundService().queryList();
			List<String> urls = new ArrayList<String>();
			for (Background b : backgrounds) {
				urls.add(b.getUrl() + "@!bg");
			}
			setResMsg(MsgUtil.getSuccessMap().put("urls", urls).finishByJson());
		}
	}
}
