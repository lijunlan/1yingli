package cn.yiyingli.Handle.Service;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Service.UserMarkService;

public class FRewardTeacherService extends MsgService {

	private RewardService rewardService;

	private UserMarkService userMarkService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	public RewardService getRewardService() {
		return rewardService;
	}

	public void setRewardService(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId") && getData().containsKey("money")
				&& getData().containsKey("teacherName") || getData().containsKey("uid");
	}

	@Override
	public void doit() {
		// TODO Auto-generated method stub

	}

}
