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

package com.huawei.account.harmony.sz.slice;

import com.huawei.account.harmony.sz.ResourceTable;
import com.huawei.account.harmony.sz.hwid.HuaweiAccountSDKProxy;
import com.huawei.account.harmony.sz.hwid.HwidPresent;
import com.huawei.hms.accountsdk.constant.AccountSdkConstant;
import com.huawei.hms.accountsdk.support.account.result.AuthAccount;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbilitySlice extends AbilitySlice implements HwidPresent {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, AccountSdkConstant.LOG_DOMAIN, "[AccountHos]" + "HuaweiAccountSDKProxy");;
    Text mTxtInfoShow;

    Button mBtnHuaweiIdSignIn;

    Button mBtnHuaweiIdSignOut;

    Button mBtnHuaweiIdCancelAuthorization;

    Button mBtnClearLog;

    HuaweiAccountSDKProxy huaweiAccountSDKProxy;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        huaweiAccountSDKProxy = new HuaweiAccountSDKProxy(this);
        mTxtInfoShow = (Text) findComponentById(ResourceTable.Id_txt_info_show);

        initHuaweiLoginButtons();

        mBtnClearLog = (Button) findComponentById(ResourceTable.Id_btn_hwid_clear);
        mBtnClearLog.setClickedListener((Component c) -> {
            mTxtInfoShow.setText("");
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    public void updateUI(AuthAccount account) {
        if (account != null) {
            appendInfo("****** [Update UI START][Huawei Account Info] ******");
            appendInfo("** Head Picture: " + account.getAvatarUriString());
            appendInfo("** Union Id: " + account.getUnionId());
            appendInfo("** Open Id: " + account.getOpenId());
            appendInfo("** Email: " + account.getEmail());
            appendInfo("** Nick Name: " + account.getDisplayName());
            appendInfo("** Authorization Code: " + account.getAuthorizationCode());
            appendInfo("****** [UPDATE UI END][Huawei Account Info] ******");
        }
    }

    @Override
    public void showInfo(String info) {
        appendInfo(info);
        HiLog.info(LABEL_LOG, info);
    }

    private void initHuaweiLoginButtons() {
        // 初始化华为帐号登录按钮
        mBtnHuaweiIdSignIn = (Button) findComponentById(ResourceTable.Id_btn_hwid_sign_in);
        mBtnHuaweiIdSignIn.setClickedListener((Component c) -> {
            huaweiAccountSDKProxy.signIn();
        });

        // 初始化华为帐号登出按钮
        mBtnHuaweiIdSignOut = (Button) findComponentById(ResourceTable.Id_btn_hwid_sign_out);
        mBtnHuaweiIdSignOut.setClickedListener((Component c) -> {
            huaweiAccountSDKProxy.signOut();
        });

        // 初始化华为帐号取消授权按钮
        mBtnHuaweiIdCancelAuthorization = (Button) findComponentById(ResourceTable.Id_btn_hwid_cancel_authorization);
        mBtnHuaweiIdCancelAuthorization.setClickedListener((Component c) -> {
            huaweiAccountSDKProxy.cancelAuthorization();
        });
    }

    private void appendInfo(String info) {
        mTxtInfoShow.setText(info  + "\n" + mTxtInfoShow.getText() );
    }
}