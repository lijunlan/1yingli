package cn.yiyingli.Handle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import cn.yiyingli.Persistant.UserMark;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.internal.OSSConstants;
import com.aliyun.oss.model.ObjectMetadata;

import cn.yiyingli.AliyunUtil.AliyunConfiguration;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.DownloadUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;
import cn.yiyingli.Util.TimeTaskUtil;

public abstract class ULoginMsgService extends MsgService {

	private UserMarkService userMarkService;

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserMarkService getUserMarkService() {
		return userMarkService;
	}

	public void setUserMarkService(UserMarkService userMarkService) {
		this.userMarkService = userMarkService;
	}

	protected void returnUser(User user, boolean register, boolean isLogin) {
		String _UUID = UUID.randomUUID().toString();
		UserMark userMark = getUserMarkService().queryUUID(user.getId());
		if (!isLogin && userMark != null && userMark.getUuid() != null) {
			_UUID = userMark.getUuid();
		} else {
			getUserMarkService().save(String.valueOf(user.getId()), _UUID);
			user.setLastLoginTime("" + Calendar.getInstance().getTimeInMillis());
			getUserService().update(user);
			TimeTaskUtil.sendTimeTask("remove", "userMark",
					(Calendar.getInstance().getTimeInMillis() + 1000 * 60 * 60 * 24 * 7) + "", _UUID);
		}
		SuperMap toSend = MsgUtil.getSuccessMap();
		toSend.put("uid", _UUID);
		toSend.put("nickName", user.getNickName());
		toSend.put("iconUrl", user.getIconUrl());
		toSend.put("username", user.getUsername());
		toSend.put("register", register);
		if (user.getTeacherState() == UserService.TEACHER_STATE_ON_SHORT) {
			toSend.put("teacherId", user.getTeacher().getId());
		}
		setResMsg(toSend.finishByJson());
	}

	protected String updateIcon(String originUrl, String tmpName) {
		String key = "";
		try {
			DownloadUtil.download(originUrl, tmpName,
					ConfigurationXmlUtil.getInstance().getSettingData().get("cachePath") + "/SYSTEM/");
			File file = new File(
					ConfigurationXmlUtil.getInstance().getSettingData().get("cachePath") + "/SYSTEM/" + tmpName);
			FileInputStream fis = new FileInputStream(file);
			OSSClient client = new OSSClient(OSSConstants.DEFAULT_OSS_ENDPOINT, AliyunConfiguration.ACCESS_ID,
					AliyunConfiguration.ACCESS_KEY);
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(file.length());
			objectMeta.setContentType("image/*");
			key = "icon/" + getImageKey() + ".jpg";
			client.putObject(AliyunConfiguration.BUCKET_NAME, key, fis, objectMeta);
			file.delete();
		} catch (IOException e) {
			e.printStackTrace();
			return originUrl;
		}
		return ConfigurationXmlUtil.getInstance().getSettingData().get("imagePath") + "/" + key;
	}

	private static String getImageKey() {
		return MD5Util.MD5(Calendar.getInstance().getTimeInMillis() + "" + (new Random().nextInt(99999) + 100000));
	}
}
