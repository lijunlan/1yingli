package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.Passage;

public class ExPassage {

	public static void assembleDetail(Passage passage, SuperMap map) {
		map.put("title", passage.getTitle());
		map.put("createTime", passage.getCreateTime());
		map.put("editorName", passage.getEditorName());
		map.put("imageUrl", passage.getImageUrl());
		map.put("teacherName", passage.getOwnTeacher().getName());
		map.put("teacherIcon", passage.getOwnTeacher().getIconUrl());
		map.put("teacherId", passage.getOwnTeacher().getId());
		map.put("tag", passage.getTag());
		map.put("likeno", passage.getLikeNumber());
	}

	public static void assembleSimple(Passage p, SuperMap map) {
		map.put("title", p.getTitle());
		map.put("simplecontent", p.getContent().substring(0, 50) + "...");
		map.put("imageUrl", p.getImageUrl());
		map.put("passageId", p.getId());
		map.put("createTime", p.getCreateTime());
		map.put("likeno", p.getLikeNumber());
		map.put("lookno", p.getLookNumber());
		map.put("tag", p.getTag());
	}

	public static void assembleForManager(Passage p, SuperMap map) {
		map.put("content", p.getContent());
		map.put("content", p.getCreateTime());
		map.put("content", p.getEditorName());
		map.put("content", p.getId());
		map.put("content", p.getImageUrl());
		map.put("content", p.getLikeNumber());
		map.put("content", p.getLookNumber());
		map.put("content", p.getOwnTeacher().getId());
		map.put("content", p.getRefuseReason());
		map.put("content", p.getOnshow());
		map.put("content", p.getState());
		map.put("content", p.getTag());
		map.put("content", p.getTitle());
	}

}
