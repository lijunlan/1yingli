package cn.yiyingli.Dao;

import java.util.List;

import cn.yiyingli.Persistant.Reward;

public interface RewardDao {

	void save(Reward reward);

	void remove(Reward reward);

	void update(Reward reward);

	Reward query(long id);

	Reward query(String rewardNo);

	Float queryMoneyByTeacher(long teacherId, Boolean finishPay, Boolean finishSalary);

	List<Reward> queryList(int page, int pageSize);

	List<Reward> queryListByTeacher(long teacherId, int page, int pageSize);

}
