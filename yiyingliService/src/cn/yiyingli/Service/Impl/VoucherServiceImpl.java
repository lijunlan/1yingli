package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.VoucherDao;
import cn.yiyingli.Persistant.Voucher;
import cn.yiyingli.Service.VoucherService;

public class VoucherServiceImpl implements VoucherService {

	private VoucherDao voucherDao;

	public VoucherDao getVoucherDao() {
		return voucherDao;
	}

	public void setVoucherDao(VoucherDao voucherDao) {
		this.voucherDao = voucherDao;
	}

	@Override
	public void save(Voucher voucher) {
		getVoucherDao().save(voucher);
	}

	@Override
	public Long saveAndReturnId(Voucher voucher) {
		return getVoucherDao().saveAndReturnId(voucher);
	}

	@Override
	public void remove(Voucher voucher) {
		getVoucherDao().remove(voucher);
	}

	@Override
	public void remove(long id) {
		getVoucherDao().remove(id);
	}

	@Override
	public void update(Voucher voucher) {
		getVoucherDao().update(voucher);
	}

	@Override
	public Voucher query(long id, boolean lazy) {
		return getVoucherDao().query(id, lazy);
	}

	@Override
	public Voucher query(String vno, boolean lazy) {
		return getVoucherDao().query(vno, lazy);
	}

	@Override
	public List<Voucher> queryList(int page, int pageSize, boolean lazy) {
		return getVoucherDao().queryList(page, pageSize, lazy);
	}

	@Override
	public List<Voucher> queryListByUserId(long userId, int page, int pageSize, boolean lazy) {
		return getVoucherDao().queryListByUserId(userId, page, pageSize, lazy);
	}

	@Override
	public List<Voucher> queryList(int page, boolean lazy) {
		return queryList(page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<Voucher> queryListByUserId(long userId, int page, boolean lazy) {
		return queryListByUserId(userId, page, PAGE_SIZE_INT, lazy);
	}

}
