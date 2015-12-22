package cn.yiyingli.Dao.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.yiyingli.Dao.OrderListDao;
import cn.yiyingli.Persistant.OrderList;

public class OrderListDaoImpl extends HibernateDaoSupport implements OrderListDao {

	@Override
	public void save(OrderList orderList) {
		getHibernateTemplate().save(orderList);
	}

	@Override
	public Long saveAndReturnId(OrderList orderList) {
		getHibernateTemplate().save(orderList);
		return orderList.getId();
	}

	@Override
	public void remove(OrderList orderList) {
		getHibernateTemplate().delete(orderList);
	}

	@Override
	public void remove(long id) {
		getHibernateTemplate().bulkUpdate("delete from OrderList ol where ol.id=?", id);
	}

	@Override
	public void update(OrderList orderList) {
		getHibernateTemplate().update(orderList);
	}

	@Override
	public OrderList queryByOrderListNo(String orderListNo) {
		String hql = "from OrderList ol left join fetch ol.user left join fetch ol.teacher where o.orderListNo=?";
		@SuppressWarnings("unchecked")
		List<OrderList> list = getHibernateTemplate().find(hql, orderListNo);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public OrderList query(long id) {
		String hql = "from OrderList ol left join fetch ol.user left join fetch ol.teacher where o.id=?";
		@SuppressWarnings("unchecked")
		List<OrderList> list = getHibernateTemplate().find(hql, id);
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderList> queryListByTeacher(final long teacherId, final int page, final int pageSize) {
		List<OrderList> list = new ArrayList<OrderList>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<OrderList>>() {

			@Override
			public List<OrderList> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from OrderList ol left join fetch ol.user  where ol.teacher.id=" + teacherId
						+ " and ol.showToTeacher=" + true + " ORDER BY o.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<OrderList> list = query.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderList> queryListByUser(final long userId, final int page, final int pageSize) {
		List<OrderList> list = new ArrayList<OrderList>();
		list = getHibernateTemplate().executeFind(new HibernateCallback<List<OrderList>>() {

			@Override
			public List<OrderList> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from OrderList ol left join fetch ol.teacher  where ol.user.id=" + userId
						+ " ORDER BY o.createTime DESC";
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
				List<OrderList> list = query.list();
				return list;
			}
		});
		return list;
	}

}
