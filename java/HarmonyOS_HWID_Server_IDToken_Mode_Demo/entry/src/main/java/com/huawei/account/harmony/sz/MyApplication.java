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

package com.huawei.account.harmony.sz;

import com.huawei.account.harmony.sz.hwid.HuaweiAccountSDKProxy;
import ohos.aafwk.ability.AbilityPackage;

public class MyApplication extends AbilityPackage {

    @Override
    public void onInitialize() {
        super.onInitialize();
        // 调用示例initHuaweiAccountSDK方法，在鸿蒙应用初始化方法OnInitialize中进行华为帐号SDK初始化
        new HuaweiAccountSDKProxy().initHuaweiAccountSDK(this);
    }
}
