package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;

public class GetOrderCommentService extends MsgService {

	private CommentService commentService;

	private TeacherService teacherService;

	private UserMarkService userMarkService;

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("teacherId") && getData().containsKey("orderId") && getData().containsKey("uid");
	}

	@Override
	public void doit() {
		String uid = (String) getData().get("uid");
		User user = getUserMarkService().queryUser(uid);
		if (user == null) {
			setResMsg(MsgUtil.getErrorMsg("uid is not existed"));
			return;
		}
		Teacher teacher = getTeacherService().queryByUserId(user.getId(), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsg("you are not a teacher"));
			return;
		}
		String teacherId = (String) getData().get("teacherId");
		if (!teacherId.equals(teacher.getId() + "")) {
			setResMsg(MsgUtil.getErrorMsg("uid don't match teacherId"));
			return;
		}
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
			setResMsg(MsgUtil.getErrorMsg("input data is wrong"));
			return;
		}
	}

}
