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
 * error infos
 *
 * @date 2021-05-19
 */
public class ErrorInfos {
    private int error;
    private int subError;
    private String errorMessage;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getSubError() {
        return subError;
    }

    public void setSubError(int subError) {
        this.subError = subError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
