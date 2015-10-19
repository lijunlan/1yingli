package cn.yiyingli.TimeTasker;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yiyingli.Persistant.Task;
import cn.yiyingli.Service.TimeTaskService;

public class CheckTask extends Thread {

	private TimeTaskService timeTaskService;

	public boolean stopFlag;

	InfromEnd ife;

	long time;

	List<Task> list;

	ApplicationContext ctx;

	public void setdata(ApplicationContext ctx) {
		stopFlag = true;
		this.ctx = ctx;
	}

	public void run() {

		LinkedBlockingQueue<Task> sendQueue = new LinkedBlockingQueue<>();

		// ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		timeTaskService = (TimeTaskService) ctx.getBean("timeTaskService");
		System.out.println("开始轮询");

		ife = new InfromEnd();
		ife.setData(sendQueue, ctx);
		ife.start();

		while (stopFlag) {
			if (sendQueue.size() == 0) {
				time = System.currentTimeMillis();
				list = timeTaskService.patrol(time);
				for (Task t : list) {
					try {
						sendQueue.put(t);
						System.out.println("insert and sendQueue.size=" + sendQueue.size());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
			try {
				System.out.println("sleep and sendQueue.size=" + sendQueue.size());
				this.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 感受一下ife的死活
			if (!ife.isAlive()) {
				ife = new InfromEnd();
				ife.setData(sendQueue, ctx);
				ife.start();
			}
		}

		ife.stopFlag = false;
	}

}
