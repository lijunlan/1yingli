package cn.yiyingli.databaselistener.handle.service;

import java.util.Date;
import java.util.List;

import com.aliyun.drc.client.message.DataMessage.Record;
import com.aliyun.drc.client.message.DataMessage.Record.Field;

import cn.yiyingli.databaselistener.handle.TableService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserLikeTeacherService extends TableService {

	public UserLikeTeacherService(Record record) {
		super(record);
	}

	@Override
	protected void dealUpdate() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void dealInsert() {
		List<Field> fields = record.getFieldList();
		String createTime = "";
		String teacherId = "";
		String userId = "";
		for (Field field : fields) {
			if (field.getFieldname().equalsIgnoreCase("createtime")) {
				createTime = getValue(field);
			} else if (field.getFieldname().equalsIgnoreCase("teacher_id")) {
				teacherId = getValue(field);
			} else if (field.getFieldname().equalsIgnoreCase("user_id")) {
				userId = getValue(field);
			}
		}
		JSONObject data = new JSONObject();
		JSONArray clist = new JSONArray();
		clist.add(userId);
		clist.add(teacherId);
		clist.add("collect");
		clist.add(0);
		clist.add(1);
		clist.add(createTime);
		clist.add("null");
		clist.add("null");
		clist.add("null");
		clist.add("null");
		clist.add("null");
		clist.add("null");
		data.put("data", clist);
		data.put("table", "user_behavior");
		data.put("partition", "ds='" + DS_DATAFORMAT.format(new Date()) + "'");
		upLoadData(data);
	}

	@Override
	protected void dealRollback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void dealDefault() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void dealDelete() {
		List<Field> fields = record.getFieldList();
		String createTime = "";
		String teacherId = "";
		String userId = "";
		for (Field field : fields) {
			if (field.getFieldname().equalsIgnoreCase("createtime")) {
				createTime = getValue(field);
			} else if (field.getFieldname().equalsIgnoreCase("teacher_id")) {
				teacherId = getValue(field);
			} else if (field.getFieldname().equalsIgnoreCase("user_id")) {
				userId = getValue(field);
			}
		}
		JSONObject data = new JSONObject();
		JSONArray clist = new JSONArray();
		clist.add(userId);
		clist.add(teacherId);
		clist.add("uncollect");
		clist.add(0);
		clist.add(1);
		clist.add(createTime);
		clist.add("null");
		clist.add("null");
		clist.add("null");
		clist.add("null");
		clist.add("null");
		clist.add("null");
		data.put("data", clist);
		data.put("table", "user_behavior");
		data.put("partition", "ds='" + DS_DATAFORMAT.format(new Date()) + "'");
		upLoadData(data);
	}

}
