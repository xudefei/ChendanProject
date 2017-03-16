package com.example.administrator.chendanproject.PayUtils;

import java.util.Map;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.H5PayActivity;
import com.alipay.sdk.app.PayTask;
import com.example.administrator.chendanproject.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends FragmentActivity {
	
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016072900115009";
	
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "";

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDJq2sNwlKMyZhT\n" +
			"7J11hWRgTFmhqxMMj/HMA/Nz171yHQXu6n5M7J6bMGxzCem/rsHr8haspE4QEjiX\n" +
			"RoS+tY6DhqVlez60buxPRF7dKFjOKqs4tiYKSw4X7gCL64vWwwSKc9jMwyggGF2d\n" +
			"tb3oSGMVeot1cWOIPcnVkNMdgp4fYAeTdmT+WAgXlINAuj486ZsDMAvF+9Uaka4y\n" +
			"uWKK2pAF/+UhjDqF0A0qmHOhPAhxXHQuAemSS5VD2VcSQYnLI4yIUQfgGHZQwdA0\n" +
			"t60efhAwkg6PQM9iU4cgC2HSEto3yrRcDZBqqKxUXDlpxx9/I+JbVBG8hSV2tXP8\n" +
			"aPevpIeDAgMBAAECggEBAJQE0zBoHnrjRIDkvLWTUJc1stvsWrDvvgOGKtwtzwjA\n" +
			"sKgpSMZhLhCtvAu7jOt58y+gom/YsMsu9IZj5e6sB4wV+bPAf8ejWqLbhd1BTd0U\n" +
			"iFO5rbYEXLXHpHMOUbcxsFWGreusmfjkqWGmEVs6Ni9/oHh03x8Sat/rymhwhGp1\n" +
			"nQsj5UPF8EAzjnN0C7psUyn/T/szdkXBSghOSTqgJjS7AyzumopjPC5BzU3C0QHB\n" +
			"X6tGy0mak2i6UDsOEZpqFYuliKfJ3Tg3xItQNnP2cMGhHB7WPQgK3q4m0xpSFyuS\n" +
			"L/0JIbWgdwnjS95VT+w597QO1IEsHJPPO7z5oXynhOECgYEA8x6Ko+fk5iVe1P2I\n" +
			"LcHSDp0Ie9heezVq11e2nx9dsofAxFyRBblUDf0DzUQHsrTlh/dEwBpjH99G2bkO\n" +
			"dtFfsXsx8gFqq9U0vUeX0M0wcbp7yjugXqLMjmqNugotW+6sdg1LIT6XsVuGI4Ia\n" +
			"kmP78N4D4T8gr2aoKPAZjau9cmsCgYEA1FqwYS0KZ9sBtocMeVU1pC/Qa2UjvuaR\n" +
			"2FOYc/03/mkXI4mGWRmv1I0knOocFWv7nU5YTn816PANpZYXrvjk139hKX2MljhL\n" +
			"9dHW+K7cEIBRDijNxi0AdXZf0D5Q11tYG1Xf2bcLc9rGlBeWVfDAL32u1VLS1176\n" +
			"gkH1poD8dUkCgYBGNXW4K8mwpO4j+CuRez2cNsEycqOmVSDLm2sDvN0kJ4wFqO3A\n" +
			"h2q8t2qXpqPKee8nOsNB7tzdYW9mhMimi4RlDry3tK0HfoTCfKBdDkSFM2N4XYvM\n" +
			"iJsmLaNMLJA40k4/sUsXCKpp75JnQrVFvVYOky7vp3la7fx2BhB7FzOYjQKBgERM\n" +
			"8wQeb4Vd7JwfrcRswrcPZfLU8f1ZvbkdkiBkZpkehgG5HwwTdflgeFE7/1Joi+JZ\n" +
			"q4973QQSCeGjd/SgO0KQxtiIeCps6wmit4j+/OsVynxKZX0DRm2BaGnbUTcjOyB3\n" +
			"q2skM7vMtyg0ufbkW3wq6YvVetkoxFwXCQLH4+t5AoGAfZblgS8a6NegWKVV+fDo\n" +
			"PQnKSSLdpqRI7j/OzqAdyq4rA5H1/zD2PIVUaQWktIKG6IRjft6w/GKdNe7mpsSf\n" +
			"mJAglwadrksUPq2OTxniUN2xBNgMiWkSBqw+7m7ZchG3OLZAbkZ2gQ8MTYP8b+Ir\n" +
			"lrJ8uZzki+pVEctBnJl9KRM=";

	public static final String RSA_PRIVATE = "";
	
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case SDK_AUTH_FLAG: {
				@SuppressWarnings("unchecked")
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				String resultStatus = authResult.getResultStatus();

				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
					Toast.makeText(PayDemoActivity.this,
							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
							.show();
				} else {
					// 其他状态值则为授权失败
					Toast.makeText(PayDemoActivity.this,
							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
	}
	
	/**
	 * 支付宝支付业务
	 * 商家的app集成的支付宝的SDK去调用支付宝的支付模块
	 * 
	 * @param v
	 */
	public void payV2(View v) {
		//&&优先级高于||
		if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
	
		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * orderInfo的获取必须来自服务端；
		 */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 支付宝账户授权业务
	 * 
	 * @param v
	 */
	public void authV2(View v) {
		if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
				|| (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
				|| TextUtils.isEmpty(TARGET_ID)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
			return;
		}

		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * authInfo的获取必须来自服务端；
		 */
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
		
		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
		final String authInfo = info + "&" + sign;
		Runnable authRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造AuthTask 对象
				AuthTask authTask = new AuthTask(PayDemoActivity.this);
				// 调用授权接口，获取授权结果
				Map<String, String> result = authTask.authV2(authInfo, true);

				Message msg = new Message();
				msg.what = SDK_AUTH_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread authThread = new Thread(authRunnable);
		authThread.start();
	}
	
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		/*Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		*//**
		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
		 * 商户可以根据自己的需求来实现
		 *//*
		String url = "https://open.alipay.com/platform/home.htm";
		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);*/
		Intent intent = new Intent(this, H5PayActivity.class);
		Bundle extras = new Bundle();
		String url = "https://tradeexprod.alipay.com/index.htm";
		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);


	}

}
