package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class TCommentUserService extends MsgService {

	private OrderService orderService;

	private TeacherService teacherService;

	private UserMarkService userMarkService;

	private CommentService commentService;

	private NotificationService notificationService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
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

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("orderId") && getData().containsKey("teacherId") && getData().containsKey("score")
				&& getData().containsKey("content") && getData().containsKey("uid");
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
			Order order = getOrderService().queryByShowId(oid, false);
			if (order == null) {
				setResMsg(MsgUtil.getErrorMsg("order is not existed"));
				return;
			}
			if (order.getTeacher().getId().longValue() != teacher.getId().longValue()) {
				setResMsg(MsgUtil.getErrorMsg("this order is not belong to you"));
				return;
			}
			String state = order.getState().split(",")[0];
			if (!OrderService.ORDER_STATE_WAIT_TCOMMENT.equals(state)) {
				setResMsg(MsgUtil.getErrorMsg("order state is not accurate"));
				return;
			}
			order.setState(OrderService.ORDER_STATE_END_SUCCESS + "," + order.getState());
			Comment comment = new Comment();
			comment.setContent((String) getData().get("content"));
			comment.setKind(CommentService.COMMENT_KIND_FROMTEACHER_SHORT);
			comment.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
			comment.setOwnOrder(order);
			comment.setScore(Short.valueOf((String) getData().get("score")));
			comment.setServiceTitle(order.getServiceTitle());
			comment.setTeacher(teacher);
			comment.setUser(order.getCreateUser());
			getCommentService().saveWithOrder(comment, order);

			NotifyUtil.notifyUserOrder(order.getCustomerPhone(), order.getCustomerEmail(),
					"尊敬的学员，(订单号:" + order.getOrderNo() + ")，导师(" + order.getCustomerName() + ")已经对您进行了评价(评价分数:"
							+ comment.getScore() + "分。评价内容" + comment.getContent() + ")",
					order.getCreateUser(), getNotificationService());

			setResMsg(MsgUtil.getSuccessMsg("comment successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsg("input data is wrong"));
			return;
		}
	}

}
