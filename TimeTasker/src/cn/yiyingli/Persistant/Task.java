package cn.yiyingli.Persistant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TASK")
public class Task {
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false)
	long taskID;
	
	@Column(name = "KIND")
	String taskKind;
	
	@Column(name = "ACTION")
	String taskAction;
	
	@Column(name = "ENDTIME")
	long taskEndTime;
	
	@Column(name = "DATA")
	String data;
	
	@Column(name = "SENDTIMES")
	int sendTimes;
	
	public long getTaskID() {
		return taskID;
	}
	public void setTaskID(long taskID) {
		this.taskID = taskID;
	}
	public String getTaskKind() {
		return taskKind;
	}
	public void setTaskKind(String taskKind) {
		this.taskKind = taskKind;
	}
	public String getTaskAction() {
		return taskAction;
	}
	public void setTaskAction(String taskAction) {
		this.taskAction = taskAction;
	}
	public long getTaskEndTime() {
		return taskEndTime;
	}
	public void setTaskEndTime(long taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}
	
}
