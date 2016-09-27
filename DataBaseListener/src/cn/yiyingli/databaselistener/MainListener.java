package cn.yiyingli.databaselistener;

import com.aliyun.drc.clusterclient.ClusterClient;
import com.aliyun.drc.clusterclient.ClusterListener;
import com.aliyun.drc.clusterclient.DefaultClusterClient;
import com.aliyun.drc.clusterclient.RegionContext;
import cn.yiyingli.odps.upload.UploadUtil;

public class MainListener {

	public static void main(String args[]) throws Exception {
		// 创建一个RegionContext
		RegionContext context = new RegionContext();

		context.setAccessKey(UploadUtil.ACCESSID);
		context.setSecret(UploadUtil.ACCESSKEY);
		// 运行SDK的服务器是否使用公网IP连接DTS订阅通道
		context.setUsePublicIp(true);

		// 创建订阅消费者
		final ClusterClient client = new DefaultClusterClient(context);
		ClusterListener listener = new MyClusterListener();
		
		client.addConcurrentListener(listener);
		// 设置请求的订阅通道ID
		client.askForGUID("dts1k1zjagl0r0vn");
		// 启动后台线程， 注意这里不会阻塞， 主线程不能退出
		client.start();
		System.out.println("eng started");
	}

}
