package cn.yiyingli.PayPal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import cn.yiyingli.Persistant.Order;

public class PayPalMobileConfirm {

	private static PayPalMobileConfirm singleInstance = null;

	public static PayPalMobileConfirm getInstance() throws IOException {
		if (singleInstance == null) {
			try {
				singleInstance = new PayPalMobileConfirm();
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException("config can not be finded");
			}
		}
		return singleInstance;
	}

	private OAuthTokenCredential tokenCredential;

	private PayPalMobileConfirm() throws IOException {
		InputStream input = PayPalMobileConfirm.class.getClassLoader()
				.getResourceAsStream("cn/yiyingli/PayPal/sdk_config.properties");
		Properties properties = new Properties();
		properties.load(input);
		tokenCredential = Payment.initConfig(properties);
	}

	public boolean checkPayment(String paymentId, Order order) throws PayPalRESTException {
		String accessToken = tokenCredential.getAccessToken();
		Payment payment = Payment.get(accessToken, paymentId);
		if (!payment.getState().equals("approved")) {
			return false;
		}
		if (Float.valueOf(payment.getTransactions().get(0).getAmount().getTotal()) != order.getMoney().floatValue()) {
			return false;
		}
		if (!payment.getTransactions().get(0).getAmount().getCurrency().equals("USD")) {
			return false;
		}
		if (!payment.getTransactions().get(0).getRelatedResources().get(0).getSale().getState().equals("completed")) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			InputStream input = PayPalMobileConfirm.class.getClassLoader()
					.getResourceAsStream("cn/yiyingli/PayPal/sdk_config.properties");
			Properties properties = new Properties();
			properties.load(input);
			OAuthTokenCredential tokenCredential = Payment.initConfig(properties);
			String accessToken = tokenCredential.getAccessToken();
			System.out.println(accessToken);
			Payment payment = Payment.get(accessToken, "PAY-6XT28492MR2687824KY5NFGI");
			System.out.println(payment.getState());
			System.out.println(payment.getTransactions().get(0).getAmount().getTotal());
			System.out.println(payment.getTransactions().get(0).getAmount().getCurrency());
			System.out.println(payment.getTransactions().get(0).getRelatedResources().get(0).getSale().getState());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
