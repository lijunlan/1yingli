package cn.yiyingli.ExchangeData;

public class NoShowUtil {

	public static String getStrNumber(Long no) {
		if (no != null) {
			if (no / 10000 > 0) {
				String t = (float) no / 10000F + "";
				String[] ts = t.split("\\.");
				return ts[0] + (ts[0].length() >= 4 ? "" : "."+ts[1].substring(0, 4 - ts[0].length())) + "万";
			} else {
				return no + "";
			}
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(getStrNumber(12345L));
	}
}
