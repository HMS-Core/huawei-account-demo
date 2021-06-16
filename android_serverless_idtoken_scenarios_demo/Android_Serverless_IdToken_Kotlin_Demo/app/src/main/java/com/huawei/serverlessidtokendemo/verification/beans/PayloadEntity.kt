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
package com.huawei.serverlessidtokendemo.verification.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PayloadEntity : Serializable {

    /**
     * 签发人,固定值https://accounts.huawei.com
     */
    @SerializedName("iss")
    private var iss: String? = null

    /**
     * 主题,unionid，对同一个开发者下的所有应用是相同的
     */
    @SerializedName("sub")
    private var sub: String? = null

    /**
     * 受众
     */
    @SerializedName("aud")
    private var aud: String? = null

    /**
     * 过期时间 idToken过期时间 1970-01-01 00：00：00 的秒数
     */
    @SerializedName("exp")
    private var exp: Long? = null

    /**
     * id_token生成时间 1970-01-01 00：00：00 的秒数
     */
    @SerializedName("iat")
    private var iat: Long? = null

    fun getIss(): String? {
        return iss
    }

    fun setIss(iss: String?) {
        this.iss = iss
    }

    fun getSub(): String? {
        return sub
    }

    fun setSub(sub: String?) {
        this.sub = sub
    }

    fun getAud(): String? {
        return aud
    }

    fun setAud(aud: String?) {
        this.aud = aud
    }

    fun getExp(): Long? {
        return exp
    }

    fun setExp(exp: Long?) {
        this.exp = exp
    }

    fun getIat(): Long? {
        return iat
    }

    fun setIat(iat: Long?) {
        this.iat = iat
    }

    /**
     * @return 调试时使用， 包含敏感信息
     */
    override fun toString(): String {
        return "PayloadEntity{" +
                "iss='" + iss + '\'' +
                ", sub='" + sub + '\'' +
                ", aud='" + aud + '\'' +
                ", exp=" + exp +
                ", iat=" + iat +
                '}'
    }
}