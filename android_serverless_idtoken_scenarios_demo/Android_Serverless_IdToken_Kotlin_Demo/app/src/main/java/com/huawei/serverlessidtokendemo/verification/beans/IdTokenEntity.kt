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

import java.text.SimpleDateFormat
import java.util.*

class IdTokenEntity {
    private var headerEntity: HeaderEntity? = null
    private var payloadEntity: PayloadEntity? = null
    private var headerJson: String? = null
    private var payloadJson: String? = null

    fun getHeaderEntity(): HeaderEntity? {
        return headerEntity
    }

    fun setHeaderEntity(headerEntity: HeaderEntity?) {
        this.headerEntity = headerEntity
    }

    fun getPayloadEntity(): PayloadEntity? {
        return payloadEntity
    }

    fun setPayloadEntity(payloadEntity: PayloadEntity?) {
        this.payloadEntity = payloadEntity
    }

    fun getHeaderJson(): String? {
        return headerJson
    }

    fun setHeaderJson(headerJson: String?) {
        this.headerJson = headerJson
    }

    fun getPayloadJson(): String? {
        return payloadJson
    }

    fun setPayloadJson(payloadJson: String?) {
        this.payloadJson = payloadJson
    }

    override fun toString(): String {
        return """
            header:
            $headerJson
            payload:
            $payloadJson
            """.trimIndent()
    }

    fun getExpTime(): String? {
        if (getPayloadEntity() == null) {
            return ""
        }
        val exp: Long? = getPayloadEntity()?.getExp()?.times(1000)
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return simpleDateFormat.format(exp?.let { Date(it) })
    }

    fun getIatTime(): String? {
        if (getPayloadEntity() == null) {
            return ""
        }
        val iat: Long? = getPayloadEntity()?.getIat()?.times(1000)
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return simpleDateFormat.format(iat?.let { Date(it) })
    }
}