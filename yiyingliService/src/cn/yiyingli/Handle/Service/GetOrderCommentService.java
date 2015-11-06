package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderCommentService extends TMsgService {

	private CommentService commentService;

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@Override
	protected boolean checkData() {
		return super.checkData() && getData().containsKey("orderId");
	}

	@Override
	public void doit() {
		Teacher teacher = getTeacher();
		try {
			String oid = (String) getData().get("orderId");
			List<Comment> commentTU = getCommentService().queryDoubleByOrderIdAndTeacherId(Long.valueOf(oid),
					Long.valueOf(teacher.getId()), false);
			SuperMap map = MsgUtil.getSuccessMap();
			Comment comment1 = commentTU.get(0);
			Comment comment2 = commentTU.get(1);
			if (comment1.getKind() == CommentService.COMMENT_KIND_FROMTEACHER_SHORT) {
				map.put("teacher_score", comment1.getScore());
				map.put("teacher_content", comment1.getContent());
				map.put("teacher_createTime", comment1.getCreateTime());
			} else {
				map.put("user_score", comment1.getScore());
				map.put("user_content", comment1.getContent());
				map.put("user_createTime", comment1.getCreateTime());
			}
			if (comment2.getKind() == CommentService.COMMENT_KIND_FROMTEACHER_SHORT) {
				map.put("teacher_score", comment2.getScore());
				map.put("teacher_content", comment2.getContent());
				map.put("teacher_createTime", comment2.getCreateTime());
			} else {
				map.put("user_score", comment2.getScore());
				map.put("user_content", comment2.getContent());
				map.put("user_createTime", comment2.getCreateTime());
			}
			setResMsg(map.finishByJson());
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("41001"));
			return;
		}
	}

}
