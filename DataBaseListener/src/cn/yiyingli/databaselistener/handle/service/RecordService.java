package cn.yiyingli.databaselistener.handle.service;

import java.util.Date;
import java.util.List;

import com.aliyun.drc.client.message.DataMessage.Record;
import com.aliyun.drc.client.message.DataMessage.Record.Field;

import cn.yiyingli.databaselistener.handle.TableService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RecordService extends TableService {

	public RecordService(Record record) {
		super(record);
	}

	@Override
	protected void dealUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void dealInsert() {
		List<Field> fields = record.getFieldList();
		String kind = "";
		String type = "";
		String createTime = "";
		String recordData = "";
		for (Field field : fields) {
			if (field.getFieldname().equalsIgnoreCase("createtime")) {
				createTime = getValue(field);
			} else if (field.getFieldname().equalsIgnoreCase("data")) {
				recordData = getValue(field);
			} else if (field.getFieldname().equalsIgnoreCase("kind")) {
				kind = getValue(field);
			} else if (field.getFieldname().equalsIgnoreCase("type")) {
				type = getValue(field);
			}
		}
		if (!((type.equals("1") || type.equals("0")) && kind.equals("0"))) {
			return;
		}
		String teacherId = recordData.split(",")[0].split("=")[1];
		String userId = recordData.split(",")[1].split("=")[1];
		JSONObject data = new JSONObject();
		JSONArray clist = new JSONArray();
		clist.add(userId);
		clist.add(teacherId);
		clist.add("click");
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
		// TODO Auto-generated method stub

	}

}
