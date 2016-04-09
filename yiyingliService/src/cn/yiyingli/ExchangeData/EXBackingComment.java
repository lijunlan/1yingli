package cn.yiyingli.ExchangeData;

import cn.yiyingli.Persistant.BackingComment;

public class EXBackingComment {

	public static void assembleSimple(BackingComment backingComment,SuperMap map) {
		map.put("backingCommentId",backingComment.getId());
		map.put("content",backingComment.getContent());
		map.put("nickName", backingComment.getUser().getNickName());
		map.put("iconUrl",backingComment.getUser().getIconUrl());
		map.put("display",backingComment.getDisplay());

	}
}
