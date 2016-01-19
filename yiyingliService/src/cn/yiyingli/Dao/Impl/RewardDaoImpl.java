package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.RewardDao;
import cn.yiyingli.Persistant.Reward;

public class RewardDaoImpl extends HibernateDaoSupport implements RewardDao {

	@Override
	public void save(Reward reward) {
		getHibernateTemplate().save(reward);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update teacher set teacher.REWARDNUMBER=(select count(*) from reward where reward.TEACHER_ID='"
						+ reward.getTeacherId() + "') where teacher.TEACHER_ID=" + reward.getTeacherId());
		query.executeUpdate();
		if (reward.getPassageId() != null) {
			query = session.createSQLQuery(
					"update passage set passage.REWARDNUMBER=(select count(*) from reward where reward.PASSAGE_ID='"
							+ reward.getPassageId() + "') where reward.PASSAGE_ID=" + reward.getPassageId());
			query.executeUpdate();
		}
	}

	@Override
	public void remove(Reward reward) {
		getHibernateTemplate().delete(reward);
	}

	@Override
	public void update(Reward reward) {
		getHibernateTemplate().update(reward);
	}

	@Override
	public Reward query(long id) {
		String hql = "from Reward r where r.id=?";
		@SuppressWarnings("unchecked")
		List<Reward> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Reward query(String rewardNo) {
		String hql = "from Reward r where r.rewardNo=?";
		@SuppressWarnings("unchecked")
		List<Reward> list = getHibernateTemplate().find(hql, rewardNo);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Float queryMoneyByTeacher(long teacherId, Boolean finishPay, Boolean finishSalary) {
		String hql = "select sum(r.money) from Reward where r.teacherId=" + teacherId;
		if (finishPay != null) {
			hql = hql + " and r.finishPay=" + finishPay;
		}
		if (finishSalary != null) {
			hql = hql + "and r.finishSalary=" + finishSalary;
		}
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		float moneySum = (float) query.uniqueResult();
		return moneySum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reward> queryList(final int page, final int pageSize) {
		List<Reward> list = new ArrayList<Reward>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Reward>>() {
			@Override
			public List<Reward> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Reward r ORDER BY r.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Reward> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reward> queryListByTeacher(final long teacherId, final int page, final int pageSize) {
		List<Reward> list = new ArrayList<Reward>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Reward>>() {
			@Override
			public List<Reward> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Reward r where teacherId=" + teacherId + " ORDER BY r.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Reward> list = query.list();
				return list;
			}
		});
		return list;
	}

}
