package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.LinkinInfo;

public interface LinkinInfoDao {

	void save(LinkinInfo linkinInfo);

	Long saveAndReturnId(LinkinInfo linkinInfo);

	void remove(LinkinInfo linkinInfo);

	void remove(long id);

	void update(LinkinInfo linkinInfo);

	void updateFromSql(String sql);

	LinkinInfo query(long id, boolean lazy);

	List<LinkinInfo> queryList(int page, int pageSize, boolean lazy);

	List<LinkinInfo> queryListByUserId(long userId, boolean lazy);

}
