package cn.yiyingli.Handle.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.MsgUtil;

public class GetCommentListService extends UMsgService {

	private CommentService commentService;

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("page") && getData().containsKey("kind");
	}

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	@Override
	public void doit() {
		super.doit();
		User user = getUser();
		String kind = (String) getData().get("kind");
		short k = 0;
		if (String.valueOf(CommentService.COMMENT_KIND_FROMUSER_SHORT).equals(kind)) {
			k = CommentService.COMMENT_KIND_FROMUSER_SHORT;
		} else if (String.valueOf(CommentService.COMMENT_KIND_FROMTEACHER_SHORT).equals(kind)) {
			k = CommentService.COMMENT_KIND_FROMTEACHER_SHORT;
		} else {
			setResMsg(MsgUtil.getErrorMsgByCode("12009"));
			return;
		}

		long count = 0;
		if (k == CommentService.COMMENT_KIND_FROMTEACHER_SHORT) {
			count = user.getReceiveCommentNumber();
		} else {
			count = user.getSendCommentNumber();
		}
		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("count", count);
		if (getData().get("page").equals("max")) {
			if (count % CommentService.PAGE_SIZE_INT > 0)
				page = (int) (count / CommentService.PAGE_SIZE_INT) + 1;
			else
				page = (int) (count / CommentService.PAGE_SIZE_INT);
			if (page == 0)
				page = 1;
			toSend.put("page", page);
		} else {
			try {
				page = Integer.parseInt((String) getData().get("page"));
			} catch (Exception e) {
				setResMsg(MsgUtil.getErrorMsgByCode("12010"));
				return;
			}
			if (page <= 0) {
				setResMsg(MsgUtil.getErrorMsgByCode("12010"));
				return;
			}
		}
		List<Comment> comments = getCommentService().queryListByUserId(user.getId(), page, k, true);
		List<String> sends = new ArrayList<String>();
		for (Comment c : comments) {
			SuperMap map = new SuperMap();
			map.put("commentId", c.getId());
			map.put("content", c.getContent());
			map.put("score", c.getScore());
			map.put("createTime", SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(c.getCreateTime()))));
			map.put("name", c.getTeacher().getName());
			map.put("url", c.getTeacher().getIconUrl());
			map.put("teacherId", c.getTeacher().getId());
			sends.add(map.finishByJson());
		}
		setResMsg(toSend.put("data", Json.getJson(sends)).finishByJson());
	}

}
