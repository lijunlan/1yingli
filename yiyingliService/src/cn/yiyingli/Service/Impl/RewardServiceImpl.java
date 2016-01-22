package cn.yiyingli.Service.Impl;

import java.util.List;

import cn.yiyingli.Dao.RewardDao;
import cn.yiyingli.Persistant.Reward;
import cn.yiyingli.Service.RewardService;

public class RewardServiceImpl implements RewardService {

	private RewardDao rewardDao;

	public RewardDao getRewardDao() {
		return rewardDao;
	}

	public void setRewardDao(RewardDao rewardDao) {
		this.rewardDao = rewardDao;
	}

	@Override
	public void save(Reward reward) {
		getRewardDao().save(reward);
	}

	@Override
	public void remove(Reward reward) {
		getRewardDao().remove(reward);
	}

	@Override
	public void update(Reward reward) {
		getRewardDao().update(reward);
	}

	@Override
	public Reward query(long id) {
		return getRewardDao().query(id);
	}

	@Override
	public Reward query(String rewardNo) {
		return getRewardDao().query(rewardNo);
	}

	@Override
	public Long queryRewardNoByTeacher(long teacherId) {
		return getRewardDao().queryRewardNoByTeacher(teacherId);
	}

	@Override
	public Float queryMoneyByTeacherShouldPay(long teacherId) {
		return getRewardDao().queryMoneyByTeacher(teacherId, true, false);
	}

	@Override
	public Float queryMoneyByTeacherUserPayed(long teacherId) {
		return getRewardDao().queryMoneyByTeacher(teacherId, true, null);
	}

	@Override
	public Float queryMoneyByTeacherAll(long teacherId) {
		return getRewardDao().queryMoneyByTeacher(teacherId, null, null);
	}

	@Override
	public List<Reward> queryList(int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reward> queryListByTeacher(long teacherId, int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
