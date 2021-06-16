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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date 2021/1/6
 */
public class IdTokenEntity {

    private HeaderEntity headerEntity;
    private PayloadEntity payloadEntity;
    private String headerJson;
    private String payloadJson;

    public HeaderEntity getHeaderEntity() {
        return headerEntity;
    }

    public void setHeaderEntity(HeaderEntity headerEntity) {
        this.headerEntity = headerEntity;
    }

    public PayloadEntity getPayloadEntity() {
        return payloadEntity;
    }

    public void setPayloadEntity(PayloadEntity payloadEntity) {
        this.payloadEntity = payloadEntity;
    }

    public String getHeaderJson() {
        return headerJson;
    }

    public void setHeaderJson(String headerJson) {
        this.headerJson = headerJson;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }

    @Override
    public String toString() {
        return "header:\n" + headerJson + '\n' +
                "payload:\n" + payloadJson + '\n';
    }

    public String getExpTime(){
        if(getPayloadEntity()==null){
            return "";
        }
        long exp = getPayloadEntity().getExp()* 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expTime = simpleDateFormat.format(new Date(exp));
        return expTime;
    }

    public String getIatTime(){
        if(getPayloadEntity()==null){
            return "";
        }
        long iat = getPayloadEntity().getIat()* 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String iatTime = simpleDateFormat.format(new Date(iat));
        return iatTime;
    }
}
