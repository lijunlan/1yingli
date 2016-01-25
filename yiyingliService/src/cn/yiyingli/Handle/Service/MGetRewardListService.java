package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.ExReward;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Reward;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Util.MsgUtil;

public class MGetRewardListService extends MMsgService {

	private RewardService rewardService;

	public RewardService getRewardService() {
		return rewardService;
	}

	public void setRewardService(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	public boolean checkData() {
		return super.checkData() && getData().containsKey("page");
	}

	@Override
	public void doit() {
		int page = getData().getInt("page");
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("22005"));
			return;
		}
		List<Reward> rewards = getRewardService().queryList(page, RewardService.PAGE_SIZE);
		ExList toSend = new ExArrayList();
		for (Reward reward : rewards) {
			SuperMap map = new SuperMap();
			ExReward.assembleForManager(reward, map);
			toSend.add(map.finish());
		}
		setResMsg(MsgUtil.getSuccessMap().put("data", toSend).finishByJson());
	}

}
