package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.UMsgService;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Service.TeacherService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class CommentTeacherService extends UMsgService {

	private OrderService orderService;

	private TeacherService teacherService;

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
		return super.checkData() && getData().containsKey("orderId") && getData().containsKey("teacherId")
				&& getData().containsKey("score") && getData().containsKey("content");
	}

	@Override
	public void doit() {
		User user = getUser();
		String teacherId = (String) getData().get("teacherId");
		Teacher teacher = getTeacherService().query(Long.valueOf(teacherId), false);
		if (teacher == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22001"));
			return;
		}

		try {
			String oid = (String) getData().get("orderId");
			Order order = getOrderService().queryByShowId(oid, false);
			if (order == null) {
				setResMsg(MsgUtil.getErrorMsgByCode("42001"));
				return;
			}
			if (order.getCreateUser().getId().longValue() != user.getId().longValue()) {
				setResMsg(MsgUtil.getErrorMsgByCode("44001"));
				return;
			}
			String state = order.getState().split(",")[0];
			if (!OrderService.ORDER_STATE_WAIT_COMMENT.equals(state)) {
				setResMsg(MsgUtil.getErrorMsgByCode("44002"));
				return;
			}
			order.setState(OrderService.ORDER_STATE_WAIT_TCOMMENT + "," + order.getState());
			Comment comment = new Comment();
			comment.setContent((String) getData().get("content"));
			comment.setKind(CommentService.COMMENT_KIND_FROMUSER_SHORT);
			comment.setCreateTime(Calendar.getInstance().getTimeInMillis() + "");
			comment.setOwnOrder(order);
			comment.setScore(Short.valueOf((String) getData().get("score")));
			comment.setServiceTitle(order.getServiceTitle());
			comment.setTeacher(teacher);
			comment.setUser(user);

			// 加积分(英里数)
			teacher.setMile(teacher.getMile() + 2L);
			
			getCommentService().saveWithOrderAndTeacher(comment, order, teacher);

			NotifyUtil.notifyTeacher(order.getTeacher().getPhone(), order.getTeacher().getEmail(),
					"尊敬的导师，您好,学员(" + order.getCustomerName() + ")已经对本次咨询(订单号:" + order.getOrderNo() + "),进行了评价,评价分数:"
							+ comment.getScore() + "分。评价内容" + comment.getContent() + ".",
					order.getTeacher(), getNotificationService());
			NotifyUtil.notifyBD("订单号：" + order.getOrderNo() + ",学员：" + order.getCustomerName() + ",导师："
					+ order.getTeacher().getName() + ",学员已经对咨询进行了评价(评价分数:" + comment.getScore() + "分。评价内容"
					+ comment.getContent() + ")");
			setResMsg(MsgUtil.getSuccessMsg("comment successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			setResMsg(MsgUtil.getErrorMsgByCode("41001"));
			return;
		}
	}

}
