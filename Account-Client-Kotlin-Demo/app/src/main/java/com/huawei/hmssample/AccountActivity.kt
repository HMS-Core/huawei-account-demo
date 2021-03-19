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
import com.huawei.hms.support.account.AccountAuthManager
import com.huawei.hms.support.account.request.AccountAuthParams
import com.huawei.hms.support.account.request.AccountAuthParamsHelper
import com.huawei.hms.support.account.service.AccountAuthService
import com.huawei.logger.Log
import com.huawei.logger.LoggerActivity
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : LoggerActivity() {
    //Log tag
    val TAG = "HuaweiIdActivity"
    private var mAuthManager: AccountAuthService? = null
    private var mAuthParam: AccountAuthParams? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        account_signin.setOnClickListener(mOnClickListener)
        account_signout.setOnClickListener(mOnClickListener)
        account_signInCode.setOnClickListener(mOnClickListener)
        account_silent_signin.setOnClickListener(mOnClickListener)
        cancel_authorization.setOnClickListener(mOnClickListener)
        //sample log Please ignore
        addLogFragment()
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Codelab Code
     * Pull up the authorization interface by getSignInIntent
     */
    private fun signIn() {
        mAuthParam = AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setAccessToken()
                .createParams()
        mAuthManager = AccountAuthManager.getService(this@AccountActivity, mAuthParam)
        startActivityForResult(mAuthManager?.signInIntent, Constant.REQUEST_SIGN_IN_LOGIN)
    }

    private fun signInCode() {
        mAuthParam = AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setProfile()
                .setAuthorizationCode()
                .createParams()
        mAuthManager = AccountAuthManager.getService(this@AccountActivity, mAuthParam)
        startActivityForResult(mAuthManager?.signInIntent, Constant.REQUEST_SIGN_IN_LOGIN_CODE)
    }

    /**
     * Codelab Code
     * sign Out by signOut
     */
    private fun signOut() {
        val signOutTask = mAuthManager?.signOut()
        signOutTask?.addOnSuccessListener {
            Log.i(TAG, "signOut Success")
        }?.addOnFailureListener {
            Log.i(TAG, "signOut fail")
        }
    }

    /**
     * Codelab Code
     * Silent SignIn by silentSignIn
     */
    private fun silentSignIn() {
        val task = mAuthManager?.silentSignIn()
        task?.addOnSuccessListener { Log.i(TAG, "silentSignIn success") }
        task?.addOnFailureListener { e ->
            //if Failed use getSignInIntent
            if (e is ApiException) {
                val apiException = e
                signIn()
            }
        }
    }

    private val mOnClickListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.account_signin -> signIn()
                R.id.account_signout -> signOut()
                R.id.account_signInCode -> signInCode()
                R.id.account_silent_signin -> silentSignIn()
                R.id.cancel_authorization -> cancelAuthorization()
            }
        }
    }

    private fun cancelAuthorization() {
        mAuthParam = AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setProfile()
                .setAuthorizationCode()
                .createParams()
        mAuthManager = AccountAuthManager.getService(this@AccountActivity, mAuthParam)
        val task = mAuthManager?.cancelAuthorization()
        task?.addOnSuccessListener { Log.i(TAG, "cancelAuthorization success") }
        task?.addOnFailureListener { e -> Log.i(TAG, "cancelAuthorization failure：" + e.javaClass.simpleName) }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN) {
            //login success
            //get user message by parseAuthResultFromIntent
            val authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data)
            if (authAccountTask.isSuccessful) {
                val authAccount = authAccountTask.result
                Log.i(TAG, authAccount.getDisplayName() + " signIn success ")
                Log.i(TAG, "AccessToken: " + authAccount.getAccessToken())
            } else {
                Log.i(TAG, "signIn failed: " + (authAccountTask.exception as ApiException).statusCode)
            }
        }
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN_CODE) {
            //login success
            val authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data)
            if (authAccountTask.isSuccessful) {
                val authAccount = authAccountTask.result
                Log.i(TAG, "signIn get code success.")
                Log.i(TAG, "ServerAuthCode: " + authAccount.authorizationCode)
                /**** english doc:For security reasons, the operation of changing the code to an AT must be performed on your server. The code is only an example and cannot be run.  */
                /** */
            } else {
                Log.i(TAG, "signIn get code failed: " + (authAccountTask.exception as ApiException).statusCode)
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