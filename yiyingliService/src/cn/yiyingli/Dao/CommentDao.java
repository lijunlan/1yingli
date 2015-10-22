package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;

public interface CommentDao {

	void save(Comment comment);

	void saveWithTeacherAndUser(Comment comment, Teacher teacher, User user,short kind);

	void saveWithUser(Comment comment, User user,short kind);

	Long saveAndReturnId(Comment comment);

	void remove(Comment comment);

	void remove(long id);

	void update(Comment comment);

	void updateFromSql(String sql);

	Comment query(int id, boolean lazy);

	long querySumNoByUserId(long userId);

	long querySumNoByTeacherId(long teacherId);

	List<Comment> queryDoubleByOrderIdAndTeacherId(long orderId, long teacherId, boolean lazy);

	List<Comment> queryListByUserId(final long userId, final int page, final int pageSize, short kind, boolean lazy);

	List<Comment> queryListByUserIdAndScore(final long userId, final short score, final int page, final int pageSize,
			short kind, final boolean lazy);

	List<Comment> queryListByTeacherId(final long teacherId, final int page, final int pageSize, short kind,
			final boolean lazy);

	List<Comment> queryListByTeacherIdAndScore(final long teacherId, final short score, final int page,
			final int pageSize, short kind, final boolean lazy);

}
