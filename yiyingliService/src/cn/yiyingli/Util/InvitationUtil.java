package cn.yiyingli.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by wpexia on 16/3/16.
 */
public class InvitationUtil {
	public static String createInvitationCode(String userName) {
		try {
			byte[] tempString = (userName + (new Date().toString())).getBytes("UTF-8");
			byte[] result = MessageDigest.getInstance("MD5").digest(tempString);
			return new String(result, "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
