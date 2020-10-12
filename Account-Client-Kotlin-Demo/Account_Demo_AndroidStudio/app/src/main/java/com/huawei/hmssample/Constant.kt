package com.huawei.hmssample


object Constant {
   const val IS_LOG = 1
    //login
    const val REQUEST_SIGN_IN_LOGIN = 1002
    //login by code
    const val REQUEST_SIGN_IN_LOGIN_CODE = 1003
    /**
     * your app’s client ID,please replace it of yours
     */
    const val CLIENT_ID = "101090009"
    /**
     * JWK JSON Web Key endpoint, developer can get the JWK of the last two days from this endpoint
     * See more about JWK in http://self-issued.info/docs/draft-ietf-jose-json-web-key.html
     */
    const val CERT_URL = "https://oauth-login.cloud.huawei.com/oauth2/v3/certs"
    /**
     * Id Token issue
     */
    const val ID_TOKEN_ISSUE = "https://accounts.huawei.com"
}