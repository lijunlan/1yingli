package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Teacher;

public class LikeNoShowUtil {

	public static void setLikeNo(Teacher teacher, SuperMap map) {
		Long likeNo = teacher.getLikeNumber();
		map.put("likeNo", NoShowUtil.getStrNumber(likeNo));
	}
}
