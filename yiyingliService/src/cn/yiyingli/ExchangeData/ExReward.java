package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Reward;

public class ExReward {
	public static void assembleForManager(Reward reward, SuperMap map) {
		map.put("teacherId", reward.getTeacherId());
		map.put("userName", reward.getUserName());
		map.put("passageId", reward.getPassageId());
		map.put("teacherName", reward.getTeacherName());
		map.put("time", reward.getCreateTime());
		map.put("money", reward.getMoney());
		map.put("finishSalary", reward.getFinishSalary());
		map.put("rewardId", reward.getRewardNo());
	}
}
