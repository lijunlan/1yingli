package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.BackingComment;
import cn.yiyingli.Service.UserService;

public class EXBackingComment {

	public static void assembleSimple(BackingComment backingComment, SuperMap map) {
		map.put("backingCommentId", backingComment.getId());
		map.put("content", backingComment.getContent());
		map.put("nickName", backingComment.getUser().getNickName());
		map.put("createTime", backingComment.getCreateTime());
		if (backingComment.getUser().getTeacherState().equals(UserService.TEACHER_STATE_ON_SHORT) ) {
			map.put("simpleInfo", backingComment.getUser().getTeacher().getSimpleInfo());
		}
		map.put("iconUrl", backingComment.getUser().getIconUrl());
	}

	public static void assembleToTeacher(BackingComment backingComment, SuperMap map) {
		assembleSimple(backingComment, map);
		int display = backingComment.getDisplay() ? 1 : 0;
		map.put("display", display);
		map.put("weight", backingComment.getWeight());
	}

	public static void assembleToUser(BackingComment backingComment, SuperMap map) {
		assembleSimple(backingComment, map);
		map.put("teacherName", backingComment.getTeacher().getName());
		map.put("teacherSimpleInfo", backingComment.getTeacher().getSimpleInfo());
	}
}
