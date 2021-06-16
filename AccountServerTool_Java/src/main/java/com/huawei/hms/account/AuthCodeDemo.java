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
import com.huawei.hms.account.code.bean.*;

import java.io.IOException;

/**
 * AuthCodeUtil use sample
 *
 * @date 2021-04-29
 */
public class AuthCodeDemo {
    public static void main(String[] args) {
        /**
         * your code get from client
         */
        String code = "DQB6e3x9pVWE4a4GU8+4xnHj+7RmGfPA7NxVa+7Qp+bjFnZhcWn4dlvb4vL+w1N2sLEsfUVNzPpShxbNTFdKS8rCqFOMVInvEDMNre+QRFZGK1cS/OPPv4Qjg40zcnnOsSyERgzdpULD8vbdmVpI6M8uGlWXZ8JJS6r3HWR7531lt90xINwMo6VGPEe495TFjVQP7G1qUOlX2/jJJqDUpD9MTn8S2U2yAMJnodgB1C6XMNy5lgbDW/ogjr2fK+iEMO00DpzNpjOSsQCFRkvJoXxF37sKIrLREVYG/CZfAZBm94sPm2z5oQhsyUITozEKoL9BFU3cTZ4pRESqmFil/nIz8KkrdxRiCubil8Hx7tV5N4MjTipAnsE=";
        /**
         * your app id
         */
        String appId = "104114183";
        /**
         * your app secret
         */
        String appSecret = "6259f85df42ac1aa927627e541f5d03c448dd4bc1161b7383adf41f7e9f56f39";
        /**
         * your redirect uri
         */
        String redirectUri = "https://com.hxb.codemodeldemo";
        ResponseInfos tokensByCode;
        String accessToken = "";
        String refreshToken = "";
        /**
         * you can configurate getNickName parameter as 0/1
         */
        int getNickName = 0;

        try {
            //get access token and refresh token by code
            tokensByCode = AuthCodeUtil.getTokensByCode(code, appId, appSecret, redirectUri);
            JSONObject tokensByCodeRespBody = (JSONObject) JSON.parse(tokensByCode.getBody());
            //handle error code
            if (tokensByCode.getBody().contains(Constants.ERROR_FLAG)) {
                ErrorInfos errorInfos = tokensByCodeRespBody.toJavaObject(ErrorInfos.class);
                if (errorInfos.getError() == Constants.CODE_INVALID_ERROR
                        && (errorInfos.getSubError() == Constants.CODE_EXPIORE_SUBERROR
                        || errorInfos.getSubError() == Constants.CODE_IS_USED_SUBERROR)) {
                    System.out.println("code is invalid,please obtain it again!");
                    //TODO you need get code again and use new code to get new access token,refresh token

                } else {
                    System.out.println("get access token by code error:" + errorInfos.toString());
                }
            } else {
                TokensEntity tokensEntity = tokensByCodeRespBody.toJavaObject(TokensEntity.class);
                accessToken = tokensEntity.getAccessToken();
                refreshToken = tokensEntity.getRefreshToken();
            }
            //parse access token
            ResponseInfos accessTokenInfosResp = AuthCodeUtil.parseAccessToken(accessToken);
            //handle error code
            if (accessTokenInfosResp.getNspStatus() == Constants.ACCESS_TOKEN_EXPIRE_ERROR
                    || accessTokenInfosResp.getNspStatus() == Constants.ACCESS_TOKEN_INVALID_ERROR) {
                ResponseInfos updateAccessTokenResp = AuthCodeUtil.updateAccessToken(refreshToken, appId, appSecret);
                if (updateAccessTokenResp.getBody().contains(Constants.ERROR_FLAG)) {
                    ErrorInfos errorInfos = ((JSONObject) JSON.parse(updateAccessTokenResp.getBody())).toJavaObject(ErrorInfos.class);
                    //handle error code when refresh token invalids
                    if (errorInfos.getError() == Constants.REFRESH_TOKEN_INVALID_ERROR
                            && (errorInfos.getError() == Constants.REFRESH_TOKEN_EXPIRE_SUBERROR
                            || errorInfos.getError() == Constants.REFRESH_TOKEN_INVALID_SUBERROR)) {
                        System.out.println("refresh token is invalid,please obtain it again!");
                        //TODO you need get refresh token again,and refresh access token

                    } else {
                        System.out.println("update access token by refresh token error:" + errorInfos.toString());
                    }
                } else {
                    TokensEntity tokensEntity = ((JSONObject) JSON.parse(updateAccessTokenResp.getBody())).toJavaObject(TokensEntity.class);
                    accessToken = tokensEntity.getAccessToken();
                    accessTokenInfosResp = AuthCodeUtil.parseAccessToken(accessToken);
                }
            }

            if (!accessTokenInfosResp.getBody().contains(Constants.ERROR_FLAG)
                    && accessTokenInfosResp.getNspStatus() == Constants.NSP_STATUS_OK) {
                System.out.println("parse access token success!");
                JSONObject accessTokenInfo = (JSONObject) JSON.parse(accessTokenInfosResp.getBody());
                AccessTokenInfos accessTokenInfos = accessTokenInfo.toJavaObject(AccessTokenInfos.class);
                //TODO you can do what you want after you get access token infos

                System.out.println("UnionId:" + accessTokenInfos.getUnionId());
                System.out.println("Scope" + accessTokenInfos.getScope());

            } else {
                System.out.println("parse access token error," + accessTokenInfosResp.toString());
            }

            //get user infos
            ResponseInfos userInfosByAccessToken = AuthCodeUtil.getUserInfos(accessToken, getNickName);
            if (!userInfosByAccessToken.getBody().contains(Constants.ERROR_FLAG)
                    && userInfosByAccessToken.getNspStatus() == Constants.NSP_STATUS_OK) {
                System.out.println("get user infos by access token success!");
                JSONObject userInfosByAccessTokenRespBody = (JSONObject) JSON.parse(userInfosByAccessToken.getBody());
                UserInfos userInfos = userInfosByAccessTokenRespBody.toJavaObject(UserInfos.class);
                //TODO you can do what you want after you get user infos

                System.out.println("email:" + userInfos.getEmail());
                System.out.println("openId:" + userInfos.getOpenId());
            } else {
                System.out.println("get user infos by access token error," + userInfosByAccessToken.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
