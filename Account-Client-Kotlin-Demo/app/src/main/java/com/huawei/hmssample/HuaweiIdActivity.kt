/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.huawei.hmssample

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import com.huawei.logger.Log
import com.huawei.logger.LoggerActivity
import kotlinx.android.synthetic.main.activity_huaweiid.*

/**
 *  Codelab
 *  Demonstration of HuaweiId
 */
class HuaweiIdActivity : LoggerActivity() {

    //Log tag
    val TAG = "HuaweiIdActivity"
    private var mAuthManager: HuaweiIdAuthService? = null
    private var mAuthParam: HuaweiIdAuthParams? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huaweiid)
        initListener()
        //sample log Please ignore
        addLogFragment()
    }

    private fun initListener() {
        hwid_signin.setOnClickListener(mOnClickListener)
        hwid_signout.setOnClickListener(mOnClickListener)
        hwid_signInCode.setOnClickListener(mOnClickListener)
    }

    private val mOnClickListener: View.OnClickListener = View.OnClickListener {
        when (it.id) {
            R.id.hwid_signin -> {
                signIn()
            }
            R.id.hwid_signout -> {
                signOut()
            }
            R.id.hwid_signInCode -> {
                signInCode()
            }
        }
    }

    /**
     * Codelab Code
     * Pull up the authorization interface by getSignInIntent
     */
    private fun signIn() {
        mAuthParam = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setAccessToken()
                .createParams()
        mAuthManager = HuaweiIdAuthManager.getService(this@HuaweiIdActivity, mAuthParam)
        startActivityForResult(mAuthManager?.signInIntent, Constant.REQUEST_SIGN_IN_LOGIN)
    }

    private fun signInCode() {
        mAuthParam = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setProfile()
                .setAuthorizationCode()
                .createParams()
        mAuthManager = HuaweiIdAuthManager.getService(this@HuaweiIdActivity, mAuthParam)
        startActivityForResult(mAuthManager?.signInIntent, Constant.REQUEST_SIGN_IN_LOGIN_CODE)
    }

    /**
     * Codelab Code
     * sign Out by signOut
     */
    private fun signOut() {
        val signOutTask = mAuthManager?.signOut()
        signOutTask?.let {
            it.addOnSuccessListener {
                Log.i(TAG, "signOut Success")
            }.addOnFailureListener {
                Log.i(TAG, "signOut fail")
            }
        }
    }

    /**
     * Codelab Code
     * Silent SignIn by silentSignIn
     */
    private fun silentSignIn() {
        val task = mAuthManager?.silentSignIn()
        task?.let {
            it.addOnSuccessListener { Log.i(TAG, "silentSignIn success") }
            it.addOnFailureListener { e ->
                //if Failed use getSignInIntent
                if (e is ApiException) {
                    val apiException = e
                    signIn()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            Constant.REQUEST_SIGN_IN_LOGIN -> {
                val authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
                if (authHuaweiIdTask.isSuccessful) {
                    val huaweiAccount = authHuaweiIdTask.result
                    Log.i(TAG, huaweiAccount.displayName + " signIn success ")
                    Log.i(TAG, "AccessToken: " + huaweiAccount.accessToken)
                } else {
                    Log.i(TAG, "signIn failed: " + (authHuaweiIdTask.exception as ApiException).statusCode)
                }
            }
            Constant.REQUEST_SIGN_IN_LOGIN_CODE ->{
                //login success
                val authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
                if (authHuaweiIdTask.isSuccessful) {
                    val huaweiAccount = authHuaweiIdTask.result
                    Log.i(TAG, "signIn get code success.")
                    Log.i(TAG, "ServerAuthCode: " + huaweiAccount.authorizationCode)
                    /**** english doc:For security reasons, the operation of changing the code to an AT must be performed on your server. The code is only an example and cannot be run.  */
                } else {
                    Log.i(TAG, "signIn get code failed: " + (authHuaweiIdTask.exception as ApiException).statusCode)
                }
            }
        }
    }

    /**
     * sample log Please ignore
     */
    private fun addLogFragment() {
        val transaction = fragmentManager.beginTransaction()
        val fragment = LogFragment()
        transaction.replace(R.id.framelog, fragment)
        transaction.commit()
    }
}