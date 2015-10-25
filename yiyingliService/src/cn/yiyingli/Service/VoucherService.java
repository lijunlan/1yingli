package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Voucher;

public interface VoucherService {

	public static final int PAGE_SIZE_INT = 12;

	void save(Voucher voucher);

	Long saveAndReturnId(Voucher voucher);

	void remove(Voucher voucher);

	void remove(long id);

	void update(Voucher voucher);

	Voucher query(long id, boolean lazy);

	Voucher query(String vno, boolean lazy);

	List<Voucher> queryList(int page, boolean lazy);

	List<Voucher> queryList(int page, int pageSize, boolean lazy);

	List<Voucher> queryListByUserId(long userId, int page, boolean lazy);

	List<Voucher> queryListByUserId(long userId, int page, int pageSize, boolean lazy);

}
