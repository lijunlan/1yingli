package cn.yiyingli.databaselistener.handle;

import com.aliyun.drc.client.message.DataMessage.Record;

import cn.yiyingli.databaselistener.handle.service.OrdersService;
import cn.yiyingli.databaselistener.handle.service.RecordService;
import cn.yiyingli.databaselistener.handle.service.UserLikeTeacherService;

public class RecordHandle {

	public static TableService getService(Record record) {
		TableService tableService = null;
		String tableName = record.getTablename();
		if (tableName.equalsIgnoreCase("userliketeacher")) {
			tableService = new UserLikeTeacherService(record);
		} else if (tableName.equalsIgnoreCase("record")) {
			tableService = new RecordService(record);
		} else if (tableName.equalsIgnoreCase("orders")) {
			tableService = new OrdersService(record);
		}
		return tableService;
	}

}
