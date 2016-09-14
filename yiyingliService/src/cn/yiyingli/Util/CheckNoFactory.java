package cn.yiyingli.Util;

import java.util.Random;

public class CheckNoFactory {

	public static String createCheckNo() {
		Random random = new Random();
		String no = "";
		for (int i = 1; i <= 6; i++) {
			no = no + random.nextInt(10);
		}
		return no;
	}

	public static void main(String[] args) {
		System.out.println(createCheckNo());
	}
}
