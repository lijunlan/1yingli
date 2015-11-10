package cn.yiyingli.TimeTasker;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yiyingli.Persistant.Task;
import cn.yiyingli.Service.TimeTaskService;
import cn.yiyingli.Util.Json;
import cn.yiyingli.Util.sendPost;

public class InfromEnd extends Thread {

	private Task t;

	public boolean stopFlag;

	LinkedBlockingQueue<Task> sendQueue;

	private TimeTaskService timeTaskService;

	String url = "http://service.1yingli.cn/yiyingliService/manage";

	String parm;

	String result;

	Map<String, String> tmp;

	int times;

	public void setData(LinkedBlockingQueue<Task> sendQueue,ApplicationContext ctx) {
		this.sendQueue = sendQueue;
		stopFlag = true;
		timeTaskService = (TimeTaskService) ctx.getBean("timeTaskService");
	}

	public void run() {

		while (stopFlag) {
			try {
				t = sendQueue.take();
				parm = "{'style':'function','method':'timer','kind':'" + t.getTaskKind() + "','action':'"
						+ t.getTaskAction() + "','data':'" + t.getData() + "'}";

				System.out.print("send->>");
				System.out.println(parm);

				result = sendPost.SendPost(url, parm);
				tmp = Json.getMap(result);

				if (tmp.get("state").equals("success")) {
					System.out.println("send success and receive->>" + tmp.get("msg"));
					timeTaskService.finishTask(t);
				} else {
					// 倍数
					System.out.println("send fail");
					times = (int) Math.pow((double) (t.getSendTimes() + 1), 3);
					t.setTaskEndTime(t.getTaskEndTime() + 60000 * times);
					t.setSendTimes(t.getSendTimes() + 1);
					timeTaskService.reSaveTask(t);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("send fail");
				times = (int) Math.pow((double) (t.getSendTimes() + 1), 3);
				t.setTaskEndTime(t.getTaskEndTime() + 60000 * times);
				t.setSendTimes(t.getSendTimes() + 1);
				timeTaskService.reSaveTask(t);
			}
		}

	}
}
