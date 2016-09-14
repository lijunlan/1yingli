package cn.yiyingli.Servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.internal.OSSConstants;
import com.aliyun.oss.model.ObjectMetadata;

import cn.yiyingli.AliyunUtil.AliyunConfiguration;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Persistant.User;
import cn.yiyingli.Service.UserMarkService;
import cn.yiyingli.Service.UserService;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.MD5Util;
import cn.yiyingli.Util.MsgUtil;

@SuppressWarnings("serial")
public class ChangeIconServlet extends HttpServlet {

	private ApplicationContext applicationContext;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()));
	}

	private static String getImageKey() {
		return MD5Util.MD5(Calendar.getInstance().getTimeInMillis() + "" + (new Random().nextInt(99999) + 100000));
	}
	
	@Override 
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		super.doOptions(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory(10 * 1024 * 1024, new File(ConfigurationXmlUtil.getInstance().getSettingData().get("cachePath") +"/SYSTEM"));
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(req);
			Map<String, String> map = new HashMap<String, String>();
			for (FileItem item : items) {
				if (item.isFormField()) {
					map.put(item.getFieldName(), item.getString());
				}
			}
			if (!map.containsKey("uid")) {
				returnMsg(resp, MsgUtil.getErrorMap().put("msg", "there is no uid").finishByJson());
				return;
			}
			String uid = map.get("uid");
			UserMarkService userMarkService = (UserMarkService) getApplicationContext().getBean("userMarkService");
			UserService userService = (UserService) getApplicationContext().getBean("userService");
			User user = userMarkService.queryUser(uid);
			if (user == null) {
				returnMsg(resp, MsgUtil.getErrorMap().put("msg", "uid is not existed").finishByJson());
				return;
			}
			for (FileItem item : items) {
				if (!item.isFormField()) {
					String endName = item.getName().substring(item.getName().lastIndexOf(".") + 1);
					OSSClient client = new OSSClient(OSSConstants.DEFAULT_OSS_ENDPOINT, AliyunConfiguration.ACCESS_ID,
							AliyunConfiguration.ACCESS_KEY);
					ObjectMetadata objectMeta = new ObjectMetadata();
					objectMeta.setContentLength(item.getSize());
					objectMeta.setContentType("image/*");
					String key = "icon/" + getImageKey() + "." + endName;
					client.putObject(AliyunConfiguration.BUCKET_NAME, key, item.getInputStream(), objectMeta);
					String url = ConfigurationXmlUtil.getInstance().getSettingData().get("imagePath")
							+ "/" + key + "@!icon";
					user.setIconUrl(url);
					userService.updateWithTeacher(user);
					returnMsg(resp, new SuperMap().put("state", "success").put("iconUrl", url).finishByJson());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(404);
		}
	}

	/**
	 * return message through httpResponse
	 * 
	 * @param msg
	 */
	private void returnMsg(HttpServletResponse resp, String msg) {
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		try {
			OutputStream stream = resp.getOutputStream();
			System.out.println(msg);
			stream.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
