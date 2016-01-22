package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MMsgService;
import cn.yiyingli.Persistant.Reward;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Util.MsgUtil;

public class MFinishPayRewardService extends MMsgService {

	private RewardService rewardService;

	public RewardService getRewardService() {
		return rewardService;
	}

	public void setRewardService(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	public boolean checkData() {
		return super.checkData() && getData().containsKey("rewardId");
	}

	@Override
	public void doit() {
		Reward reward = getRewardService().query(getData().getString("rewardId"));
		if (reward == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("42002"));
			return;
		}
		reward.setFinishSalary(true);
		getRewardService().update(reward);
		setResMsg(MsgUtil.getSuccessMsg("finish salary reward successfully"));
	}

}
