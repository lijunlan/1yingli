package cn.yiyingli.Servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.internal.OSSConstants;
import com.aliyun.oss.model.ObjectMetadata;

import cn.yiyingli.AliyunUtil.AliyunConfiguration;
import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.Util.ConfigurationXmlUtil;

@SuppressWarnings("serial")
public class UpLoadImageServlet extends HttpServlet {

	private static String getImageKey() {
		return Calendar.getInstance().getTimeInMillis() + UUID.randomUUID().toString();
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		super.doOptions(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		DiskFileItemFactory factory = new DiskFileItemFactory(10 * 1024 * 1024, new File("/TEMP/SYSTEM"));
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(req);
			Map<String, String> map = new HashMap<String, String>();
			for (FileItem item : items) {
				if (!item.isFormField()) {
					String endName = item.getName().substring(item.getName().lastIndexOf(".") + 1);
					String name = new Date().getTime() + "." + endName;
					File saveFile = new File(
							ConfigurationXmlUtil.getInstance().getSettingData().get("cachePath") + "/Document/" + name);
					if (!saveFile.getParentFile().exists()) {
						saveFile.getParentFile().mkdirs();
					}
					OSSClient client = new OSSClient(OSSConstants.DEFAULT_OSS_ENDPOINT, AliyunConfiguration.ACCESS_ID,
							AliyunConfiguration.ACCESS_KEY);
					ObjectMetadata objectMeta = new ObjectMetadata();
					objectMeta.setContentLength(item.getSize());
					objectMeta.setContentType("image/*");
					String key = getImageKey() + "." + endName;
					client.putObject(AliyunConfiguration.BUCKET_NAME, key, item.getInputStream(), objectMeta);
					// item.write(saveFile);
					returnMsg(resp,
							new SuperMap().put("state", "success")
									.put("url", ConfigurationXmlUtil.getInstance().getSettingData().get("imagePath")
											+ "/" + key + "@!icon")
									.finishByJson());
				} else {
					map.put(item.getName(), item.getString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(500);
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
		try {
			OutputStream stream = resp.getOutputStream();
			System.out.println(msg);
			stream.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
