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
package com.huawei.serverlessidtokendemo.verification.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @date 2021/1/6
 */
public class PayloadEntity implements Serializable {

    /**
     * 签发人,固定值https://accounts.huawei.com
     */
    @SerializedName("iss")
    private String iss;
    /**
     * 主题,unionid，对同一个开发者下的所有应用是相同的
     */
    @SerializedName("sub")
    private String sub;
    /**
     * 受众
     */
    @SerializedName("aud")
    private String aud;
    /**
     * 过期时间 idToken过期时间 1970-01-01 00：00：00 的秒数
     */
    @SerializedName("exp")
    private Long exp;
    /**
     * id_token生成时间 1970-01-01 00：00：00 的秒数
     */
    @SerializedName("iat")
    private Long iat;

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    /**
     * @return 调试时使用， 包含敏感信息
     */

    @Override
    public String toString() {
        return "PayloadEntity{" +
                "iss='" + iss + '\'' +
                ", sub='" + sub + '\'' +
                ", aud='" + aud + '\'' +
                ", exp=" + exp +
                ", iat=" + iat +
                '}';
    }
}
