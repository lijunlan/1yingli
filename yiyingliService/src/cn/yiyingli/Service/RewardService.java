package cn.yiyingli.Service;

import java.util.List;

import cn.yiyingli.Persistant.Reward;

public interface RewardService {
	
	public static final int PAGE_SIZE = 12;

	void save(Reward reward);

	void remove(Reward reward);

	void update(Reward reward);

	Reward query(long id);

	Reward query(String rewardNo);

	Long queryRewardNoByTeacher(long teacherId);

	Float queryMoneyByTeacherShouldPay(long teacherId);

	Float queryMoneyByTeacherUserPayed(long teacherId);

	Float queryMoneyByTeacherAll(long teacherId);

	List<Reward> queryList(int page, int pageSize);

	List<Reward> queryListByTeacher(long teacherId, int page, int pageSize);

}
