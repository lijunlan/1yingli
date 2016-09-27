package cn.yiyingli.databaselistener.handle.service;

import java.util.Date;
import java.util.List;

import com.aliyun.drc.client.message.DataMessage.Record;
import com.aliyun.drc.client.message.DataMessage.Record.Field;

import cn.yiyingli.databaselistener.handle.TableService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OrdersService extends TableService {

	public OrdersService(Record record) {
		super(record);
	}

	@Override
	protected void dealUpdate() {
		List<Field> fields = record.getFieldList();
		String createTime = "";
		String teacherId = "";
		String userId = "";
		String money = "";
		String count = "";
		String oldstate = "";
		String newstate = "";
		for (int i = 0; i < fields.size(); i++) {
			Field field = fields.get(i);
			if (i % 2 == 1) {
				if (field.getFieldname().equalsIgnoreCase("createtime")) {
					createTime = getValue(field);
				} else if (field.getFieldname().equalsIgnoreCase("teacher_id")) {
					teacherId = getValue(field);
				} else if (field.getFieldname().equalsIgnoreCase("user_id")) {
					userId = getValue(field);
				} else if (field.getFieldname().equalsIgnoreCase("money")) {
					userId = getValue(field);
				} else if (field.getFieldname().equalsIgnoreCase("count")) {
					userId = getValue(field);
				} else if (field.getFieldname().equalsIgnoreCase("state")) {
					newstate = getValue(field);
				}
			} else {
				if (field.getFieldname().equalsIgnoreCase("state")) {
					oldstate = getValue(field);
				}
			}
		}
		if (oldstate.equals(newstate)) {
			return;
		}
		if (!newstate.startsWith("0300")) {
			return;
		}
		JSONObject data = new JSONObject();
		JSONArray clist = new JSONArray();
		clist.add(userId);
		clist.add(teacherId);
		clist.add("consume");
		clist.add(money);
		clist.add(count);
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
	protected void dealInsert() {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
