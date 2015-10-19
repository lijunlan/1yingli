package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Order;

public interface CommentService {

	public static final short COMMENT_KIND_FROMUSER_SHORT = 1;

	public static final short COMMENT_KIND_FROMTEACHER_SHORT = 2;

	public static final int PAGE_SIZE_INT = 12;

	void save(Comment comment);

	void saveWithOrder(Comment comment, Order order);

	Long saveAndReturnId(Comment comment);

	void remove(Comment comment);

	void remove(long id);

	void update(Comment comment);

	Comment query(int id, boolean lazy);

	long querySumNoByUserId(long userId);

	long querySumNoByTeacherId(long teacherId);

	List<Comment> queryDoubleByOrderIdAndTeacherId(long orderId, long teacherId, boolean lazy);

	List<Comment> queryListByUserId(final long userId, final int page, short kind, boolean lazy);

	List<Comment> queryListByUserId(final long userId, final int page, final int pageSize, short kind, boolean lazy);

	List<Comment> queryListByUserIdAndScore(final long userId, final short score, final int page, short kind,
			final boolean lazy);

	List<Comment> queryListByUserIdAndScore(final long userId, final short score, final int page, final int pageSize,
			short kind, final boolean lazy);

	List<Comment> queryListByTeacherId(final long teacherId, final int page, short kind, final boolean lazy);

	List<Comment> queryListByTeacherId(final long teacherId, final int page, final int pageSize, short kind,
			final boolean lazy);

	List<Comment> queryListByTeacherIdAndScore(final long teacherId, final short score, final int page, short kind,
			final boolean lazy);

	List<Comment> queryListByTeacherIdAndScore(final long teacherId, final short score, final int page,
			final int pageSize, short kind, final boolean lazy);
}
