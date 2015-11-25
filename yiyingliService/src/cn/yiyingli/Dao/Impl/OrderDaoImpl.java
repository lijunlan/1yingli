package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.OrderDao;
import cn.yiyingli.Persistant.Distributor;
import cn.yiyingli.Persistant.Order;
import cn.yiyingli.Persistant.Teacher;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.OrderService;

public class OrderDaoImpl extends HibernateDaoSupport implements OrderDao {

	@Override
	public void save(Order order) {
		getHibernateTemplate().save(order);
	}

	@Override
	public Long saveWithUserNumber(Order order, User user) {
		getHibernateTemplate().save(order);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session
				.createSQLQuery("update user set user.ORDERNUMBER=(select count(*) from orders where orders.USER_ID='"
						+ user.getId() + "') where user.USER_ID=" + user.getId());
		query.executeUpdate();
		return order.getId();
	}

	@Override
	public Long saveAndReturnId(Order order) {
		getHibernateTemplate().save(order);
		return order.getId();
	}

	@Override
	public void remove(Order order) {
		getHibernateTemplate().delete(order);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from Order o where o.id=?", id);
	}

	@Override
	public void update(Order order) {
		if (OrderService.ORDER_STATE_END_SUCCESS.equals(order.getState().split(",")[0])) {
			order.setEndTime(Calendar.getInstance().getTimeInMillis() + "");
		} else if (OrderService.ORDER_STATE_END_FAILED.equals(order.getState().split(",")[0])) {
			order.setEndTime(Calendar.getInstance().getTimeInMillis() + "");
		}
		getHibernateTemplate().update(order);
	}

	@Override
	public void updateWithTeacherNumber(Order order, Teacher teacher) {
		getHibernateTemplate().update(order);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update teacher set teacher.ORDERNUMBER=(select count(*) from orders where orders.TEACHER_ID='"
						+ teacher.getId()
						+ "' and orders.STATE not like '0100%' and orders.STATE not like '%0200,0100%') where teacher.TEACHER_ID="
						+ teacher.getId());
		query.executeUpdate();
	}

	@Override
	public void updateDistriOrderNumber(Order order, Distributor distributor) {
		getHibernateTemplate().update(order);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update distributor set distributor.ORDERNUMBER=(select count(*) from orders where orders.DISTRIBUTOR_ID='"
						+ distributor.getId() + "') where distributor.DISTRIBUTOR_ID=" + distributor.getId());
		query.executeUpdate();
	}

	@Override
	public void updateOrderWhenOrderFinish(Order order) {
		getHibernateTemplate().update(order);
		Session session = getSessionFactory().getCurrentSession();
		session.flush();
		Query query = session.createSQLQuery(
				"update teacher set teacher.FINISHORDERNUMBER=(select count(*) from orders where orders.TEACHER_ID="
						+ order.getTeacher().getId() + " and orders.STATE like '%1000%') where teacher.TEACHER_ID="
						+ order.getTeacher().getId());
		query.executeUpdate();
		session.flush();
		query = session.createSQLQuery(
				"update distributor set distributor.DEALNUMBER=(select count(*) from orders where orders.DISTRIBUTOR_ID=(select orders.DISTRIBUTOR_ID from orders where orders.ORDER_ID='"
						+ order.getId()
						+ "') and orders.STATE like '%1000%') where distributor.DISTRIBUTOR_ID=(select orders.DISTRIBUTOR_ID from orders where orders.ORDER_ID='"
						+ order.getId() + "')");
		query.executeUpdate();
	}

	@Override
	public void updateFromSql(String sql) {
		getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public Order query(long id, boolean lazy) {
		String hql = "from Order o left join fetch o.createUser left join fetch o.teacher  where o.id=?";
		if (lazy) {
			hql = "from Order o left join fetch o.createUser left join fetch o.useVouchers "
					+ "left join fetch o.teacher left join fetch o.tService where o.id=?";
		}
		@SuppressWarnings("unchecked")
		List<Order> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Order queryByOrderNo(String orderNo) {
		String hql = "from Order o left join fetch o.createUser left join fetch o.teacher  where o.orderNo=?";
		@SuppressWarnings("unchecked")
		List<Order> list = getHibernateTemplate().find(hql, orderNo);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public Order queryByShowId(String id, boolean lazy) {
		String hql = "from Order o left join fetch o.createUser left join fetch o.teacher  where o.orderNo=?";
		if (lazy) {
			hql = "from Order o left join fetch o.createUser left join fetch o.useVouchers "
					+ "left join fetch o.teacher left join fetch o.tService where o.orderNo=?";
		}
		@SuppressWarnings("unchecked")
		List<Order> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByState(final String state, final int page, final int pageSize, final boolean lazy) {
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {

			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Order o left join fetch o.createUser  left join fetch o.teacher where o.state like '"
						+ state + "%' ORDER BY o.createTime DESC";
				if (lazy) {
					hql = "from Order o left join fetch o.useVouchers left join fetch o.createUser "
							+ "left join fetch o.tService where o.state like '" + state
							+ "%' ORDER BY o.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByTeacherId(final long teacherId, final int page, final int pageSize,
			final boolean lazy) {
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {

			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Order o left join fetch o.createUser  where o.teacher.id=" + teacherId
						+ " and o.state not like '0100%' and o.state not like '%0200,0100%' ORDER BY o.createTime DESC";
				if (lazy) {
					hql = "from Order o left join fetch o.useVouchers left join fetch o.createUser "
							+ "left join fetch o.tService where o.teacher.id=" + teacherId
							+ " and o.state not like '0100%' and o.state not like '%0200,0100%' ORDER BY o.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByTeacherId(final long teacherId, final String state, final int page,
			final int pageSize, final boolean lazy) {
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {

			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Order o left join fetch o.createUser where o.teacher.id=" + teacherId
						+ "and o.state like '" + state
						+ "%' and o.state not like '0100%' and o.state not like '%0200,0100%' ORDER BY o.createTime DESC";
				if (lazy) {
					hql = "from Order o left join fetch o.useVouchers left join fetch o.createUser "
							+ "left join fetch o.tService where o.teacher.id=" + teacherId + "and o.state like '"
							+ state
							+ "%' and o.state not like '0100%' and o.state not like '%0200,0100%' ORDER BY o.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByTeacherId(long teacherId, String[] state, final int page, final int pageSize,
			boolean lazy) {
		if (state.length <= 0)
			return new ArrayList<Order>();
		String hql = "from Order o left join fetch o.createUser where o.teacher.id=" + teacherId + "and (o.state like '"
				+ state[0] + "%'";
		if (state.length > 1) {
			for (int i = 1; i < state.length; i++) {
				hql = hql + " or o.state like '" + state[i] + "%'";
			}
		}
		final String _hql = hql + ") ORDER BY o.createTime DESC";
		// System.out.println(_hql);
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {

			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(_hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByUserId(final long userId, final int page, final int pageSize, final boolean lazy) {
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {

			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Order o left join fetch o.teacher where o.createUser.id=" + userId
						+ " ORDER BY o.createTime DESC";
				if (lazy) {
					hql = "from Order o left join fetch o.useVouchers left join fetch o.teacher "
							+ "left join fetch o.tService where o.createUser.id=" + userId
							+ " ORDER BY o.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByUserId(long userId, String[] state, final int page, final int pageSize,
			boolean lazy) {
		if (state.length <= 0)
			return new ArrayList<Order>();
		String hql = "from Order o left join fetch o.teacher where o.createUser.id=" + userId + "and (o.state like '"
				+ state[0] + "%'";
		if (state.length > 1) {
			for (int i = 1; i < state.length; i++) {
				hql = hql + " or o.state like '" + state[i] + "%'";
			}
		}
		final String _hql = hql + ") ORDER BY o.createTime DESC";
		// System.out.println(_hql);
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {

			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(_hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByUserId(final long userId, final String state, final int page, final int pageSize,
			final boolean lazy) {
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {

			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Order o left join fetch o.teacher where o.createUser.id=" + userId
						+ "and o.state like '" + state + "%' ORDER BY o.createTime DESC";
				if (lazy) {
					hql = "from Order o left join fetch o.useVouchers left join fetch o.teacher "
							+ "left join fetch o.tService where o.createUser.id=" + userId + "and o.state like '"
							+ state + "%' ORDER BY o.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByTServiceId(final long tServiceId, final String state, final int page,
			final int pageSize, final boolean lazy) {
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {

			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Order o where o.tService.id=" + tServiceId + "and o.state like '" + state
						+ "%' ORDER BY o.createTime DESC";
				if (lazy) {
					hql = "from Order o left join fetch o.useVouchers left join fetch o.teacher "
							+ "left join fetch o.createUser where o.tService.id=" + tServiceId + "and o.state like '"
							+ state + "%' ORDER BY o.createTime DESC";
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@Override
	public long querySumNo() {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction ts = session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Order o").uniqueResult();
		ts.commit();
		return sum;
	}

	@Override
	public long querySumNoByUserId(long userId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction ts = session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Order o where o.createUser.id=" + userId)
				.uniqueResult();
		ts.commit();
		return sum;
	}

	@Override
	public long querySumNoByTeacherId(long teacherId) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction ts = session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Order o where o.teacher.id=" + teacherId)
				.uniqueResult();
		ts.commit();
		return sum;
	}

	@Override
	public long querySumNoByState(String state) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction ts = session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Order o where o.state like '" + state + "%'")
				.uniqueResult();
		ts.commit();
		return sum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByName(String name, final int page, final int pageSize) {
		final String hql = "from Order o left join fetch o.createUser u left join fetch o.teacher t where u.name='"
				+ name + "' or t.name='" + name + "' or o.customerName='" + name + "'";
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {
			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;

	}

	@Override
	public long querySumNoBySalaryState(short salaryState) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction ts = session.beginTransaction();
		long sum = (long) session.createQuery("select count(*) from Order o where o.salaryState = " + salaryState)
				.uniqueResult();
		ts.commit();
		return sum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListBySalaryState(final int page, final int pageSize, short salaryState) {
		final String hql = "from Order o left join fetch o.createUser u left join fetch o.teacher t where o.salaryState="
				+ salaryState + " ORDER BY o.createTime DESC";
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {
			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListBySalaryState(short salaryState, final int page, final int pageSize, String rank) {
		final String hql = "from Order o left join fetch o.createUser u left join fetch o.teacher t left join fetch o.distributor od where o.salaryState="
				+ salaryState + " ORDER BY od.name " + ("DESC".equals(rank) ? "DESC" : "ASC");
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {
			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryListByState(final String state, final int page, final int pageSize, final boolean lazy,
			final String rank) {
		List<Order> list = new ArrayList<Order>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<Order>>() {
			@Override
			public List<Order> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Order o left join fetch o.createUser left join fetch o.teacher left join fetch o.distributor od where o.state like '"
						+ state + "%' ORDER BY od.name " + ("DESC".equals(rank) ? "DESC" : "ASC");
				if (lazy) {
					hql = "from Order o left join fetch o.useVouchers left join fetch o.createUser "
							+ "left join fetch o.tService where o.state like '" + state
							+ "%' ORDER BY o.createTime DESC" + ("DESC".equals(rank) ? "DESC" : "ASC");
				}
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<Order> list = query.list();
				return list;
			}
		});
		return list;
	}

}
