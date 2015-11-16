package cn.yiyingli.TimeTasker;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.yiyingli.Persistant.Task;
import cn.yiyingli.Service.TimeTaskService;

public class Core {
	
	static Task task;
	
	static ApplicationContext ctx;
	
	static TimeTaskService timeTaskService;
	
	public static void setApplicationContext(ApplicationContext ctx){
		Core.ctx=ctx;
	}
	 
	public static ApplicationContext getApplicationContext(){
		return ctx;
	}
	
	public static void setTask(Map<String, String> map){
		task=new Task();
		task.setTaskKind(map.get("kind"));
		task.setTaskAction(map.get("action"));
		task.setTaskEndTime(Long.parseLong(map.get("endTime")));
		task.setData(map.get("data"));
		task.setSendTimes(0);
		timeTaskService=(TimeTaskService)ctx.getBean("timeTaskService");
		timeTaskService.setTask(task);
	}
}
