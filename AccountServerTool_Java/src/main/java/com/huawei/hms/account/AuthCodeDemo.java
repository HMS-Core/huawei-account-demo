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

package com.huawei.hms.account;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huawei.hms.account.code.AuthCodeUtil;
import com.huawei.hms.account.code.Constants;
import com.huawei.hms.account.code.bean.ErrorInfos;
import com.huawei.hms.account.code.bean.ResponseInfos;
import com.huawei.hms.account.code.bean.TokensEntity;
import com.huawei.hms.account.code.bean.UserInfos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AuthCodeUtil use sample,you need to fill in the corresponding code in the corresponding method
 *
 * @date 2021-04-29
 */
public class AuthCodeDemo {
    private static final Logger logger = LoggerFactory.getLogger(AuthCodeDemo.class);
    /**
     * your app id
     */
    private static final String appId = "10436***3";
    /**
     * your app secret
     */
    private static final String appSecret = "600746***8784e5dc00106f956cdcb652136b658bff71d5910ba5d31d";
    /**
     * your redirect uri
     */
    private static final String redirectUri = "https://com.hxb.codemodeldemo1";
    /**
     * you can configurate getNickName parameter as 0/1
     */
    private static final int getNickName = 0;

    private static String accessToken = "";

    private static String refreshToken = "";

    public static void main(String[] args) {
        /**
         * your code get from client
         */
        String code = "DQB6e3x***rTEWKe4i7hNPSiZKZKjZFXm+UvGPJ1Pephy9UulKDhXbkz6omXzzMJFduZ7YTtfmQhd/iR1Q+bVr5b5BV63KyD3yJm3+74TaKOaFVh7yRRLi79m+BxUjhisdhHb2oZVMbO/jy7lvKdlvSwFDA7/DAn2GGEbKID2AGAeJw9ZztEXTEXiX4jbYpygOLwZIY4tsS9uVHCF9XrULinLKH89Zjd6f5o54suYpTP4FAdBAPTr2cA9n6UOQE3cCxFz/lZnRoDlifoK17psHOI1ojqdjji6AiKKz1EbPZEDJ7dulLa3S0q7xTygnXg95R9qowdskX2p6V7+tmKkQhBnCT137F6/2OAng==";
        getAccessToken(code, appId, appSecret, redirectUri);
        getUserInfos(getNickName);
    }

    private static void getAccessToken(String code, String appId, String appSecret, String redirectUri) {
        ResponseInfos tokensByCode = null;
        tokensByCode = AuthCodeUtil.getTokensByCode(code, appId, appSecret, redirectUri);
        if (tokensByCode == null) {
            logger.error("get tokens by code failed");
            return;
        }
        JSONObject tokensByCodeRespBody = (JSONObject) JSON.parse(tokensByCode.getBody());
        // handle error code
        if (tokensByCode.getBody().contains(Constants.ERROR_FLAG)) {
            ErrorInfos errorInfos = tokensByCodeRespBody.toJavaObject(ErrorInfos.class);
            if (errorInfos.getError() == Constants.CODE_INVALID_ERROR
                    && (errorInfos.getSubError() == Constants.CODE_EXPIORE_SUBERROR
                    || errorInfos.getSubError() == Constants.CODE_IS_USED_SUBERROR)) {
                logger.error("code is invalid,please obtain it again!");
                //TODO you need get code again and use new code to get new access token,refresh token

            } else {
                logger.error("get access token by code error:" + errorInfos.toString());
            }
        } else {
            TokensEntity tokensEntity = tokensByCodeRespBody.toJavaObject(TokensEntity.class);
            // proposal to persist
            accessToken = tokensEntity.getAccessToken();
            // proposal to persist
            refreshToken = tokensEntity.getRefreshToken();
        }
    }

    private static void getUserInfos(int getNickName) {
        ResponseInfos userInfosByAccessToken = AuthCodeUtil.getUserInfos(accessToken, getNickName);
        if (userInfosByAccessToken == null) {
            return;
        }
        if (handeAccessTokenExpire(userInfosByAccessToken, refreshToken, appId, appSecret)) {
            userInfosByAccessToken = AuthCodeUtil.getUserInfos(accessToken, getNickName);
        }
        if (!userInfosByAccessToken.getBody().contains(Constants.ERROR_FLAG)
                && userInfosByAccessToken.getNspStatus() == Constants.NSP_STATUS_OK) {
            logger.info("get user infos by access token success!");
            JSONObject userInfosByAccessTokenRespBody = (JSONObject) JSON.parse(userInfosByAccessToken.getBody());
            UserInfos userInfos = userInfosByAccessTokenRespBody.toJavaObject(UserInfos.class);
            System.out.println(userInfos.toString());
            //TODO you can do what you want after you get user infos

        } else {
            logger.error("get user infos by access token error," + userInfosByAccessToken.toString());
        }
    }

    private static boolean handeAccessTokenExpire(ResponseInfos responseInfos, String refreshToken, String appId, String appSecret) {
        if (responseInfos.getNspStatus() == Constants.ACCESS_TOKEN_EXPIRE_ERROR
                || responseInfos.getNspStatus() == Constants.ACCESS_TOKEN_INVALID_ERROR) {
            ResponseInfos updateAccessTokenResp = AuthCodeUtil.updateAccessToken(refreshToken, appId, appSecret);
            if (updateAccessTokenResp.getBody().contains(Constants.ERROR_FLAG)) {
                ErrorInfos errorInfos = ((JSONObject) JSON.parse(updateAccessTokenResp.getBody())).toJavaObject(ErrorInfos.class);
                // handle error code when refresh token invalid
                handleRefreshTokenExpire(errorInfos);
            } else {
                TokensEntity tokensEntity = ((JSONObject) JSON.parse(updateAccessTokenResp.getBody())).toJavaObject(TokensEntity.class);
                accessToken = tokensEntity.getAccessToken();
            }
            return true;
        }
        return false;
    }

    private static void handleRefreshTokenExpire(ErrorInfos errorInfos) {
        if (errorInfos.getError() == Constants.REFRESH_TOKEN_INVALID_ERROR
                && (errorInfos.getError() == Constants.REFRESH_TOKEN_EXPIRE_SUBERROR
                || errorInfos.getError() == Constants.REFRESH_TOKEN_INVALID_SUBERROR)) {
            logger.error("refresh token is invalid,please obtain it again!");
            //TODO you need get refresh token again,and refresh access token

        } else {
            logger.error("update access token by refresh token error:" + errorInfos.toString());
        }
    }
}
