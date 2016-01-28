package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.CommentDao;
import cn.yiyingli.Dao.OrderDao;
import cn.yiyingli.Dao.TeacherDao;
import cn.yiyingli.Dao.UserDao;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Service.CommentService;

public class CommentServiceImpl implements CommentService {

	private CommentDao commentDao;

	private OrderDao orderDao;

	private TeacherDao teacherDao;

	private UserDao userDao;

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public TeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void save(Comment comment) {
		getCommentDao().save(comment);
	}

	@Override
	public void saveWithOrder(Comment comment, Order order) {
		getOrderDao().update(order);
		if (comment.getKind() == COMMENT_KIND_FROMUSER_SHORT) {
			getCommentDao().saveWithTeacherAndUser(comment, comment.getTeacher(), comment.getUser(),
					COMMENT_KIND_FROMUSER_SHORT);
		} else {
			getCommentDao().saveWithUser(comment, comment.getUser(), COMMENT_KIND_FROMTEACHER_SHORT);
		}
	}

	@Override
	public void saveWithOrderAndTeacher(Comment comment, Order order, Teacher teacher) {
		getTeacherDao().updateAddMile(teacher.getId(), 2L);
		getOrderDao().update(order);
		if (comment.getKind() == COMMENT_KIND_FROMUSER_SHORT) {
			getCommentDao().saveWithTeacherAndUser(comment, comment.getTeacher(), comment.getUser(),
					COMMENT_KIND_FROMUSER_SHORT);
		} else {
			getCommentDao().saveWithUser(comment, comment.getUser(), COMMENT_KIND_FROMTEACHER_SHORT);
		}
	}

	@Override
	public Long saveAndReturnId(Comment comment) {
		return getCommentDao().saveAndReturnId(comment);
	}

	@Override
	public void remove(Comment comment) {
		getCommentDao().remove(comment);
	}

	@Override
	public void remove(long id) {
		getCommentDao().remove(id);
	}

	@Override
	public void update(Comment comment) {
		getCommentDao().update(comment);
	}

	@Override
	public Comment query(int id, boolean lazy) {
		return getCommentDao().query(id, lazy);
	}

	@Override
	public List<Comment> queryListByUserId(long userId, int page, int pageSize, short kind, boolean lazy) {
		return getCommentDao().queryListByUserId(userId, page, pageSize, kind, lazy);
	}

	@Override
	public List<Comment> queryListByUserIdAndScore(long userId, short score, int page, int pageSize, short kind,
			boolean lazy) {
		return getCommentDao().queryListByUserIdAndScore(userId, score, page, pageSize, kind, lazy);
	}

	@Override
	public List<Comment> queryListByTeacherId(long teacherId, int page, int pageSize, short kind, boolean lazy) {
		return getCommentDao().queryListByTeacherId(teacherId, page, pageSize, kind, lazy);
	}

	@Override
	public List<Comment> queryListByTeacherIdAndScore(long teacherId, short score, int page, int pageSize, short kind,
			boolean lazy) {
		return getCommentDao().queryListByTeacherIdAndScore(teacherId, score, page, pageSize, kind, lazy);
	}

	@Override
	public List<Comment> queryListByUserId(long userId, int page, short kind, boolean lazy) {
		return queryListByUserId(userId, page, PAGE_SIZE_INT, kind, lazy);
	}

	@Override
	public List<Comment> queryListByUserIdAndScore(long userId, short score, int page, short kind, boolean lazy) {
		return queryListByUserIdAndScore(userId, score, page, PAGE_SIZE_INT, kind, lazy);
	}

	@Override
	public List<Comment> queryListByTeacherId(long teacherId, int page, short kind, boolean lazy) {
		return queryListByTeacherId(teacherId, page, PAGE_SIZE_INT, kind, lazy);
	}

	@Override
	public List<Comment> queryListByTeacherIdAndScore(long teacherId, short score, int page, short kind, boolean lazy) {
		return queryListByTeacherIdAndScore(teacherId, score, page, PAGE_SIZE_INT, kind, lazy);
	}

	@Override
	public long querySumNoByUserId(long userId) {
		return getCommentDao().querySumNoByUserId(userId);
	}

	@Override
	public long querySumNoByTeacherId(long teacherId) {
		return getCommentDao().querySumNoByTeacherId(teacherId);
	}

	@Override
	public List<Comment> queryDoubleByOrderIdAndTeacherId(long orderId, long teacherId, boolean lazy) {
		return queryDoubleByOrderIdAndTeacherId(orderId, teacherId, lazy);
	}

}
