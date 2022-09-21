package com.example.harmonyjssdk;

import com.huawei.hms.jsb.adapter.har.bridge.HmsBridge;
import ohos.aafwk.ability.AbilityPackage;

public class MyApplication extends AbilityPackage {
    private HmsBridge mHmsBridge;
    @Override
    public void onInitialize() {
        // 注册HMS Core服务
        mHmsBridge = HmsBridge.getInstance();
        mHmsBridge.initBridge(this);
        super.onInitialize();
    }

    @Override
    public void onEnd() {
        // 注销HMS Core服务
        mHmsBridge.destoryBridge();
        super.onEnd();
    }
}
