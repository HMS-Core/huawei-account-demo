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
package com.huawei.androidcodedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.account.AccountAuthManager
import com.huawei.hms.support.account.request.AccountAuthParams
import com.huawei.hms.support.account.request.AccountAuthParamsHelper
import com.huawei.hms.support.account.result.AuthAccount
import com.huawei.hms.support.account.service.AccountAuthService

class MainActivity : AppCompatActivity() {
    // 华为帐号登录授权服务，提供静默登录接口silentSignIn，获取前台登录视图getSignInIntent，登出signOut等接口
    // Huawei account service, provides silent signIn API silentSignIn, obtain front-end sign-in view API getSignInIntent, sign out API signOut and other APIs
    private var mAuthService: AccountAuthService? = null

    // 华为帐号登录授权参数
    // parameter
    private var mAuthParam: AccountAuthParams? = null

    // 用户自定义signInIntent请求码
    // User-defined signInIntent request code
    private val REQUEST_CODE_SIGN_IN = 1000

    // 用户自定义日志标记
    // User-defined log mark
    private val TAG = "Account"
    private var logTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // activity_main为自定义布局文件名称
        // activity_main is the name of the custom layout file
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.HuaweiIdAuthButton).setOnClickListener { silentSignInByHwId() }
        findViewById<View>(R.id.HuaweiIdSignOutButton).setOnClickListener { signOut() }
        findViewById<View>(R.id.HuaweiIdCancelAuthButton).setOnClickListener { cancelAuthorization() }
        logTextView = findViewById<View>(R.id.LogText) as TextView
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
    private fun silentSignInByHwId() {
        // 1、配置登录请求参数AccountAuthParams，包括请求用户的id(openid、unionid)、email、profile(昵称、头像)等;
        // 2、DEFAULT_AUTH_REQUEST_PARAM默认包含了id和profile（昵称、头像）的请求;
        // 3、如需要再获取用户邮箱，需要setEmail();
        // 4、如需要获取其他受限信息，如手机号，则需要先申请scope，再设置请求参数；
        // 5、通过setAuthorizationCode()来选择使用code模式，最终所有请求的用户信息都可以调服务器的接口获取；
        // 1. Configure the login request parameters AccountAuthParams, including the requested user id (openid, unionid),
        // email, profile (nickname, avatar), etc.
        // 2. DEFAULT_AUTH_REQUEST_PARAM includes requests for id and profile (nickname, avatar) by default.
        // 3. If you need to get the user mailbox again, you need setEmail();
        // 4. If you need to obtain other restricted information, such as mobile phone, you need to apply for scope first,
        // and then set the request parameters.
        // 5. Select the code mode through setAuthorizationCode(), and finally all requested user information can be obtained by calling the server's interface;
        mAuthParam = AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setEmail()
                .setAuthorizationCode()
                .createParams()

        // 使用请求参数构造华为帐号登录授权服务AccountAuthService
        // Use request parameters to construct a Huawei account login authorization service AccountAuthService
        mAuthService = AccountAuthManager.getService(this, mAuthParam)

        // 使用静默登录进行华为帐号登录
        // Use silent sign in for HUAWEI ID login
        val task = mAuthService!!.silentSignIn()
        task.addOnSuccessListener { authAccount -> // 静默登录成功，处理返回的帐号对象AuthAccount，获取帐号信息并处理
            // Silent sign in is successful, the returned account object AuthAccount is processed,account information is obtained and processed
            dealWithResultOfSignIn(authAccount)
        }
        task.addOnFailureListener { e -> //静默登录失败，使用getSignInIntent()方法进行前台显式登录
            // Silent sign in fails, use the getSignInIntent() method to log in from the foreground
            if (e is ApiException) {
                val apiException = e
                val signInIntent = mAuthService!!.getSignInIntent()
                startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN)
            }
        }
    }

    /**
     * 处理返回的AuthAccount，获取帐号信息
     * Process the returned AuthAccount and get account information
     *
     * @param authAccount AccountAccount对象，用于记录帐号信息(AccountAccount object, used to record account information)
     */
    private fun dealWithResultOfSignIn(authAccount: AuthAccount) {
        showLog("code:" + authAccount.authorizationCode)
        Log.i(TAG, "code:" + authAccount.authorizationCode)
        //TODO 获取到Code信息后，应用需要发送给应用服务器
        //TODO After obtaining the Code information, the application needs to send it to the application server
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            Log.i(TAG, "onActivitResult of sigInInIntent, request code: $REQUEST_CODE_SIGN_IN")
            val authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data)
            if (authAccountTask.isSuccessful) {
                showLog("sign in success")
                // 登录成功，获取到登录帐号信息对象authAccount
                // The login is successful, and the login account information object authAccount is obtained
                val authAccount = authAccountTask.result
                dealWithResultOfSignIn(authAccount)
                Log.i(TAG, "onActivitResult of sigInInIntent, request code: $REQUEST_CODE_SIGN_IN")
            } else {
                // 登录失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
                // Login failed. The status code identifies the reason for the failure. Please refer to the error
                // code reference in the API for detailed error reasons.
                Log.e(TAG, "sign in failed : " + (authAccountTask.exception as ApiException).statusCode)
                showLog("sign in failed : " + (authAccountTask.exception as ApiException).statusCode)
            }
        }
    }

    private fun signOut() {
        val signOutTask = mAuthService!!.signOut()
        signOutTask.addOnSuccessListener {
            Log.i(TAG, "signOut Success")
            showLog("signOut Success")
        }.addOnFailureListener {
            Log.i(TAG, "signOut fail")
            showLog("signOut fail")
        }
    }

    private fun cancelAuthorization() {
        val task = mAuthService!!.cancelAuthorization()
        task.addOnSuccessListener {
            Log.i(TAG, "cancelAuthorization success")
            showLog("cancelAuthorization success")
        }
        task.addOnFailureListener { e ->
            Log.i(TAG, "cancelAuthorization failure：" + e.javaClass.simpleName)
            showLog("cancelAuthorization failure：" + e.javaClass.simpleName)
        }
    }

    private fun showLog(log: String) {
        logTextView!!.text = "log:\n$log"
    }

}