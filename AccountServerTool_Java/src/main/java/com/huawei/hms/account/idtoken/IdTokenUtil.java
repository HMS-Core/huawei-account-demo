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
package com.huawei.hms.account.idtoken;

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
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * verify id token and get user infos by id token when use ID-Token model
 *
 * @date 2021-04-22
 */
public class IdTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(IdTokenUtil.class);

    private final static int MAX_PUBLIC_KEY_SIZE = 5;

    private static Map<String, RSAPublicKey> keyId2PublicKey = new HashMap<String, RSAPublicKey>();

    private static final String CERT_URL = "https://oauth-login.cloud.huawei.com/oauth2/v3/certs";

    private static final String ID_TOKEN_ISSUE = "https://accounts.huawei.com";

    private static DecodedJWT decoder;

    /**
     * get user infos by id token if id token verify success
     *
     * @param idToken
     * @param appId
     * @return
     */
    public static String getUserInfosByIdToken(String idToken, String appId) {
        decoder = JWT.decode(idToken);
        if (!verify(appId)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(new String(Base64.decodeBase64(decoder.getPayload())));
        return jsonObject.toJSONString();
    }

    /**
     * verify id token
     *
     * @param appId
     * @return
     */
    public static boolean verify(String appId) {
        try {
            Algorithm algorithm = Algorithm.RSA256(getRSAPublicKeyByKid(decoder.getKeyId()), null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            // Verify the value of iss
            if (!decoder.getIssuer().equals(ID_TOKEN_ISSUE)) {
                logger.info("verify iss failed!");
                return false;
            }
            // Verify your app’s app ID.
            if (decoder.getAudience().size() > 0) {
                if (!decoder.getAudience().get(0).equals(appId)) {
                    logger.info("verify app id failed!");
                    return false;
                }
            }
            // verify signature
            verifier.verify(decoder);
            return true;
        } catch (JWTDecodeException e) {
            logger.error("JWT decode failed!");
            return false;
        } catch (TokenExpiredException e) {
            logger.error("token is expire!");
            return false;
        } catch (JWTVerificationException e) {
            logger.error("verify signature failed!");
            return false;
        }
    }


    /**
     * get the RSAPublicKey by kid
     * Please cache the RSAPublicKey
     * In the demo we cache it in a map
     *
     * @param keyId
     * @return
     */
    private static RSAPublicKey getRSAPublicKeyByKid(String keyId) {
        if (keyId2PublicKey.get(keyId) != null) {
            return keyId2PublicKey.get(keyId);
        }
        JSONArray keys = getJwks();
        if (keys == null)
            return null;
        if (keyId2PublicKey.size() > MAX_PUBLIC_KEY_SIZE) {
            keyId2PublicKey.clear();
        }
        for (int i = 0; i < keys.size(); i++) {
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
     * "keys":[
     * {
     * "kty":"RSA",
     * "e":"AQAB",
     * "use":"sig",
     * "kid":"670c64e7443941582167f405e0a62a08c6db5becb090f397a45aa572fa000553",
     * "alg":"RS256",
     * "n":"AK4C-h_gWpziPmzo6PEBuwxHHD2F9x_LgiE5zl73fVmzBTo3KzRu8nXURQA-uV857r_qEhfsJQyy0Nr_wIkfAU86JsFHcGwzLlJucN12EHXOFY6nLti9tSWUAWaa2HAZuJytyc-DyguLR_nH5IKmGhmcgI26zUG07UPUB2Xnsn-T-K1npnaNI7K7xlbGQy5UUPFjQPBRiV2R_-iGf5KIqCwebXe24wzhbWMnmfb0lilAZyYO7PiQ8UgJJTuZOMbCD7P0dUJwxitHo81OyoVJUGQZpLBZqHVSsRpC0UZuxMEMBPza4R55yZS3gAKkE1xILabCUV-CJ6Gp4c4J1tiFNcc"
     * },
     * {
     * "kty":"RSA",
     * "e":"AQAB",
     * "use":"sig",
     * "kid":"1226cda6b82e6aa140ffe2f32515f1929c3048b2cdba267935fb71963fd3e57a",
     * "alg":"RS256",
     * "n":"AMNDMIxlySrGqeV7V3s865ZwzBD0hXVq8ys0H_ZQGMbfWss0WuwHrmIRdq8OQrYoN_o2KZKtUPBsJpJMAZ26JeGqf3dsU_wFEEjNOfrDdyIs86K6gKfQFLewUNycmzMhlqDFlCujAAF33RYn-Xg7UXG3pO_2PdcD1zIrxtawQZ-TQTMgH5mgX_lWO1YfYSuB91xEvUZII1ZYDjLcbkzjZCbvfU4tT2_PV8-gU0UaHI-pcyreUwB1EhleDZUW8MiaIVSr6UIYfHflxBQRrwCcFy-q_u-OeYBS683djmbF-FKZVKlipKim6hGpqIb-PC8pHO_WmM01sNvvMAkF1D5bg0M"
     * }
     * ]
     * }
     *
     * @return JSONObject
     */
    private static JSONArray getJwks() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(CERT_URL);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .build();
        httpGet.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, Charsets.UTF_8);
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
     * @param jwkObject
     * @return
     */
    private static RSAPublicKey getRsaPublicKeyByJwk(JSONObject jwkObject) {
        Map<String, Object> additionalAttributes = new HashMap<String, Object>();
        additionalAttributes.put("n", jwkObject.getString("n"));
        additionalAttributes.put("e", jwkObject.getString("e"));
        List<String> operations = new ArrayList<String>();
        PublicKey publicKey = null;
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
        try {
            publicKey = jwk.getPublicKey();
        } catch (InvalidPublicKeyException e) {
            logger.error("public key is invalid!");
            return null;
        }
        return (RSAPublicKey) publicKey;
    }
}
