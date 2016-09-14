package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.ExchangeData.ExRewardForPay;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Reward;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMessageToWXUtil;

public class FGetWXRewardUrlService extends MsgService {

	private UserMarkService userMarkService;

	private RewardService rewardService;

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
				&& getData().containsKey("teacherName") || getData().containsKey("uid")
				|| getData().containsKey("passageId");
	}

	@Override
	public void doit() {
		Long teacherId = getData().getLong("teacherId");
		String teacherName = getData().getString("teacherName");
		String money = getData().getString("money");

		String uid = null;
		Long userId = null;
		String userName = null;
		Long passageId = null;
		if (getData().containsKey("uid")) {
			uid = getData().getString("uid");
			LogUtil.info("receive>>ALIPAY>>reward--->uid:" + uid, this.getClass());
			User user = userMarkService.queryUser(uid);
			if (user != null) {
				userId = user.getId();
				userName = user.getNickName();
			}
		}

		if (getData().containsKey("passageId")) {
			passageId = getData().getLong("passageId");
		}

		// 订单名称
		String subject = "打赏-" + teacherName;
		// 必填

		int tempmoney = (int) (Float.valueOf(money) * 100F);
		// 付款金额
		String total_fee = String.valueOf(tempmoney >= 1 ? tempmoney : 1);
		// 必填

		Reward reward = new Reward();
		String time = Calendar.getInstance().getTimeInMillis() + "";
		reward.setCreateTime(time);
		reward.setFinishPay(false);
		reward.setFinishSalary(false);
		reward.setMoney(Float.valueOf(money));
		reward.setTeacherId(teacherId);
		reward.setTeacherName(teacherName);
		reward.setUserId(userId);
		reward.setUserName(userName);

		reward.setPassageId(passageId);

		getRewardService().save(reward);

		String oid = ExRewardForPay.getRewardNo(reward.getId());

		SuperMap map = new SuperMap();
		map.put("extra_param", ExRewardForPay.getExtraParams(reward.getId()));
		map.put("content", "【一英里】" + subject);
		map.put("oid", oid);
		map.put("ip", getData().getString("IP").split(",")[0]);
		map.put("money", total_fee);
		String url = SendMessageToWXUtil.unifyOrder(map.finish());
		if (url.equals("")) {
			setResMsg(MsgUtil.getErrorMsgByCode("43003"));
		} else {
			setResMsg(MsgUtil.getSuccessMap().put("url", url).finishByJson());
		}
	}

}
