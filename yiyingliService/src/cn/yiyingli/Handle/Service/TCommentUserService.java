package cn.yiyingli.Handle.Service;

import java.util.Calendar;

import cn.yiyingli.Handle.TMsgService;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Service.NotificationService;
import cn.yiyingli.Service.OrderService;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.NotifyUtil;

public class TCommentUserService extends TMsgService {

	private OrderService orderService;

	private CommentService commentService;

	private NotificationService notificationService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
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
		return super.checkData() && getData().containsKey("orderId") && getData().containsKey("score")
				&& getData().containsKey("content");
	}

	@Override
	public void doit() {
		super.doit();
		Teacher teacher = getTeacher();
		try {
			String oid = (String) getData().get("orderId");
			Order order = getOrderService().queryByShowId(oid, false);
			if (order == null) {
				setResMsg(MsgUtil.getErrorMsgByCode("42001"));
				return;
			}
			if (order.getTeacher().getId().longValue() != teacher.getId().longValue()) {
				setResMsg(MsgUtil.getErrorMsgByCode("44001"));
				return;
			}
			String state = order.getState().split(",")[0];
			if (!OrderService.ORDER_STATE_WAIT_TCOMMENT.equals(state)) {
				setResMsg(MsgUtil.getErrorMsgByCode("44002"));
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
			setResMsg(MsgUtil.getErrorMsgByCode("41001"));
			return;
		}
	}

}
