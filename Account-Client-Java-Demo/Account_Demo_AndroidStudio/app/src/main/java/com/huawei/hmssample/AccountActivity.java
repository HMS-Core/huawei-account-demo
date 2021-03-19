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
package com.huawei.hmssample;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.logger.Log;
import com.huawei.logger.LoggerActivity;

/**
 * Codelab
 * Demonstration of HuaweiId
 */
public class AccountActivity extends LoggerActivity implements OnClickListener {

    //Log tag
    public static final String TAG = "HuaweiIdActivity";
    private AccountAuthService mAuthManager;
    private AccountAuthParams mAuthParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        findViewById(R.id.account_signin).setOnClickListener(this);
        findViewById(R.id.account_signout).setOnClickListener(this);
        findViewById(R.id.account_signInCode).setOnClickListener(this);
        findViewById(R.id.account_silent_signin).setOnClickListener(this);
        findViewById(R.id.cancel_authorization).setOnClickListener(this);
        //sample log Please ignore
        addLogFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Codelab Code
     * Pull up the authorization interface by getSignInIntent
     */
    private void signIn() {
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setAccessToken()
                .createParams();
        mAuthManager = AccountAuthManager.getService(AccountActivity.this, mAuthParam);
        startActivityForResult(mAuthManager.getSignInIntent(), Constant.REQUEST_SIGN_IN_LOGIN);
    }

    private void signInCode() {
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setProfile()
                .setAuthorizationCode()
                .createParams();
        mAuthManager = AccountAuthManager.getService(AccountActivity.this, mAuthParam);
        startActivityForResult(mAuthManager.getSignInIntent(), Constant.REQUEST_SIGN_IN_LOGIN_CODE);
    }

    /**
     * Codelab Code
     * sign Out by signOut
     */
    private void signOut() {
        Task<Void> signOutTask = mAuthManager.signOut();
        signOutTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "signOut Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "signOut fail");
            }
        });
    }

    /**
     * Codelab Code
     * Silent SignIn by silentSignIn
     */
    private void silentSignIn() {
        Task<AuthAccount> task = mAuthManager.silentSignIn();
        task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
            @Override
            public void onSuccess(AuthAccount authAccount) {
                Log.i(TAG, "silentSignIn success");
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                //if Failed use getSignInIntent
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    signIn();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_signin:
                signIn();
                break;
            case R.id.account_signout:
                signOut();
                break;
            case R.id.account_signInCode:
                signInCode();
                break;
            case R.id.account_silent_signin:
                silentSignIn();
                break;
            case R.id.cancel_authorization:
                cancelAuthorization();
                break;
            default:
                break;
        }
    }

    private void cancelAuthorization() {
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setProfile()
                .setAuthorizationCode()
                .createParams();
        mAuthManager = AccountAuthManager.getService(AccountActivity.this, mAuthParam);
        Task<Void> task = mAuthManager.cancelAuthorization();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "cancelAuthorization success");
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "cancelAuthorization failure：" + e.getClass().getSimpleName());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN) {
            //login success
            //get user message by parseAuthResultFromIntent
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                AuthAccount authAccount = authAccountTask.getResult();
                Log.i(TAG, authAccount.getDisplayName() + " signIn success ");
                Log.i(TAG, "AccessToken: " + authAccount.getAccessToken());
            } else {
                Log.i(TAG, "signIn failed: " + ((ApiException) authAccountTask.getException()).getStatusCode());
            }
        }
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN_CODE) {
            //login success
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                AuthAccount authAccount = authAccountTask.getResult();
                Log.i(TAG, "signIn get code success.");
                Log.i(TAG, "ServerAuthCode: " + authAccount.getAuthorizationCode());

                /**** english doc:For security reasons, the operation of changing the code to an AT must be performed on your server. The code is only an example and cannot be run. ****/
                /**********************************************************************************************/
            } else {
                Log.i(TAG, "signIn get code failed: " + ((ApiException) authAccountTask.getException()).getStatusCode());
            }
        }
    }

    /**
     * sample log Please ignore
     */
    private void addLogFragment() {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        final LogFragment fragment = new LogFragment();
        transaction.replace(R.id.framelog, fragment);
        transaction.commit();
    }

}
