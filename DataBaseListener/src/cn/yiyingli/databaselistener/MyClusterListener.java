package cn.yiyingli.databaselistener;

import java.util.List;

import com.aliyun.drc.clusterclient.ClusterListener;
import com.aliyun.drc.clusterclient.message.ClusterMessage;

import cn.yiyingli.databaselistener.handle.RecordHandle;
import cn.yiyingli.databaselistener.handle.TableService;

public class MyClusterListener extends ClusterListener {

	@Override
	public void noException(Exception exception) {
		exception.printStackTrace();
	}

	@Override
	public void notify(List<ClusterMessage> messages) {
		for (ClusterMessage message : messages) {
			try {
				if (message.getRecord().getTablename() != null) {
					System.out.println("table:" + message.getRecord().getTablename());
					TableService tableService = RecordHandle.getService(message.getRecord());
					if (tableService != null) {
						tableService.deal();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 消费完数据后向DTS汇报ACK，必须调用
				message.ackAsConsumed();
			}
		}
	}

}
