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
package com.huawei.serverlessidtokendemo.verification.utils;


import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huawei.serverlessidtokendemo.verification.beans.HeaderEntity;
import com.huawei.serverlessidtokendemo.verification.beans.IdTokenEntity;
import com.huawei.serverlessidtokendemo.verification.beans.PayloadEntity;
import com.huawei.serverlessidtokendemo.verification.beans.VerifyBean;
import com.huawei.serverlessidtokendemo.verification.interfaces.IVerifyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 从idToken中解码用户信息
 *
 * @date 2021/1/6
 */
public class IdTokenUtils {
	private static final String TAG = "IdTokenUtils";
	private static final String PUBLIC_KEY_ALGORITHM = "RSA";
	public static final String CERT_URL = "https://oauth-login.cloud.huawei.com/oauth2/v3/certs";
	public static final String ID_TOKEN_ISSUE = "https://accounts.huawei.com";
	private JSONArray mJsonArray;

	/**
	 * catch the public key in this map
	 */
	private Map<String, RSAPublicKey> keyId2PublicKey = new HashMap<String, RSAPublicKey>();
	private final static int MAX_PUBLIC_KEY_SIZE = 4;

	public IdTokenUtils() {
	}

	/**
	 * 从id Token中解码用户信息
	 *
	 * @param idToken idToken
	 * @return IdTokenEntity idToken中的数据为空，或者不是合法的jwt格式会返回null
	 */
	public IdTokenEntity decodeJsonStringFromIdtoken(String idToken) {
		IdTokenEntity idTokenEntity = null;
		try {
			if (TextUtils.isEmpty(idToken)) {
				Log.w(TAG, "idToken is Empty");
				return null;
			}

			String[] arrs = idToken.split("\\.");
			if (arrs.length < 3) {
				Log.e(TAG, "\n" + "The idToken is malformed");
				return null;
			}
			Log.i(TAG, "idToken header: " + arrs[0]);
			Log.i(TAG, "idToken payload: " + arrs[1]);
			byte[] headerBytes = Base64.decode(arrs[0], Base64.URL_SAFE);
			byte[] payloadBytes = Base64.decode(arrs[1], Base64.URL_SAFE);
			if (null == headerBytes || null == payloadBytes) {
				Log.e(TAG, "The idToken decode failed");
				return null;
			}
			Gson gson = new Gson();
			String headerJson = new String(headerBytes, Charset.forName("utf-8"));
			Log.i(TAG, "headerJson: " + headerJson);
			String payloadJson = new String(payloadBytes, Charset.forName("utf-8"));
			Log.i(TAG, "payloadJson: " + payloadJson);
			HeaderEntity headerEntity = gson.fromJson(headerJson, HeaderEntity.class);
			PayloadEntity payloadEntity = gson.fromJson(payloadJson, PayloadEntity.class);

			idTokenEntity = new IdTokenEntity();
			idTokenEntity.setHeaderJson(headerJson);
			idTokenEntity.setPayloadJson(payloadJson);
			idTokenEntity.setHeaderEntity(headerEntity);
			idTokenEntity.setPayloadEntity(payloadEntity);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			Log.e(TAG, "decodeJsonStringFromIdtoken JsonSyntaxException e:" + e.getMessage());
		}
		return idTokenEntity;
	}

	/**
	 * 认证解析后的idToken
	 *
	 * @param idTokenEntity
	 * @return
	 */
	public void validateIdToken(String subInput, String idToken, String appId, IdTokenEntity idTokenEntity, IVerifyCallBack iCallBack) {
		// 1 getJwks
		getJwks(new IVerifyCallBack() {

			@Override
			public void onSuccess() {
				// 2.验证签名，验证verifyToken中的过期时间（exp字段）是否已过期。
				VerifyBean verifyRSAPublicKey = verifyRSAPublicKey(idTokenEntity.getHeaderEntity().getKid(), idToken);
				if (!verifyRSAPublicKey.isSuccess()) {
					iCallBack.onFailed("verifyRSAPublicKey error," + verifyRSAPublicKey.getMessage());
					return;
				}
				// 3. 验证verifyToken中iss字段的值是否等于“https://accounts.huawei.com”。
				String iss = idTokenEntity.getPayloadEntity().getIss();
				boolean verifyIss = !TextUtils.isEmpty(iss) && iss.equals(ID_TOKEN_ISSUE);
				if (!verifyIss) {
					iCallBack.onFailed("verifyIss error");
					return;
				}
				// 4. 验证verifyToken中aud字段的值等于应用的包名。
				String aud = idTokenEntity.getPayloadEntity().getAud();
				boolean verifyAud = !TextUtils.isEmpty(aud) && aud.equals(appId);
				if (!verifyAud) {
					iCallBack.onFailed("verifyAud error");
					return;
				}
				// 5、验证verifyToken中的用户（sub字段）是否正确。
				String sub = idTokenEntity.getPayloadEntity().getSub();
				boolean verifySub = !TextUtils.isEmpty(sub) && sub.equals(subInput);
				if (!verifySub) {
					iCallBack.onFailed("verifySub error");
					return;
				}
				iCallBack.onSuccess();
			}

			@Override
			public void onFailed(String errorMsg) {
				iCallBack.onFailed("getJwks error,errorMsg:" + errorMsg);
			}

		});
	}

	/**
	 * 验证签名
	 *
	 * @param keyId
	 * @param idToken
	 * @return
	 */
	private VerifyBean verifyRSAPublicKey(String keyId, String idToken) {
		Log.d(TAG, "verifyRSAPublicKey keyId:" + keyId);
		RSAPublicKey mRSAPublicKey;
		try {
			if (keyId2PublicKey.get(keyId) != null) {
				mRSAPublicKey = keyId2PublicKey.get(keyId);
			} else {
				if (keyId2PublicKey.size() > MAX_PUBLIC_KEY_SIZE) {
					keyId2PublicKey.clear();
				}
				Log.d(TAG, "mJsonArray:" + mJsonArray.toString());
				for (int i = 0; i < mJsonArray.length(); i++) {
					String kid = mJsonArray.getJSONObject(i).getString("kid");
					Log.d(TAG, "mJsonArray kid:" + kid);
					keyId2PublicKey.put(kid, getRsaPublicKeyByJwk(mJsonArray.getJSONObject(i)));
				}
				Log.d(TAG, "keyId2PublicKey:" + keyId2PublicKey.toString());
				mRSAPublicKey = keyId2PublicKey.get(keyId);
			}
			if (mRSAPublicKey == null) {
				return new VerifyBean(false, "mRSAPublicKey is null");
			}

			Jwts.parser().setSigningKey(mRSAPublicKey).parse(idToken);

		} catch (Exception e) {
			if (e instanceof ExpiredJwtException) {
				return new VerifyBean(false, "verify exp error:" + e.getMessage());
			} else {
				return new VerifyBean(false, "e:" + e.getClass().getSimpleName() + ", msg:" + e.getMessage());
			}
		}
		return new VerifyBean(true);
	}

	private RSAPublicKey getRsaPublicKeyByJwk(JSONObject jwkObject) throws InvalidPublicKeyException, JSONException {
		Map<String, Object> additionalAttributes = new HashMap<String, Object>();
		additionalAttributes.put("n", jwkObject.getString("n"));
		additionalAttributes.put("e", jwkObject.getString("e"));
		List<String> operations = new ArrayList<String>();
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
		return (RSAPublicKey) getPublicKey(jwk);
	}

	public PublicKey getPublicKey(Jwk jwk) throws InvalidPublicKeyException {
		if (!PUBLIC_KEY_ALGORITHM.equalsIgnoreCase(jwk.getType())) {
			throw new InvalidPublicKeyException("The key is not of type RSA", null);
		}
		try {
			KeyFactory kf = KeyFactory.getInstance(PUBLIC_KEY_ALGORITHM);
			BigInteger modulus = new BigInteger(1, Base64.decode((String) jwk.getAdditionalAttributes().get("n"), Base64.URL_SAFE));
			BigInteger exponent = new BigInteger(1, Base64.decode((String) jwk.getAdditionalAttributes().get("e"), Base64.URL_SAFE));
			return kf.generatePublic(new RSAPublicKeySpec(modulus, exponent));
		} catch (InvalidKeySpecException e) {
			throw new InvalidPublicKeyException("Invalid public key", e);
		} catch (NoSuchAlgorithmException e) {
			throw new InvalidPublicKeyException("Invalid algorithm to generate key", e);
		}
	}

	/**
	 * 1.验证JWT有效性
	 *
	 * @param iCallBack
	 */
	private void getJwks(IVerifyCallBack iCallBack) {
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			final Request request = new Request.Builder()
					.url(CERT_URL)
					.build();
			Call call = okHttpClient.newCall(request);
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					Log.i(TAG, "Get ID Token failed.");
					iCallBack.onFailed("getJwks onFailure Get ID Token failed." + e.getMessage());
				}

				@Override
				public void onResponse(Call call, Response response) {
					if (response.isSuccessful()) {
						try {
							String res = response.body().string();
							JSONObject jsonObject = new JSONObject(res);
							mJsonArray = jsonObject.getJSONArray("keys");
							if (mJsonArray == null) {
								iCallBack.onFailed("getJwks JsonArray is null failed.");
								return;
							}
							iCallBack.onSuccess();
						} catch (NullPointerException | JSONException | IOException e) {
							Log.i(TAG, "parse JsonArray failed." + e.getMessage());
							iCallBack.onFailed("getJwks parse JsonArray failed.");
						}
					} else {
						iCallBack.onFailed("getJwks onResponse failed." + response.message() + "code:" + response.code());
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			iCallBack.onFailed("getJwks Exception error e:" + e.getMessage());
		}
	}

}
