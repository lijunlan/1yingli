package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Teacher;
import net.sf.json.JSONObject;

public class LikeNoShowUtil {

	public static void setLikeNo(Teacher teacher, SuperMap map) {
		Long likeNo = teacher.getLikeNumber();
		map.put("likeNo", NoShowUtil.getStrNumber(likeNo));
	}

	public static void setLikeNo(Teacher teacher, JSONObject map) {
		Long likeNo = teacher.getLikeNumber();
		map.put("likeNo", NoShowUtil.getStrNumber(likeNo));
		map.put("likenumber", NoShowUtil.getStrNumber(likeNo));
	}

	public static void setFinishNo(Teacher teacher, SuperMap map) {
		Long finishNo = teacher.getMaskNumber() == null ? teacher.getFinishOrderNumber()
				: teacher.getFinishOrderNumber() + teacher.getMaskNumber();
		map.put("finishNo", NoShowUtil.getStrNumber(finishNo));
		map.put("finishno", NoShowUtil.getStrNumber(finishNo));
	}

	public static void setFinishNo(Teacher teacher, JSONObject map) {
		Long finishNo = teacher.getFinishOrderNumber();
		map.put("finishNo", NoShowUtil.getStrNumber(finishNo));
		map.put("finishnumber", NoShowUtil.getStrNumber(finishNo));
	}
}
