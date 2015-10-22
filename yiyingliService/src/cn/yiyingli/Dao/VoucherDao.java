package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Voucher;

public interface VoucherDao {

	void save(Voucher voucher);

	Long saveAndReturnId(Voucher voucher);

	void remove(Voucher voucher);

	void remove(long id);

	void update(Voucher voucher);

	void updateFromSql(String sql);

	Voucher query(long id, boolean lazy);

	Voucher query(String vno, boolean lazy);

	List<Voucher> queryList(int page, int pageSize, boolean lazy);

	List<Voucher> queryListByUserId(long userId, int page, int pageSize, boolean lazy);

}
