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
public class HeaderEntity implements Serializable {

    @SerializedName("kid")
    private String kid;

    /**
     * 这个令牌（token）的类型（type），JWT 令牌统一写为JWT。
     */
    @SerializedName("typ")
    private String typ;

    /**
     * 签名的算法（algorithm），默认是 HMAC SHA256（写成 HS256）
     */
    @SerializedName("alg")
    private String alg;

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    @Override
    public String toString() {
        return "HeaderEntity{" +
                "kid='" + kid + '\'' +
                ", typ='" + typ + '\'' +
                ", alg='" + alg + '\'' +
                '}';
    }
}
