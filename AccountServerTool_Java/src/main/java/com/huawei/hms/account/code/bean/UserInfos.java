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
 * user infos
 *
 * @date 2021-04-22
 */
public class UserInfos {
    private String displayName;
    private String openId;
    private String headPictureURL;
    private String mobileNumber;
    private String srvNationalCode;
    private String nationalCode;
    private String birthDate;
    private int ageGroupFlag = Integer.MAX_VALUE;
    private String email;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getHeadPictureURL() {
        return headPictureURL;
    }

    public void setHeadPictureURL(String headPictureURL) {
        this.headPictureURL = headPictureURL;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSrvNationalCode() {
        return srvNationalCode;
    }

    public void setSrvNationalCode(String srvNationalCode) {
        this.srvNationalCode = srvNationalCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getAgeGroupFlag() {
        return ageGroupFlag;
    }

    public void setAgeGroupFlag(int ageGroupFlag) {
        this.ageGroupFlag = ageGroupFlag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfos{" +
                "displayName='" + displayName + '\'' +
                ", openId='" + openId + '\'' +
                ", headPictureURL='" + headPictureURL + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", srvNationalCode='" + srvNationalCode + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", ageGroupFlag='" + ageGroupFlag + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
