package cn.yiyingli.Handle.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Util.MsgUtil;

public class FGetCommentListService extends MsgService {

	private CommentService commentService;

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId") && getData().containsKey("page");
	}

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

	@Override
	public void doit() {

		Teacher teacher = getTeacher();

		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		long count = teacher.getCommentNumber();
		toSend.put("count", count);
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("22005"));
			return;
		}
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("22005"));
			return;
		}
		List<Comment> comments = getCommentService().queryListByTeacherId(teacher.getId(), page,
				CommentService.COMMENT_KIND_FROMUSER_SHORT, true);
		ExList sends = new ExArrayList();
		for (Comment c : comments) {
			SuperMap map = new SuperMap();
			map.put("commentId", c.getId());
			map.put("content", c.getContent());
			map.put("score", c.getScore());
			map.put("createTime", SIMPLE_DATE_FORMAT.format(new Date(Long.valueOf(c.getCreateTime()))));
			map.put("nickName", c.getUser().getNickName());
			map.put("iconUrl", c.getUser().getIconUrl());
			map.put("serviceTitle", c.getServiceTitle());
			sends.add(map.finish());
		}
		setResMsg(toSend.put("data", sends).finishByJson());
	}

}
