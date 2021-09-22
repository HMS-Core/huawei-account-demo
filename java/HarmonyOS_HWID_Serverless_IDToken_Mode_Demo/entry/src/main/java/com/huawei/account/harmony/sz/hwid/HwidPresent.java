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

import com.huawei.hms.accountsdk.support.account.result.AuthAccount;

public interface HwidPresent {
    /**
     * 通过获取到的华为帐号对象，更新界面
     *
     * @param account 华为帐号对象
     */
    void updateUI(AuthAccount account);

    /**
     * 更新界面提示，用户可以自定义
     *
     * @param info 需要展示的信息
     */
    void showInfo(String info);
}
