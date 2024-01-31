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

package com.huawei.jwtcreatedemo;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Objects;

public class JWTGenerateDemo {
    // please replace it with the private_key in your json file
    // this is the plain text in this demo, please encrypt the private key in your code, only get the string between
    // '-----BEGIN PRIVATE KEY-----\n' and '\n-----END PRIVATE KEY-----\n'
    private static final String PRIVATE_KEY = "******";

    // please replace it with the sub_account in your json file
    private static final String ISS = "******";

    // please replace it with the key_id in your json file
    private static final String KID = "******";

    private static final String AUD = "https://oauth-login.cloud.huawei.com/oauth2/v3/token";

    private static final String ALG_PS256 = "PS256";

    private static final String DOT = ".";

    private static PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodeBase64(key));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private static byte[] decodeBase64(String Base64Str) {
        return Base64.decodeBase64(Base64Str.getBytes(StandardCharsets.UTF_8));
    }

    private String createJwt()
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        long iat = System.currentTimeMillis() / 1000;
        long exp = iat + 3600;

        // jwt header
        JSONObject header = new JSONObject();
        header.put("alg", ALG_PS256);
        header.put("kid", KID);
        header.put("typ", "JWT");

        // jwt payload
        JSONObject payload = new JSONObject();
        payload.put("aud", AUD);
        payload.put("iss", ISS);
        payload.put("exp", exp);
        payload.put("iat", iat);

        // jwt signature
        byte[] encodeHeaderBytes = Base64.encodeBase64URLSafe(header.toString().getBytes(StandardCharsets.UTF_8));
        byte[] encodePayloadBytes = Base64.encodeBase64URLSafe(payload.toString().getBytes(StandardCharsets.UTF_8));
        String encodeHeader = new String(encodeHeaderBytes, StandardCharsets.UTF_8);
        String encodePayload = new String(encodePayloadBytes, StandardCharsets.UTF_8);
        String jwtHeaderAndPayload = encodeHeader + DOT + encodePayload;
        Signature signatureInstance = Signature.getInstance("SHA256withRSA/PSS", new BouncyCastleProvider());
        signatureInstance.initSign(getPrivateKey(PRIVATE_KEY));
        signatureInstance.update(jwtHeaderAndPayload.getBytes(StandardCharsets.UTF_8));
        String signature =
            new String(Objects.requireNonNull(Base64.encodeBase64URLSafe(signatureInstance.sign())), StandardCharsets.UTF_8);

        return jwtHeaderAndPayload + DOT + signature;
    }

    public static void main(String args[])
        throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        JWTGenerateDemo JWTGenerateDemo = new JWTGenerateDemo();
        System.out.println(JWTGenerateDemo.createJwt());
    }
}
