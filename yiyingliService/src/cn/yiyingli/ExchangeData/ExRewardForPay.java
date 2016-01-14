package cn.yiyingli.ExchangeData;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Random;

import cn.yiyingli.Persistant.Reward;
import cn.yiyingli.Service.RewardService;
import cn.yiyingli.Util.LogUtil;

public class ExRewardForPay {

	public static String getRewardNo(long teacherId) {
		return "" + Calendar.getInstance().get(Calendar.YEAR) + new Random().nextInt(10) + new Random().nextInt(10)
				+ new Random().nextInt(10) + (100000000L + teacherId);
	}

	@SuppressWarnings("deprecation")
	public static String getExtraParams(String teacherId, String teacherName, String money, String uid,
			String passageId, String userId, String userName) {
		return "teacherId^" + teacherId + "|" + "teacherName^" + URLEncoder.encode(teacherName) + "|" + "money^" + money
				+ (uid == null ? "" : "|userId^" + userId + "|" + "userName^" + userName)
				+ (passageId == null ? "" : "|passageId^" + passageId);
	}

	public static void dealReward(RewardService rewardService, String extra_common_param, String rewardNo) {
		String[] params = extra_common_param.split("\\|");
		SuperMap map = new SuperMap();
		for (String param : params) {
			LogUtil.info(param, ExRewardForPay.class);
			String[] temp = param.split("\\^");
			String key = temp[0];
			@SuppressWarnings("deprecation")
			String value = URLDecoder.decode(temp[1]);
			map.put(key, value);
		}
		Reward reward = new Reward();
		String time = Calendar.getInstance().getTimeInMillis() + "";
		reward.setCreateTime(time);
		reward.setFinishPay(true);
		reward.setFinishSalary(false);
		reward.setMoney(Float.valueOf(map.finish().getString("money")));
		reward.setPayTime(time);
		reward.setRewardNo(rewardNo);
		reward.setTeacherId(map.finish().getLong("teacherId"));
		reward.setTeacherName(map.finish().getString("teacherName"));
		if (map.finish().containsKey("userId") && map.finish().containsKey("userName")) {
			reward.setUserId((map.finish().getLong("userId")));
			reward.setUserName(map.finish().getString("userName"));
		}
		if (map.finish().containsKey("passageId")) {
			reward.setPassageId(map.finish().getLong("passageId"));
		}
		rewardService.save(reward);
	}
}
