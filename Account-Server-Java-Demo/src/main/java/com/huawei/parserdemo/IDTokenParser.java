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

package com.huawei.parserdemo;

import java.nio.charset.Charset;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Function Description
 * This is a demo for verify Id Token issued by HuaWei OAuth Server
 * The demo just show how to verify Id Token in your local server
 * The open source software depended by this demo may have vulnerabilities,
 * please refer to the open source software release website and update to
 * the latest version or replace it with other open source software.
 * Local validation is much more efficiently than by access the tokeninfo endpoint
 * You'd better learn more about the JWT and JWK for understanding this demo
 * See more about JWT in <a href="https://jwt.io/">...</a>
 * See more about JWK in <a href="http://self-issued.info/docs/draft-ietf-jose-json-web-key.html">...</a>
 */
public class IDTokenParser {

    /**
     * your app’s client ID,please replace it of yours
     */
    private final static String CLIENT_ID = "101484027";

    private final static int MAX_PUBLIC_KEY_SIZE = 4;

    private final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * catch the public key in this map
     */
    private final Map<String,RSAPublicKey> keyId2PublicKey = new HashMap<String, RSAPublicKey>();

    /**
     * JWK JSON Web Key endpoint, developer can get the JWK of the last two days from this endpoint
     * See more about JWK in <a href="http://self-issued.info/docs/draft-ietf-jose-json-web-key.html">...</a>
     */
    private static final String CERT_URL = "https://oauth-login.cloud.huawei.com/oauth2/v3/certs";

    /**
     *  Id Token issue
     */
    public static final String ID_TOKEN_ISSUE = "https://accounts.huawei.com";

    /**
     * verify id token
     */
    public String verify(String idToken) throws InvalidPublicKeyException {
        try {
            DecodedJWT decoder = JWT.decode(idToken);
            Algorithm algorithm = Algorithm.RSA256(getRSAPublicKeyByKid(decoder.getKeyId()) , null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            JSONObject jsonObject = JSONObject.parseObject(new String(Base64.decodeBase64(decoder.getPayload())));

            // Verify the value of iss
            if (!decoder.getIssuer().equals(ID_TOKEN_ISSUE)) {
                return null;
            }

            // Verify your app’s client ID.
            if(decoder.getAudience().size() > 0) {
                if (!decoder.getAudience().get(0).equals(CLIENT_ID)) {
                    return null;
                }
            }

            // verify signature
            verifier.verify(decoder);
            jsonObject.put("alg", decoder.getAlgorithm());
            jsonObject.put("typ", decoder.getType());
            jsonObject.put("kid", decoder.getKeyId());
            return jsonObject.toJSONString();
        } catch (JWTDecodeException e){
                return null;
        } catch (TokenExpiredException e) {
            // jwt token is expire
            return null;
        } catch (JWTVerificationException e) {
            // VERIFY SIGNATURE failed
            return null;
        }
    }

    /**
     * get the RSAPublicKey by kid
     * Please cache the RSAPublicKey
     * In the demo we cache it in a map
     */
    private RSAPublicKey getRSAPublicKeyByKid(String keyId) throws InvalidPublicKeyException {
        if (keyId2PublicKey.get(keyId) != null) {
            return keyId2PublicKey.get(keyId);
        }
        JSONArray keys = getJwks();
        if (keys == null)
            return null;
        if (keyId2PublicKey.size() > MAX_PUBLIC_KEY_SIZE){
            keyId2PublicKey.clear();
        }
        for (int i = 0; i < keys.size();i++) {
            String kid = keys.getJSONObject(i).getString("kid");
            keyId2PublicKey.put(kid, getRsaPublicKeyByJwk(keys.getJSONObject(i)));
        }
        return keyId2PublicKey.get(keyId);
    }
    /**
     * get jwks from the https://oauth-login.cloud.huawei.com/oauth2/v3/certs endpoint
     * because the jwk update each day, please cache the jwk,here is the example of jwks
     * See more about JWK in http://self-issued.info/docs/draft-ietf-jose-json-web-key.html
     * {
     *  "keys":[
     *   {
     *    "kty":"RSA",
     *    "e":"AQAB",
     *    "use":"sig",
     *    "kid":"xxxx",
     *    "alg":"RS256",
     *    "n":"xxxx"
     *   },
     *   {
     *    "kty":"RSA",
     *    "e":"AQAB",
     *    "use":"sig",
     *    "kid":"xxxx",
     *    "alg":"RS256",
     *    "n":"xxxx"
     *   }
     *  ]
     * }
     * @return JSONObject
     */
    private static JSONArray  getJwks() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(CERT_URL);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .build();
        // 为httpGet实例设置配置
        httpGet.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
            return JSONObject.parseObject(result).getJSONArray("keys");
        } catch (Exception e) {
            return null;
        } finally {
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                   return null;
                }
            }
        }
    }

    /**
     * get RsaPublicKey from a JWK
     * @return RSAPublicKey
     */
    private static RSAPublicKey getRsaPublicKeyByJwk(JSONObject jwkObject) throws InvalidPublicKeyException {
        Map<String, Object> additionalAttributes = new HashMap<>();
        additionalAttributes.put("n", jwkObject.getString("n"));
        additionalAttributes.put("e", jwkObject.getString("e"));
        List<String> operations = new ArrayList<>();
        Jwk jwk = new Jwk(
                jwkObject.getString("kid"),
                jwkObject.getString("kty"),
                jwkObject.getString("alg"),
                jwkObject.getString("use"),
                operations,
                null,
                null,
                null,
                additionalAttributes);
         return ( RSAPublicKey )jwk.getPublicKey();
    }
    public static void main(String[] args) throws InvalidPublicKeyException {
        // 1. The public key is updated within 24 hours. Ensure that the following idToken is generated within 24 hours.
        // 2. Make sure the following idToken generated by CLIENT_ID which is declared as 101484027 in line 63.
        String idToken = "eyJraWQiOiI0MTRhNTkxxxx";
        IDTokenParser idTokenParser = new IDTokenParser();
        System.out.println(idTokenParser.verify(idToken));
    }
}
