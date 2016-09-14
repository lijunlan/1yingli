package cn.yiyingli.Util;

public class MyLoop implements Runnable {

	@Override
	public void run() {
		while (true) {
			GetKeyUtil.refresh();
			try {
				Thread.sleep(7000 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
