package cn.yiyingli.Dao;

import cn.yiyingli.Persistant.Teacher;

import java.util.List;

public interface UserLikeTeacherDao {
	List<Teacher> queryTeacherListByLikedTeacherId(long likedTeacherId,int page,int pageSize);

	long querySumNoByLikedTeacherId(long likeTeacherId);
}
