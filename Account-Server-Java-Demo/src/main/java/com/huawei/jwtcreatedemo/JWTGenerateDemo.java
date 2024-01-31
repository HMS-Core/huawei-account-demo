/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2024-2024. All rights reserved.
 */

package jwtGenerate;

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

/**
 * Function Description
 * This is a demo for create a JWT
 * The open source software depended by this demo may have vulnerabilities,
 * please refer to the open source software release website and update to
 * the latest version or replace it with other open source software.
 * Local validation is much more efficiently than by access the tokeninfo endpoint
 * You'd better learn more about the JWT for understanding this demo
 * See more about JWT in https://jwt.io/
 *
 * @author j00449997
 * @since 2019-12-19
 */
public class JWTGenerateDemo {
    // please replace it with the private_key in your json file
    // this is the plain text in this demo, please encrypt the private key in your code, only get the string between
    // '-----BEGIN PRIVATE KEY-----\n' and '\n-----END PRIVATE KEY-----\n'
    private static final String PRIVATE_KEY =
        "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";

    // please replace it with the sub_account in your json file
    private static final String ISS = "110192491";

    // please replace it with the key_id in your json file
    private static final String KID = "6b915212f84a40a5b9d299539d0cabb6";

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
