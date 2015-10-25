package cn.yiyingli.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

	public static void main(String[] args) {
		// System.out.println(checkPassword("3456931"));
		System.out.println(checkGlobalPhoneNumber(""));
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean checkGlobalPhoneNumber(String number) {
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile(
					"^\\s*\\+?\\s*(\\(\\s*\\d+\\s*\\)|\\d+)(\\s*-?\\s*(\\(\\s*\\d+\\s*\\)|\\s*\\d+\\s*))*\\s*$");
			Matcher matcher = regex.matcher(number);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-9]))|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证密码，6-20位字母或数字，至少包含一个字母
	 * 
	 * @param psw
	 * @return
	 */
	public static boolean checkPassword(String psw) {
		boolean flag = false;
		try {
			// 验证是不是6-20位纯数字
			Pattern regex1 = Pattern.compile("[0-9]{6,20}");
			Matcher matcher = regex1.matcher(psw);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return !flag;
	}
}
