/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

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

package com.huawei.hms.account.code.bean;

/**
 * return infos
 *
 * @date 2021-05-19
 */
public class ResponseInfos {
    private String body;
    private int nspStatus;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getNspStatus() {
        return nspStatus;
    }

    public void setNspStatus(int nspStatus) {
        this.nspStatus = nspStatus;
    }

    @Override
    public String toString() {
        return "ResponseInfos{" +
                "body='" + body + '\'' +
                ", nspStatus=" + nspStatus +
                '}';
    }
}
