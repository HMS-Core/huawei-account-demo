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

package com.huawei.hms.account.code;

/**
 * Constants
 *
 * @date 2021-05-21
 */
public class Constants {
    /**
     * error flag
     */
    public static final String ERROR_FLAG = "error";
    /**
     * nsp status ok
     */
    public static final int NSP_STATUS_OK = 0;
    /**
     * access token expire error code
     */
    public static final int ACCESS_TOKEN_EXPIRE_ERROR = 6;
    /**
     * access token is invalid error code
     */
    public static final int ACCESS_TOKEN_INVALID_ERROR = 102;
    /**
     * Code is invalid error code
     */
    public static final int CODE_INVALID_ERROR = 1101;
    /**
     * Code expire suberror code
     */
    public static final int CODE_EXPIORE_SUBERROR = 20155;
    /**
     * Code is used suberror code
     */
    public static final int CODE_IS_USED_SUBERROR = 20156;
    /**
     * refresh token invalid error code
     */
    public static final int REFRESH_TOKEN_INVALID_ERROR = 1203;
    /**
     * refresh token expire suberror code
     */
    public static final int REFRESH_TOKEN_EXPIRE_SUBERROR = 11205;
    /**
     * refresh token invalid suberror code
     */
    public static final int REFRESH_TOKEN_INVALID_SUBERROR = 31204;
}
