/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.huawei.serverlessidtokendemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.serverlessidtokendemo.verification.beans.IdTokenEntity;
import com.huawei.serverlessidtokendemo.verification.interfaces.IVerifyCallBack;
import com.huawei.serverlessidtokendemo.verification.utils.IdTokenUtils;

public class MainActivity extends AppCompatActivity {
	// 华为帐号登录授权服务，提供静默登录接口silentSignIn，获取前台登录视图getSignInIntent，登出signOut等接口
	// Huawei account service, provides silent signIn API silentSignIn, obtain front-end sign-in view API getSignInIntent, sign out API signOut and other APIs
	private AccountAuthService mAuthService;

	// 华为帐号登录授权参数
	// parameter
	private AccountAuthParams mAuthParam;

	// 用户自定义signInIntent请求码
	// User-defined signInIntent request code
	private static final int REQUEST_CODE_SIGN_IN = 1000;

	// 用户自定义日志标记
	// User-defined log mark
	private static final String TAG = "Account";

	// 应用的APP ID
	// APP ID of this application
	private static final String APP_ID = "104366981";
	private TextView logTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity_main为自定义布局文件名称
		// activity_main is the name of the custom layout file
		setContentView(R.layout.activity_main);
		findViewById(R.id.HuaweiIdAuthButton).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				silentSignInByHwId();
			}
		});
		findViewById(R.id.HuaweiIdSignOutButton).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				signOut();
			}
		});

		findViewById(R.id.HuaweiIdCancelAuthButton).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelAuthorization();
			}
		});
		logTextView = (TextView) findViewById(R.id.LogText);
	}

	/**
	 * 静默登录，如果设备上的华为帐号系统帐号已经登录，并且用户已经授权过，无需再拉起登录页面和授权
	 * 页面，将直接静默登录成功，在成功监听器中，返回帐号信息;
	 * 如果华为帐号系统帐号未登录或者用户没有授权，静默登录会失败，需要显示拉起前台登录授权视图。
	 * Silent sign in, if the HUAWEI ID system account on the device has been logged in and
	 * the user has been authorized, there is no need to pull up the login page and authorization page,
	 * and the silent login will be successful. In the success monitor, the account information will be returned;
	 * If the HUAWEI ID system account is not logged in or the user is not authorized, the silent login will fail,
	 * and the front-end login authorization view needs to be displayed.
	 */
	private void silentSignInByHwId() {
		// 1、配置登录请求参数AccountAuthParams，包括请求用户id(openid、unionid)、email、profile（昵称、头像）等。
		// 2、DEFAULT_AUTH_REQUEST_PARAM默认包含了id和profile（昵称、头像）的请求。
		// 3、如需要再获取用户邮箱，需要setEmail();
		// 4、如需要获取其他受限信息，如国家和地区，则需要先申请scope，再设置请求参数。
		// 5、通过setIdToken()来选择使用id token模式，最终所有请求的用户信息都可以从idtoken中解析出来
		// 1. Configure the login request parameters AccountAuthParams, including the requested user id (openid, unionid),
		// email, profile (nickname, avatar), etc.
		// 2. DEFAULT_AUTH_REQUEST_PARAM includes requests for id and profile (nickname, avatar) by default.
		// 3. If you need to get the user mailbox again, you need setEmail();
		// 4. If you need to obtain other restricted information, such as countries and regions, you need to apply for scope first,
		// and then set the request parameters.
		// 5. Select the id token mode through setIdToken(), and finally all requested user information can be parsed from idtoken
		mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
				.setEmail()
				.setIdToken()
				.createParams();

		// 使用请求参数构造华为帐号登录授权服务AccountAuthService
		// Use request parameters to construct a Huawei account login authorization service AccountAuthService
		mAuthService = AccountAuthManager.getService(this, mAuthParam);

		// 使用静默登录进行华为帐号登录
		// Use silent sign in for HUAWEI ID login
		Task<AuthAccount> task = mAuthService.silentSignIn();
		task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
			@Override
			public void onSuccess(AuthAccount authAccount) {
				// 静默登录成功，处理返回的帐号对象AuthAccount，获取帐号信息并处理
				// Silent sign in is successful, the returned account object AuthAccount is processed,account information is obtained and processed
				dealWithResultOfSignIn(authAccount);
			}
		});
		task.addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(Exception e) {
				// 静默登录失败，使用getSignInIntent()方法进行前台显式登录
				// Silent sign in fails, use the getSignInIntent() method to log in from the foreground
				if (e instanceof ApiException) {
					ApiException apiException = (ApiException) e;
					Intent signInIntent = mAuthService.getSignInIntent();
					startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
				}
			}
		});
	}

	/**
	 * 处理返回的AuthAccount，获取帐号信息
	 * Process the returned AuthAccount and get account information
	 *
	 * @param authAccount AccountAccount对象，用于记录帐号信息(AccountAccount object, used to record account information)
	 */
	private void dealWithResultOfSignIn(AuthAccount authAccount) {
		Log.i(TAG, "idToken:" + authAccount.getIdToken());
		showLog("idToken:" + authAccount.getIdToken());
		String idToken = authAccount.getIdToken();
		verifyIdToken(idToken, authAccount.getUnionId(), APP_ID);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_SIGN_IN) {
			Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
			Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
			if (authAccountTask.isSuccessful()) {
				showLog("sign in success");
				// 登录成功，获取到登录帐号信息对象authAccount
				// The login is successful, and the login account information object authAccount is obtained
				AuthAccount authAccount = authAccountTask.getResult();
				dealWithResultOfSignIn(authAccount);
				Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
			} else {
				// 登录失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
				// Login failed. The status code identifies the reason for the failure. Please refer to the error
				// code reference in the API for detailed error reasons.
				Log.e(TAG, "sign in failed : " + ((ApiException) authAccountTask.getException()).getStatusCode());
				showLog("sign in failed : " + ((ApiException) authAccountTask.getException()).getStatusCode());
			}
		}
	}

	private void signOut() {
		if (mAuthService == null) {
			return;
		}
		Task<Void> signOutTask = mAuthService.signOut();
		signOutTask.addOnSuccessListener(new OnSuccessListener<Void>() {
			@Override
			public void onSuccess(Void aVoid) {
				Log.i(TAG, "signOut Success");
				showLog("signOut Success");
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(Exception e) {
				Log.e(TAG, "signOut fail");
				showLog("signOut fail");
			}
		});
	}

	private void cancelAuthorization() {
		if (mAuthService == null) {
			return;
		}
		Task<Void> task = mAuthService.cancelAuthorization();
		task.addOnSuccessListener(new OnSuccessListener<Void>() {
			@Override
			public void onSuccess(Void aVoid) {
				Log.i(TAG, "cancelAuthorization success");
				showLog("cancelAuthorization success");
			}
		});
		task.addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(Exception e) {
				Log.e(TAG, "cancelAuthorization failure：" + e.getClass().getSimpleName());
				showLog("cancelAuthorization failure：" + e.getClass().getSimpleName());
			}
		});
	}

	/**
	 * verify id token
	 */
	private void verifyIdToken(String idToken, String unionId, String appId) {
		if (idToken == null) {
			Log.e(TAG, "\n" + "id token id null");
			showLog("id token id null");
			return;
		}

		IdTokenUtils idTokenUtils = new IdTokenUtils();
		IdTokenEntity idTokenEntity = idTokenUtils.decodeJsonStringFromIdtoken(idToken);
		if (idTokenEntity != null) {
			Log.i(TAG, "\n" + "idToken parse success " + "\n" + idTokenEntity.toString() + "\n"
					+ "exp:" + idTokenEntity.getExpTime());
			idTokenUtils.validateIdToken(unionId, idToken, appId, idTokenEntity, new IVerifyCallBack() {
				@Override
				public void onSuccess() {
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							Log.i(TAG, "\n" + "IdToken validate success");
							Log.i(TAG, "\n" + "user infos:" + idTokenEntity.getPayloadJson());
							showLog("IdToken validate success" + "\n" + "user infos:" + idTokenEntity.getPayloadJson());
							// TODO 获取到用户信息后，应用可根据需要使用这些信息
							// TODO After obtaining the user infos, the application can use them
						}
					});
				}

				@Override
				public void onFailed(String errorMsg) {
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							Log.e(TAG, "IdToken validate failed" + "\n" + errorMsg);
							showLog("IdToken validate failed");
							// idToken校验失败后，重新获取用户授权
							silentSignInByHwId();
						}
					});
				}
			});
		}
	}

	private void showLog(String log) {
		logTextView.setText("log:" + "\n" + log);
	}

}