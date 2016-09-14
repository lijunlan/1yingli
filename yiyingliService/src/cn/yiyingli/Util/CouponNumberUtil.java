package cn.yiyingli.Util;

import java.util.Random;

public class CouponNumberUtil {

	public static String getNewNumber(int index) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= index; i++) {
			sb.append(getSingle());
		}
		return sb.toString();
	}

	private static char getSingle() {
		// 48-122
		Random random = new Random();
		char t = 91;
		while ((t >= 91 && t <= 96) || (t >= 58 && t <= 64)) {
			t = (char) (48 + random.nextInt(75));
		}
		return t;
	}

	public static void main(String[] args) {
		for (int i = 1; i <= 100; i++) {
			System.out.println(getNewNumber(10));
		}
	}

}
