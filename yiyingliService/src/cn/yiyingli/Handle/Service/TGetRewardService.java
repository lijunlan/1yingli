package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Util.MsgUtil;

public class TGetRewardService extends TMsgService {

	public RewardService rewardService;

	public RewardService getRewardService() {
		return rewardService;
	}

	public void setRewardService(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	@Override
	public void doit() {
		long rewardNo = getRewardService().queryRewardNoByTeacher(getTeacher().getId());
		float allMoney = getRewardService().queryMoneyByTeacherUserPayed(getTeacher().getId());
		float shouldPayMoney = getRewardService().queryMoneyByTeacherShouldPay(getTeacher().getId());
		setResMsg(MsgUtil.getSuccessMap().put("allReward", allMoney).put("canGetReward", shouldPayMoney)
				.put("gotReward", allMoney - shouldPayMoney).put("rewardNo", rewardNo).finishByJson());
	}

}
