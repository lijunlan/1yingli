package cn.yiyingli.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yiyingli.Util.ValidateCodeUtil;

public class ValidateCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest reqeust, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		ValidateCodeUtil vCode = new ValidateCodeUtil(100, 30, 4, 30);
		Cookie c1 = new Cookie("validate", vCode.getCode());
		c1.setDomain(".1yingli.cn");
		c1.setPath("/");
		response.addCookie(c1);
		vCode.write(response.getOutputStream());
	}
}