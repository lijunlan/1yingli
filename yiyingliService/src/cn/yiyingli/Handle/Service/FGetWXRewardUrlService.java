package cn.yiyingli.Handle.Service;

import cn.yiyingli.ExchangeData.ExRewardForPay;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.SendMessageToWXUtil;

public class FGetWXRewardUrlService extends MsgService {

	private UserMarkService userMarkService;

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId") && getData().containsKey("money")
				&& getData().containsKey("teacherName") || getData().containsKey("uid")
				|| getData().containsKey("passageId");
	}

	@Override
	public void doit() {
		String teacherId = getData().getString("teacherId");
		String teacherName = getData().getString("teacherName");
		String money = getData().getString("money");
		String uid = getData().getString("uid");
		String passageId = getData().getString("passageId");

		String userId = null;
		String userName = null;
		if (uid != null) {
			LogUtil.info("receive>>ALIPAY>>reward--->uid:" + uid, this.getClass());
			User user = userMarkService.queryUser(uid);
			if (user != null) {
				userId = String.valueOf(user.getId());
				userName = user.getName();
			}
		}

		String oid = ExRewardForPay.getRewardNo(Long.valueOf(teacherId));
		// 订单名称
		String subject = "打赏-" + teacherName;
		// 必填

		int tempmoney = (int) (Float.valueOf(money) * 100F);
		// 付款金额
		String total_fee = String.valueOf(tempmoney >= 1 ? tempmoney : 1);
		// 必填

		SuperMap map = new SuperMap();
		map.put("extra_param",
				ExRewardForPay.getExtraParams(teacherId, teacherName, money, uid, passageId, userId, userName));
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
