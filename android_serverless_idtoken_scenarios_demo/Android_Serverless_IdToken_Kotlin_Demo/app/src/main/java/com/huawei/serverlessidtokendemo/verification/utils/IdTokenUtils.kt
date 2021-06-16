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
package com.huawei.serverlessidtokendemo.verification.utils

import android.text.TextUtils
import android.util.Base64
import android.util.Log
import com.auth0.jwk.InvalidPublicKeyException
import com.auth0.jwk.Jwk
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.huawei.serverlessidtokendemo.verification.beans.HeaderEntity
import com.huawei.serverlessidtokendemo.verification.beans.IdTokenEntity
import com.huawei.serverlessidtokendemo.verification.beans.PayloadEntity
import com.huawei.serverlessidtokendemo.verification.beans.VerifyBean
import com.huawei.serverlessidtokendemo.verification.interfaces.IVerifyCallBack
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPublicKeySpec
import java.util.*

class IdTokenUtils {
    private val TAG = "IdTokenUtils"
    private val PUBLIC_KEY_ALGORITHM = "RSA"
    val CERT_URL = "https://oauth-login.cloud.huawei.com/oauth2/v3/certs"
    val ID_TOKEN_ISSUE = "https://accounts.huawei.com"
    private var mJsonArray: JSONArray? = null

    /**
     * catch the public key in this map
     */
    private val keyId2PublicKey: MutableMap<String, RSAPublicKey> =
        HashMap()
    private val MAX_PUBLIC_KEY_SIZE = 4

    fun IdTokenUtils() {}

    /**
     * 从id Token中解码用户信息
     *
     * @param idToken idToken
     * @return IdTokenEntity idToken中的数据为空，或者不是合法的jwt格式会返回null
     */
    fun decodeJsonStringFromIdtoken(idToken: String): IdTokenEntity? {
        var idTokenEntity: IdTokenEntity? = null
        try {
            if (TextUtils.isEmpty(idToken)) {
                Log.w(TAG, "idToken is Empty")
                return null
            }
            val arrs = idToken.split("\\.".toRegex()).toTypedArray()
            if (arrs.size < 3) {
                Log.e(TAG, "\n" + "The idToken is malformed")
                return null
            }
            Log.i(TAG, "idToken header: " + arrs[0])
            Log.i(TAG, "idToken payload: " + arrs[1])
            val headerBytes =
                Base64.decode(arrs[0], Base64.URL_SAFE)
            val payloadBytes =
                Base64.decode(arrs[1], Base64.URL_SAFE)
            if (null == headerBytes || null == payloadBytes) {
                Log.e(TAG, "The idToken decode failed")
                return null
            }
            val gson = Gson()
            val headerJson =
                String(headerBytes, Charset.forName("utf-8"))
            Log.i(TAG, "headerJson: $headerJson")
            val payloadJson =
                String(payloadBytes, Charset.forName("utf-8"))
            Log.i(TAG, "payloadJson: $payloadJson")
            val headerEntity: HeaderEntity = gson.fromJson(headerJson, HeaderEntity::class.java)
            val payloadEntity: PayloadEntity = gson.fromJson(payloadJson, PayloadEntity::class.java)
            idTokenEntity = IdTokenEntity()
            idTokenEntity.setHeaderJson(headerJson)
            idTokenEntity.setPayloadJson(payloadJson)
            idTokenEntity.setHeaderEntity(headerEntity)
            idTokenEntity.setPayloadEntity(payloadEntity)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            Log.e(
                TAG,
                "decodeJsonStringFromIdtoken JsonSyntaxException e:" + e.message
            )
        }
        return idTokenEntity
    }

    /**
     * 认证解析后的idToken
     *
     * @param idTokenEntity
     * @return
     */
    fun validateIdToken(
        subInput: String,
        idToken: String,
        appId: String,
        idTokenEntity: IdTokenEntity,
        iCallBack: IVerifyCallBack
    ) {
        // 1 getJwks
        getJwks(object : IVerifyCallBack {
            override fun onSuccess() {
                // 2.验证签名，验证verifyToken中的过期时间（exp字段）是否已过期。
                val verifyRSAPublicKey: VerifyBean? =
                    idTokenEntity.getHeaderEntity()?.getKid()?.let { verifyRSAPublicKey(it, idToken) }
                if (verifyRSAPublicKey != null) {
                    if (!verifyRSAPublicKey.isSuccess()) {
                        iCallBack.onFailed("verifyRSAPublicKey error," + verifyRSAPublicKey.getMessage())
                        return
                    }
                }
                // 3. 验证verifyToken中iss字段的值是否等于“https://accounts.huawei.com”。
                val iss: String? = idTokenEntity.getPayloadEntity()?.getIss()
                val verifyIss = !TextUtils.isEmpty(iss) && iss == ID_TOKEN_ISSUE
                if (!verifyIss) {
                    iCallBack.onFailed("verifyIss error")
                    return
                }
                // 4. 验证verifyToken中aud字段的值等于应用的包名。
                val aud: String? = idTokenEntity.getPayloadEntity()?.getAud()
                val verifyAud = !TextUtils.isEmpty(aud) && aud == appId
                if (!verifyAud) {
                    iCallBack.onFailed("verifyAud error")
                    return
                }
                // 5、验证verifyToken中的用户（sub字段）是否正确。
                val sub: String? = idTokenEntity.getPayloadEntity()?.getSub()
                val verifySub = !TextUtils.isEmpty(sub) && sub == subInput
                if (!verifySub) {
                    iCallBack.onFailed("verifySub error")
                    return
                }
                iCallBack.onSuccess()
            }
            override fun onFailed(errorMsg: String?) {
                iCallBack.onFailed("getJwks error,errorMsg:$errorMsg")
            }
        })
    }

    /**
     * 验证签名
     *
     * @param keyId
     * @param idToken
     * @return
     */
    private fun verifyRSAPublicKey(keyId: String, idToken: String): VerifyBean {
        Log.d(TAG, "verifyRSAPublicKey keyId:$keyId")
        val mRSAPublicKey: RSAPublicKey?
        try {
            if (keyId2PublicKey[keyId] != null) {
                mRSAPublicKey = keyId2PublicKey[keyId]
            } else {
                if (keyId2PublicKey.size > MAX_PUBLIC_KEY_SIZE) {
                    keyId2PublicKey.clear()
                }
                Log.d(TAG, "mJsonArray:" + mJsonArray.toString())
                for (i in 0 until mJsonArray!!.length()) {
                    val kid = mJsonArray!!.getJSONObject(i).getString("kid")
                    Log.d(TAG, "mJsonArray kid:$kid")
                    keyId2PublicKey[kid] = getRsaPublicKeyByJwk(mJsonArray!!.getJSONObject(i))
                }
                Log.d(TAG, "keyId2PublicKey:$keyId2PublicKey")
                mRSAPublicKey = keyId2PublicKey[keyId]
            }
            if (mRSAPublicKey == null) {
                return VerifyBean(false, "mRSAPublicKey is null")
            }
            Jwts.parser().setSigningKey(mRSAPublicKey).parse(idToken)
        } catch (e: Exception) {
            return if (e is ExpiredJwtException) {
                VerifyBean(false, "verify exp error:" + e.message)
            } else {
                VerifyBean(false, "e:" + e.javaClass.simpleName + ", msg:" + e.message)
            }
        }
        return VerifyBean(true,"")
    }

    @Throws(InvalidPublicKeyException::class, JSONException::class)
    private fun getRsaPublicKeyByJwk(jwkObject: JSONObject): RSAPublicKey {
        val additionalAttributes: MutableMap<String, Any> =
            HashMap()
        additionalAttributes["n"] = jwkObject.getString("n")
        additionalAttributes["e"] = jwkObject.getString("e")
        val operations: List<String> =
            ArrayList()
        val jwk = Jwk(
            jwkObject.getString("kid"),
            jwkObject.getString("kty"),
            jwkObject.getString("alg"),
            jwkObject.getString("use"),
            operations,
            null,
            null,
            null,
            additionalAttributes
        )
        return getPublicKey(jwk) as RSAPublicKey
    }

    @Throws(InvalidPublicKeyException::class)
    fun getPublicKey(jwk: Jwk): PublicKey {
        if (!PUBLIC_KEY_ALGORITHM.equals(jwk.type, ignoreCase = true)) {
            throw InvalidPublicKeyException("The key is not of type RSA", null)
        }
        return try {
            val kf =
                KeyFactory.getInstance(PUBLIC_KEY_ALGORITHM)
            val modulus = BigInteger(
                1,
                Base64.decode(
                    jwk.additionalAttributes["n"] as String?,
                    Base64.URL_SAFE
                )
            )
            val exponent = BigInteger(
                1,
                Base64.decode(
                    jwk.additionalAttributes["e"] as String?,
                    Base64.URL_SAFE
                )
            )
            kf.generatePublic(RSAPublicKeySpec(modulus, exponent))
        } catch (e: InvalidKeySpecException) {
            throw InvalidPublicKeyException("Invalid public key", e)
        } catch (e: NoSuchAlgorithmException) {
            throw InvalidPublicKeyException("Invalid algorithm to generate key", e)
        }
    }

    /**
     * 1.验证JWT有效性
     *
     * @param iCallBack
     */
    private fun getJwks(iCallBack: IVerifyCallBack) {
        try {
            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .url(CERT_URL)
                .build()
            val call = okHttpClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i(TAG, "Get ID Token failed.")
                    iCallBack.onFailed("getJwks onFailure Get ID Token failed." + e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        try {
                            val res = response.body!!.string()
                            val jsonObject = JSONObject(res)
                            mJsonArray = jsonObject.getJSONArray("keys")
                            if (mJsonArray == null) {
                                iCallBack.onFailed("getJwks JsonArray is null failed.")
                                return
                            }
                            iCallBack.onSuccess()
                        } catch (e: NullPointerException) {
                            Log.i(TAG, "parse JsonArray failed." + e.message)
                            iCallBack.onFailed("getJwks parse JsonArray failed.")
                        } catch (e: JSONException) {
                            Log.i(TAG, "parse JsonArray failed." + e.message)
                            iCallBack.onFailed("getJwks parse JsonArray failed.")
                        } catch (e: IOException) {
                            Log.i(TAG, "parse JsonArray failed." + e.message)
                            iCallBack.onFailed("getJwks parse JsonArray failed.")
                        }
                    } else {
                        iCallBack.onFailed("getJwks onResponse failed." + response.message + "code:" + response.code)
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            iCallBack.onFailed("getJwks Exception error e:" + e.message)
        }
    }

}