/*
 * Copyright 2021. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.account.harmony.sz.hwid;

import com.huawei.hms.accountsdk.constant.CommonConstant;
import com.huawei.hms.accountsdk.exception.ApiException;
import com.huawei.hms.accountsdk.support.account.AccountAuthManager;
import com.huawei.hms.accountsdk.support.account.request.AccountAuthParams;
import com.huawei.hms.accountsdk.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.accountsdk.support.account.result.AuthAccount;
import com.huawei.hms.accountsdk.support.account.service.AccountAuthService;
import com.huawei.hms.accountsdk.support.account.tasks.OnFailureListener;
import com.huawei.hms.accountsdk.support.account.tasks.OnSuccessListener;
import com.huawei.hms.accountsdk.support.account.tasks.Task;
import ohos.aafwk.ability.AbilityPackage;

/**
 * 华为帐号SDK代理类，所有与华为帐号相关的方法都通过此类进行封装处理。
 * 包括华为帐号登录、退出、取消授权三个方法
 */
public final class HuaweiAccountSDKProxy {
    // 用来更新界面的present接口类
    private HwidPresent hwidPresent;

    public HuaweiAccountSDKProxy() {
        super();
    }

    public HuaweiAccountSDKProxy(HwidPresent hwidPresent) {
        super();
        this.hwidPresent = hwidPresent;
    }

    // 示例：此方法中调用华为帐号SDK的初始化方法AccountAuthManager.init()进行初始化
    public void initHuaweiAccountSDK(AbilityPackage abilityPackage) {
        Task<Void> task;
        try {
            showInfo("InitHuaweiAccountSDK Start");
            // 调用AccountAuthManager.init方法初始化
            task = AccountAuthManager.init(abilityPackage);
        } catch (ApiException apiException) {
            showInfo("InitHuaweiAccountSDK exception: "
                    + apiException.getStatusCode()
                    + "; message: "
                    + apiException.getStatusMessage());
            apiException.getStatusCode();
            return;
        }
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                //初始化成功
                showInfo("initHuaweiAccountSDK SUCCESS.");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // SDK初始化失败
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    // SDK初始化失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
                    apiException.getStatusCode();
                    showInfo("InitHuaweiAccountSDK FAILED. statusCode: "
                            + apiException.getStatusCode()
                            + "; message: "
                            + apiException.getStatusMessage());
                }
            }
        });
    }

    /**
     * 先静默登录，如果静默登录成功，则从成功回调方法中获取AuthAccount用户信息，此时用户静默登录成功，获取到用户信息，登录流程结束；
     * 如果静默登录失败，则在失败回调方法中，显示调用前台登录授权signIn接口，进行登录授权。
     * 调用前台登录授权signIn接口：
     * 如果前台登录授权接口执行成功，在前台登录授权signIn接口的成功回调方法中获取AuthAccount用户信息，登录流程结束。
     * 如果前台登录授权接口执行失败，在前台登录授权signIn接口的失败回调方法中，可以获取到ApiException异常对象，从statsCode可以看出错误原因。
     * 一般有用户点击取消授权按钮，用户取消登录等原因，详细statusCode说明请参考API参考的错误码章节。
     */
    public void signIn() {
        showInfo("Init Huawei account auth service START.");
        AccountAuthService accountAuthService;
        // 1、配置登录请求参数AccountAuthParams，包括请求用户id(openid、unionid)、email、profile（昵称、头像）等。
        // 2、DEFAULT_AUTH_REQUEST_PARAM默认包含了id和profile（昵称、头像）的请求。
        // 3、如需要再获取用户邮箱，需要setEmail()。
        // 4、如需要获取其他受限信息，如国家和地区，则需要先申请scope，再设置请求参数。
        // 5、通过setIdToken()方法，设置IdToken模式。
        AccountAuthParams accountAuthParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setEmail()
                .createParams();
        try {
            accountAuthService = AccountAuthManager.getService(accountAuthParams);
        } catch (ApiException e) {
            // 处理初始化登录授权服务失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
            e.getStatusCode();
            showInfo("Init Huawei accountAuthService FAILED.");
            return;
        }
        showInfo("Init Huawei accountAuthService SUCCESS");

        // 调用静默登录接口。
        // 如果华为系统帐号已经登录，并且已经授权，会登录成功；
        // 否则静默登录失败，需要在失败监听中，显式地调用前台登录授权接口，完成登录授权。
        Task<AuthAccount> taskSilentSignIn = accountAuthService.silentSignIn();
        showInfo("SilentSign START.");
        // 添加静默登录成功处理监听
        taskSilentSignIn.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
            @Override
            public void onSuccess(AuthAccount authAccount) {
                showInfo("SilentSignIn SUCCESS");
                // 静默登录成功后，根据结果中获取到的帐号基本信息更新UI，authAccount.getIdToken()可以获取IdToken
                updateUI(authAccount);
            }
        });
        // 添加静默登录失败监听
        taskSilentSignIn.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    showInfo("SilentSignIn FAILED, status code: " + apiException.getStatusCode() + ". Need to foreground sign in");

                    // 静默登录失败，显式地调用前台登录授权接口，完成登录授权。
                    Task<AuthAccount> taskSignIn = accountAuthService.signIn();
                    showInfo("SignIn foreground START.");
                    if (taskSignIn == null) {
                        showInfo("SignIn foreground task is null.");
                        return;
                    }
                    taskSignIn.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
                        @Override
                        public void onSuccess(AuthAccount result) {
                            showInfo("SignIn foreground SUCCESS.");
                            updateUI(result);
                        }
                    });
                    taskSignIn.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            showInfo("SignIn foreground FAILED.");
                            if (e instanceof ApiException) {
                                ApiException apiException = (ApiException) e;
                                // 登录失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
                                apiException.getStatusCode();
                                showInfo("SignIn foreground FAILED. status code: "
                                        + apiException.getStatusCode()
                                        + ".");
                                if (CommonConstant.RETCODE.SIGN_IN_CANCELLED == apiException.getStatusCode()) {
                                    showInfo("Error message: User click CANCEL or Return, user cancel login in.");
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 示例：华为帐号退出
     */
    public void signOut() {
        AccountAuthService accountAuthService = null;
        AccountAuthParams accountAuthParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setEmail()
                .createParams();
        try {
            accountAuthService = AccountAuthManager.getService(accountAuthParams);
        } catch (ApiException apiException) {
            // 处理初始化登录授权服务失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
            apiException.getStatusCode();
            showInfo("Account service init FAILED. status code: " + apiException.getStatusCode());
            return;
        }

        Task<Void> task = accountAuthService.signOut();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                // 华为帐号退出成功，接入应用处理登出后逻辑
                showInfo("Account sign out SUCCESS.");
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // 华为帐号退出失败
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    // 华为帐号退出失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
                    apiException.getStatusCode();
                    showInfo("Account sign out FAILED. status code: " + apiException.getStatusCode());
                }
            }
        });
    }

    /**
     * 示例：华为帐号取消授权，取消授权后，再次静默登录将拉起授权页面
     */
    public void cancelAuthorization() {
        AccountAuthService accountAuthService;
        AccountAuthParams accountAuthParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setEmail()
                .createParams();
        try {
            accountAuthService = AccountAuthManager.getService(accountAuthParams);
        } catch (ApiException e) {
            // 处理初始化登录授权服务失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
            e.getStatusCode();
            return;
        }
        // 调用取消授权接口
        Task<Void> task = accountAuthService.cancelAuthorization();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                // 取消授权成功
                showInfo("Cancel authorization SUCCESS.");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // 取消授权失败
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    // 华为帐号取消授权失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
                    apiException.getStatusCode();
                    showInfo("Cancel authorization FAILED. status code: " + apiException.getStatusCode());
                }
            }
        });
    }

    private void updateUI(AuthAccount account) {
        if (hwidPresent != null) {
            hwidPresent.updateUI(account);
        }
    }

    private void showInfo(String info) {
        if (hwidPresent != null) {
            hwidPresent.showInfo(info);
        }
    }

}
