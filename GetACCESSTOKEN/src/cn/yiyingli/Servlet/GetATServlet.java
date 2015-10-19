package cn.yiyingli.Servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yiyingli.Data.GlobalData;
import cn.yiyingli.Util.MyLoop;

@SuppressWarnings("serial")
public class GetATServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("get!");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		if (GlobalData.ACCESS_TOKEN.equals("") || GlobalData.JSAPI_TICKET.equals("")) {
			Thread t = new Thread(new MyLoop());
			t.start();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String msg = "{'ticket':'" + GlobalData.JSAPI_TICKET + "'}";
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			OutputStream stream = resp.getOutputStream();
			System.out.println("send---->>>" + msg);
			stream.write(msg.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
