package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Persistant.Teacher;
import net.sf.json.JSONObject;

public class LikeAndFinishNoShowUtil {

	public static void setLikeNo(Teacher teacher, SuperMap map) {
		Long likeNo = teacher.getLikeNumber();
		map.put("likeNo", NoShowUtil.getStrNumber(likeNo));
		map.put("likeno", NoShowUtil.getStrNumber(likeNo));
	}

	public static void setLikeNo(Teacher teacher, JSONObject map) {
		Long likeNo = teacher.getLikeNumber();
		map.put("likeNo", NoShowUtil.getStrNumber(likeNo));
		map.put("likenumber", NoShowUtil.getStrNumber(likeNo));
	}

	public static void setFinishNo(Teacher teacher, SuperMap map) {
		Long finishNo = teacher.getMaskFinishNumber();
		map.put("finishNo", NoShowUtil.getStrNumber(finishNo));
		map.put("finishno", NoShowUtil.getStrNumber(finishNo));
	}

	public static void setFinishNo(Teacher teacher, JSONObject map) {
		Long finishNo = teacher.getMaskFinishNumber();
		map.put("finishNo", NoShowUtil.getStrNumber(finishNo));
		map.put("finishnumber", NoShowUtil.getStrNumber(finishNo));
	}
	
	public static void setLikeNo(ServicePro servicePro, SuperMap map) {
		Long likeNo = servicePro.getLikeNo();
		map.put("likeNo", NoShowUtil.getStrNumber(likeNo));
	}
	
	public static void setFinishNo(ServicePro servicePro, SuperMap map) {
		Long finishNo = servicePro.getMaskFinishNo();
		map.put("finishNo", NoShowUtil.getStrNumber(finishNo));
	}

}
