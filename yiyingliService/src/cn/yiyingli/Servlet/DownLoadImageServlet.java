package cn.yiyingli.Servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;

import cn.yiyingli.AliyunUtil.GetOSSImageUtil;
import cn.yiyingli.Util.ConfigurationXmlUtil;
import cn.yiyingli.Util.MsgUtil;

@SuppressWarnings("serial")
public class DownLoadImageServlet extends HttpServlet {
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fn = req.getParameter("fn");
		fn = new String(fn.getBytes("ISO-8859-1"), "utf-8");
		try {
//			OSSClient client = new OSSClient(OSSConstants.DEFAULT_OSS_ENDPOINT, AliyunConfiguration.ACCESS_ID,
//					AliyunConfiguration.ACCESS_KEY);
			File temp = new File(ConfigurationXmlUtil.getInstance().getSettingData().get("cachePath") + fn);
			if (!temp.exists()) {
				if(!getImageFromOSS(resp, fn, temp)){
					returnMsg(resp, MsgUtil.getErrorMap().put("msg", "fn is not exsited").finishByJson());
					return;
				}
//				InputStream fis = new BufferedInputStream(new FileInputStream(temp));
//				byte[] buffer = new byte[fis.available()];
//				fis.read(buffer);
//				fis.close();
				// toClient.write(buffer);
				// toClient.flush();
				// toClient.close();
			}
				InputStream fis = new BufferedInputStream(new FileInputStream(temp));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				// 清空response
				resp.reset();
				// 设置response的Header
				resp.setHeader("Access-Control-Allow-Origin", "*");
				resp.addHeader("Content-Disposition", "attachment;filename=" + temp.getName());
				resp.addHeader("Content-Length", "" + temp.length());
				OutputStream toClient = new BufferedOutputStream(resp.getOutputStream());
				resp.setContentType("image/*");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
			returnMsg(resp, MsgUtil.getErrorMap().put("msg", ex.getMessage()).finishByJson());
		}

	}

	private boolean getImageFromOSS(HttpServletResponse resp, String fn, File temp)
			throws IOException, FileNotFoundException {
		if (!temp.getParentFile().exists()) {
			temp.getParentFile().mkdirs();
		}
		// ObjectMetadata metadata = client.getObject(new
		// GetObjectRequest(AliyunConfiguration.BUCKET_NAME, fn+""),
		// temp);

		HttpEntity entity = GetOSSImageUtil.getImage(fn);
//		// 清空response
//		resp.reset();
//		// 设置response的Header
//		resp.setHeader("Access-Control-Allow-Origin", "*");
//		resp.addHeader("Content-Disposition","attachment;filename=" + temp.getName());
//		resp.addHeader("Content-Length", entity.getContentLength() + "");
//		resp.setContentType(entity.getContentType().toString().split(":")[1]);
//		// OutputStream toClient = new
//		// BufferedOutputStream(resp.getOutputStream());
		if(entity==null)return false;
		byte[] buffer = org.apache.commons.io.IOUtils.toByteArray(entity.getContent());
		FileOutputStream fos = new FileOutputStream(temp);
		fos.write(buffer);
		fos.close();
		return true;
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
