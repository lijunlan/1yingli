package cn.yiyingli.WeiboUtil;

import cn.yiyingli.Util.LogUtil;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class GetSingleUserWeiboInfoUtil {

	public static void main(String[] args) {
		AccessToken accessToken = getAccessToken("dd9ce06a70f6da46b604285f764d6852");
		System.out.println(accessToken);
		if (accessToken != null) {
			User user = getUserInfo(accessToken);
			System.out.println(user);
		}
	}

	public static User getUserInfo(AccessToken accessToken) {
		Users um = new Users(accessToken.getAccessToken());
		try {
			User user = um.showUserById(accessToken.getUid());
			return user;
		} catch (WeiboException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static User getUserInfo(String accessToken, String uid) {
		Users um = new Users(accessToken);
		try {
			User user = um.showUserById(uid);
			return user;
		} catch (WeiboException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static AccessToken getAccessToken(String code) {
		Oauth oauth = new Oauth();
		try {
			AccessToken access_token = oauth.getAccessTokenByCode(code);
			return access_token;
		} catch (WeiboException e) {
			if (401 == e.getStatusCode()) {
				LogUtil.info("Unable to get the access token.", GetSingleUserWeiboInfoUtil.class);
			} else {
				e.printStackTrace();
			}
		}
		return null;
	}

}
