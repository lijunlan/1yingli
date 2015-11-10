package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.LinkinInfo;

public interface LinkinInfoService {

	public static final int PAGE_SIZE_INT = 10;

	void save(LinkinInfo linkinInfo);

	Long saveAndReturnId(LinkinInfo linkinInfo);

	void remove(LinkinInfo linkinInfo);

	void remove(long id);

	void update(LinkinInfo linkinInfo);

	LinkinInfo query(long id, boolean lazy);

	List<LinkinInfo> queryList(int page, int pageSize, boolean lazy);

	List<LinkinInfo> queryList(int page, boolean lazy);

	List<LinkinInfo> queryListByUserId(long userId, boolean lazy);
}
