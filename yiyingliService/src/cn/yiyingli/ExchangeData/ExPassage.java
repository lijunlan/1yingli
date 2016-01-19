package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Passage;

public class ExPassage {

	public static void assembleDetail(Passage passage, SuperMap map) {
		map.put("passageId", passage.getId());
		map.put("title", passage.getTitle());
		map.put("createTime", passage.getCreateTime());
		map.put("editorName", passage.getEditorName());
		map.put("imageUrl", passage.getImageUrl());
		map.put("teacherName", passage.getOwnTeacher().getName());
		map.put("teacherIcon", passage.getOwnTeacher().getIconUrl());
		map.put("teacherId", passage.getOwnTeacher().getId());
		map.put("tag", passage.getTag());
		map.put("content", passage.getContent());
		map.put("likeno", passage.getLikeNumber());
		map.put("summary", passage.getSummary());
		map.put("lookno", passage.getLookNumber());
		map.put("simpleinfo", passage.getOwnTeacher().getSimpleInfo());
		map.put("rewardNumber", passage.getRewardNumber());
	}

	public static void assembleSimple(Passage p, SuperMap map) {
		map.put("title", p.getTitle());
		map.put("summary", p.getSummary());
		map.put("imageUrl", p.getImageUrl());
		map.put("passageId", p.getId());
		map.put("createTime", p.getCreateTime());
		map.put("likeno", p.getLikeNumber());
		map.put("lookno", p.getLookNumber());
		map.put("tag", p.getTag());
	}

	public static void assembleForManager(Passage p, SuperMap map) {
		map.put("content", p.getContent());
		map.put("summary", p.getSummary());
		map.put("createTime", p.getCreateTime());
		map.put("editorName", p.getEditorName());
		map.put("passageId", p.getId());
		map.put("imageUrl", p.getImageUrl());
		map.put("likeNumber", p.getLikeNumber());
		map.put("lookNumber", p.getLookNumber());
		map.put("teacherId", p.getOwnTeacher().getId());
		map.put("refuseReason", p.getRefuseReason());
		map.put("onshow", p.getOnshow());
		map.put("state", p.getState());
		map.put("tag", p.getTag());
		map.put("title", p.getTitle());
		map.put("rewardNumber", p.getRewardNumber());
	}

}
