package cn.yiyingli.Exception;

public class WeiboAccessTokenOverdueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2358408719244152270L;

	private int errorCode;

	private String errorMessage;

	public WeiboAccessTokenOverdueException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.errorMessage = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
