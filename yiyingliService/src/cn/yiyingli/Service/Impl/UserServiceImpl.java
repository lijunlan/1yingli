package cn.yiyingli.Service.Impl;

import java.util.*;

import cn.yiyingli.Dao.*;
import cn.yiyingli.Persistant.*;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.CheckUtil;
import cn.yiyingli.Util.CouponNumberUtil;
import cn.yiyingli.Util.LogUtil;
import cn.yiyingli.Util.NotifyUtil;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	private TeacherDao teacherDao;

	private VoucherDao voucherDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public VoucherDao getVoucherDao() {
		return voucherDao;
	}

	public void setVoucherDao(VoucherDao voucherDao) {
		this.voucherDao = voucherDao;
	}

	@Override
	public void save(User user) throws Exception {
		User u = getUserDao().query(user.getUsername(), false);
		if (u == null) {
			if (user.getDistributor() != null) {
				getUserDao().saveAndCount(user, user.getDistributor());
				if (user.getDistributor().getSendVoucher() && user.getDistributor().getVoucherCount() > 0) {
					Distributor distributor = user.getDistributor();
					float money = distributor.getVoucherMoney();
					int count = distributor.getVoucherCount();
					List<String> vouchers = new ArrayList<String>();
					for (int i = 1; i <= count; i++) {
						String number = CouponNumberUtil.getNewNumber(16);
						Voucher voucher = new Voucher();
						long time = Calendar.getInstance().getTimeInMillis();
						long endTime = time + 1000 * 60 * 60 * 24 * 30;
						voucher.setCreateTime(time + "");
						voucher.setOrigin("System,Distributor:" + distributor.getId() + "," + distributor.getName());
						voucher.setStartTime(time + "");
						voucher.setEndTime(endTime + "");
						voucher.setMoney(money);
						voucher.setNumber(number);
						voucher.setUsed(false);
						getVoucherDao().save(voucher);
						vouchers.add(number);
					}
					StringBuffer sb = new StringBuffer();
					sb.append("感谢您在一英里平台注册，以下是送您的优惠码(" + count + "个):");
					for (String v : vouchers) {
						sb.append(v + "   ");
					}
					sb.append(",优惠码即时生效，每次消费可以使用一个优惠码，每个可抵扣人民币" + money + "元，有效期为30天。");
					NotifyUtil.notifyUserNormal(user.getPhone(), user.getEmail(), "优惠码", sb.toString(), user);
				}
			} else {
				getUserDao().save(user);
			}
		} else {
			throw new Exception("phone,email or weixin,weibo has been registered");
		}
	}

	@Override
	public Long saveAndReturnId(User user) {
		return getUserDao().saveAndReturnId(user);
	}

	@Override
	public void remove(User user) {
		getUserDao().remove(user);
	}

	@Override
	public void remove(long id) {
		getUserDao().remove(id);
	}

	@Override
	public void update(User user) {
		getUserDao().update(user);
	}

	@Override
	public void updateUsername(User user) {
		getUserDao().updateUsername(user);
	}

	@Override
	public void updateWithTeacher(User user) {
		if (user.getTeacherState() == TEACHER_STATE_ON_SHORT) {
			Teacher teacher = getTeacherDao().queryByUserId(user.getId());
			// 防止TEACHER被强制下架
			if (teacher != null) {
				// teacher.setSex(user.getSex());
				teacher.setEmail(user.getEmail());
				teacher.setName(user.getName());
				teacher.setPhone(user.getPhone());
				teacher.setIconUrl(user.getIconUrl());
				getUserDao().merge(user);
				getTeacherDao().merge(teacher);
			}
		} else {
			getUserDao().update(user);
		}
	}

	@Override
	public void mergeUser(User user, User subUser) {
		if (subUser.getId().equals(user.getId()) || subUser.getFaUserId() != null) {
			return;
		}
		String changeUserSql = " set USER_ID = " + user.getId() + " where USER_ID = " + subUser.getId();
		String tableNames[] = {"comment", "notification", "orderlist", "orders",
				"reward", "userlikepassage", "userlikeservicepro", "userliketeacher", "voucher"};
		for (String tableName : tableNames) {
			getUserDao().updateWithRawSql("update " + tableName + changeUserSql);
		}
		if (subUser.getIconUrl() != null && user.getIconUrl() == null) {
			user.setIconUrl(subUser.getIconUrl());
		}
		if (subUser.getName() != null && user.getName() == null) {
			user.setName(subUser.getName());
		}
		if (subUser.getAddress() != null && user.getAddress() == null) {
			user.setAddress(subUser.getAddress());
		}
		user.setOrderNumber(user.getOrderNumber() + subUser.getOrderNumber());
		user.setReceiveCommentNumber(user.getReceiveCommentNumber() + subUser.getReceiveCommentNumber());
		user.setSendCommentNumber(user.getSendCommentNumber() + subUser.getSendCommentNumber());
		user.setLikeTeacherNumber(user.getLikeTeacherNumber() + subUser.getLikeTeacherNumber());
		user.setLikeServiceProNumber(user.getLikeServiceProNumber() + subUser.getLikeServiceProNumber());
		subUser.setFaUserId(user.getId());
		getUserDao().merge(subUser);
		getUserDao().merge(user);
	}

	@Override
	public void changePassword(long userId, String oldpwd, String newpwd) throws Exception {
		User user = getUserDao().query(userId, false);
		if (user.getPassword().equals(oldpwd)) {
			user.setPassword(newpwd);
		} else {
			throw new Exception("old password is not accurate");
		}
	}

	@Override
	public User query(long id, boolean lazy) {
		return getUserDao().query(id, lazy);
	}

	@Override
	public User queryByNo(String no, boolean lazy) {
		User user = getUserDao().queryByNo(no, lazy);
		if (!CheckUtil.checkEmail(no) && user == null) {
			String[] tmp = no.split("-");
			if (tmp.length > 1) {
				user = getUserDao().queryByNo(tmp[1], lazy);
			}
		}
		return getUserDao().queryByNo(no, lazy);
	}

	@Override
	public User queryWithTeacher(long id, boolean lazy) {
		return getUserDao().queryWithTeacher(id, lazy);
	}

	@Override
	public User query(String username, boolean lazy) {
		User user = getUserDao().queryWithTeacher(username, lazy);
		if (!CheckUtil.checkEmail(username) && user == null) {
			String[] tmp = username.split("-");
			if (tmp.length > 1) {
				user = getUserDao().queryWithTeacher(tmp[1], lazy);
			}
		}
		return user;
	}

	@Override
	public User queryWithWeibo(String weiboNo, boolean lazy) {
		return getUserDao().queryWithWeibo(weiboNo, lazy);
	}

	@Override
	public User queryWithWeixin(String weixinNo, boolean lazy) {
		return getUserDao().queryWithWeixin(weixinNo, lazy);
	}

	@Override
	public User queryWithWeixinPlatform(String weixinNo) {
		return getUserDao().queryWithWeixinPlatform(weixinNo);
	}

	@Override
	public User queryWithTeacher(String username, boolean lazy) {
		User user = getUserDao().queryWithTeacher(username, lazy);
		if (!CheckUtil.checkEmail(username) && user == null) {
			String[] tmp = username.split("-");
			if (tmp.length > 1) {
				user = getUserDao().queryWithTeacher(tmp[1], lazy);
			}
		}
		return user;
	}

	@Override
	public List<User> queryList(int page, int pageSize, boolean lazy) {
		return getUserDao().queryList(page, pageSize, lazy);
	}

	@Override
	public List<User> queryListByForbid(int page, int pageSize, boolean forbid, boolean lazy) {
		return getUserDao().queryListByForbid(page, pageSize, forbid, lazy);
	}

	@Override
	public List<User> queryListByValidate(int page, int pageSize, boolean validate, boolean lazy) {
		return getUserDao().queryListByValidate(page, pageSize, validate, lazy);
	}

	@Override
	public List<User> queryListByLevel(int page, int pageSize, short level, boolean lazy) {
		return getUserDao().queryListByLevel(page, pageSize, level, lazy);
	}

	@Override
	public List<User> queryListByTeacher(int page, int pageSize, short teacherState, boolean lazy) {
		return getUserDao().queryListByTeacher(page, pageSize, teacherState, lazy);
	}

	@Override
	public List<User> queryList(int page, boolean lazy) {
		return queryList(page, PAGE_SIZE_INT, lazy);
	}

	@Override
	public List<User> queryListByForbid(int page, boolean forbid, boolean lazy) {
		return queryListByForbid(page, PAGE_SIZE_INT, forbid, lazy);
	}

	@Override
	public List<User> queryListByValidate(int page, boolean validate, boolean lazy) {
		return queryListByValidate(page, PAGE_SIZE_INT, validate, lazy);
	}

	@Override
	public List<User> queryListByLevel(int page, short level, boolean lazy) {
		return queryListByLevel(page, PAGE_SIZE_INT, level, lazy);
	}

	@Override
	public List<User> queryListByTeacher(int page, short teacherState, boolean lazy) {
		return queryListByTeacher(page, PAGE_SIZE_INT, teacherState, lazy);
	}
}
