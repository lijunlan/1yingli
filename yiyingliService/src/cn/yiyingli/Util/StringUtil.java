package cn.yiyingli.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}

		return dest.replaceAll("\b", "");
	}

	/**
	 * 去掉字符串中的html源码。<br>
	 * 
	 * @param con
	 *            内容
	 * 
	 * @param length
	 *            截取长度
	 * 
	 * @param end
	 *            原始字符串超过截取长度时，后面增加字符
	 * @return 去掉后的内容
	 */
	public static String subStringHTML(String con, int length, String end) {
		String content = "";
		if (con != null) {
			content = con.replaceAll("</?[^>]+>", "");// 剔出了<html>的标签
			content = content.replace("&nbsp;", "");
			content = content.replace("\"", "‘");
			content = content.replace("'", "‘");
			if (content.length() > length) {
				content = content.substring(0, length) + end;
			}
		}

		return content;
	}
}
