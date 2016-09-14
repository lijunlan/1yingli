package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.TipDao;
import cn.yiyingli.Persistant.Tip;
import cn.yiyingli.Service.TipService;

public class TipServiceImpl implements TipService {

	private TipDao tipDao;

	public TipDao getTipDao() {
		return tipDao;
	}

	public void setTipDao(TipDao tipDao) {
		this.tipDao = tipDao;
	}

	@Override
	public void save(Tip tip) {
		getTipDao().save(tip);
	}

	@Override
	public Long saveAndReturnId(Tip tip) {
		return getTipDao().saveAndReturnId(tip);
	}

	@Override
	public void remove(Tip tip) {
		getTipDao().remove(tip);
	}

	@Override
	public void remove(long id) {
		getTipDao().remove(id);
	}

	@Override
	public void update(Tip tip) {
		getTipDao().update(tip);
	}

	@Override
	public Tip query(long id) {
		return getTipDao().query(id);
	}

	@Override
	public List<Tip> queryList() {
		return getTipDao().queryList();
	}

	@Override
	public List<Tip> queryNormalList() {
		return getTipDao().queryNormalList();
	}

}
