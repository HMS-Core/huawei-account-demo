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

import com.huawei.hms.account.code.bean.ResponseInfos;
import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * tool when use Authorization Code mode
 *
 * @date 2021-04-22
 */
public class AuthCodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(AuthCodeUtil.class);
    private static final String CODE_GRANT_TYPE = "authorization_code";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private static final String URL_SCOPE = "https://oauth-api.cloud.huawei.com/rest.php";
    private static final String URL_GET_INFO = "https://account.cloud.huawei.com/rest.php";
    private static final String TOKEN_URI = "https://oauth-login.cloud.huawei.com/oauth2/v3/token";
    private static final String OPEN_ID = "OPENID";
    private static final String NSP_SVC_GET_INFO = "GOpen.User.getInfo";
    private static final String NSP_SVC_PERSE_ACCESS_TOKEN = "huawei.oauth2.user.getTokenInfo";
    private static final int HTTP_STATUS_OK = 200;

    /**
     * get access token and refresh token by code
     * if request success,will return as ResponseInfos{body=TokensEntity,nspStatus=0}
     * if request fail,will return as ResponseInfos{body=ErrorInfos,nspStatus=*}
     *
     * @param code
     * @param appId
     * @param appSecret
     * @param redirectUri
     * @return
     */
    public static ResponseInfos getTokensByCode(String code, String appId, String appSecret, String redirectUri) {
        HttpPost httpPost = new HttpPost(TOKEN_URI);
        ResponseInfos responseInfos = null;
        List<NameValuePair> request = new ArrayList<>();
        request.add(new BasicNameValuePair("redirect_uri", redirectUri));
        request.add(new BasicNameValuePair("code", code));
        request.add(new BasicNameValuePair("client_secret", appSecret));
        request.add(new BasicNameValuePair("client_id", appId));
        request.add(new BasicNameValuePair("grant_type", CODE_GRANT_TYPE));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(request));
            responseInfos = executeHttpRequest(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseInfos;
    }

    /**
     * parse access token
     * if request success,will return as ResponseInfos{body=AccessTokenInfos,nspStatus=0}
     * if request fail,will return as ResponseInfos{body=ErrorInfos,nspStatus=*}
     *
     * @param accessToken
     * @return
     */
    public static ResponseInfos parseAccessToken(String accessToken) {
        ResponseInfos responseInfos = null;
        HttpPost httpPost = new HttpPost(URL_SCOPE);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("nsp_svc=");
        stringBuffer.append(NSP_SVC_PERSE_ACCESS_TOKEN);
        stringBuffer.append("&open_id=");
        stringBuffer.append(OPEN_ID);
        stringBuffer.append("&access_token=");
        try {
            stringBuffer.append(URLEncoder.encode(accessToken, "UTF-8"));
            StringEntity entity = new StringEntity(stringBuffer.toString());
            httpPost.setEntity(entity);
            responseInfos = executeHttpRequest(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseInfos;
    }

    /**
     * update access token by refresh token when access token is expired,
     * if request success,will return as ResponseInfos{body=TokensEntity,nspStatus=0}
     * if request fail,will return as ResponseInfos{body=ErrorInfos,nspStatus=*}
     *
     * @param refreshToken
     * @param appId
     * @param appSecret
     * @return
     */
    public static ResponseInfos updateAccessToken(String refreshToken, String appId, String appSecret) {
        ResponseInfos responseInfos = null;
        HttpPost httpPost = new HttpPost(TOKEN_URI);
        List<NameValuePair> request = new ArrayList<>();
        request.add(new BasicNameValuePair("refresh_token", refreshToken));
        request.add(new BasicNameValuePair("client_secret", appSecret));
        request.add(new BasicNameValuePair("client_id", appId));
        request.add(new BasicNameValuePair("grant_type", REFRESH_TOKEN_GRANT_TYPE));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(request));
            responseInfos = executeHttpRequest(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseInfos;
    }

    /**
     * get user Infos by access token
     * if request success,will return as ResponseInfos{body=UserInfos,nspStatus=0}
     * if request fail,will return as ResponseInfos{body=ErrorInfos,nspStatus=*}
     *
     * @param accessToken
     * @param getNickName
     * @return
     * @throws IOException
     */
    public static ResponseInfos getUserInfos(String accessToken, int getNickName) {
        ResponseInfos responseInfos = null;
        HttpPost httpPost = new HttpPost(URL_GET_INFO);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("nsp_fmt=");
        stringBuffer.append("JSON");
        stringBuffer.append("&nsp_svc=");
        stringBuffer.append(NSP_SVC_GET_INFO);
        stringBuffer.append("&access_token=");
        try {
            stringBuffer.append(URLEncoder.encode(accessToken, "UTF-8"));
            stringBuffer.append("&getNickName=");
            stringBuffer.append(getNickName);
            StringEntity entity = new StringEntity(stringBuffer.toString());
            httpPost.setEntity(entity);
            responseInfos = executeHttpRequest(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseInfos;
    }

    /**
     * execute http request
     *
     * @param httpPost
     * @return
     */
    private static ResponseInfos executeHttpRequest(HttpPost httpPost) {
        CloseableHttpResponse response = null;
        try {
            response = getClient().execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            Header[] nsp_statuses = response.getHeaders("NSP_STATUS");
            String ret = responseEntity != null ? EntityUtils.toString(responseEntity, Charsets.UTF_8) : null;
            ResponseInfos responseInfos = new ResponseInfos();
            responseInfos.setBody(ret);
            if (response.getStatusLine().getStatusCode() != HTTP_STATUS_OK) {
                logger.error("http request failed,{}", ret);
            }
            EntityUtils.consume(responseEntity);
            if (nsp_statuses.length == 1) {
                String value = nsp_statuses[0].getValue();
                responseInfos.setNspStatus(Integer.valueOf(value));
                logger.info("NSP_STATUS:{}", value);
            }
            return responseInfos;
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get httpclient
     *
     * @return
     */
    private static CloseableHttpClient getClient() {
        PoolingHttpClientConnectionManager connectionManager = buildConnectionManager("TLSv1.2", new String[] { "TLSv1.2", "TLSv1.3" }, 
                new String[] { "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384" });
        connectionManager.setMaxTotal(400);
        connectionManager.setDefaultMaxPerRoute(400);
        RequestConfig config =
                RequestConfig.custom().setConnectionRequestTimeout(100).setRedirectsEnabled(false).build();

        return HttpClients.custom()
                .useSystemProperties()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .build();
    }

    /**
     * @param protocol
     * @param supportedProtocols
     * @param supportedCipherSuites
     * @return
     */
    private static PoolingHttpClientConnectionManager buildConnectionManager(String protocol,
                                                                             String[] supportedProtocols, String[] supportedCipherSuites) {
        PoolingHttpClientConnectionManager connectionManager = null;
        try {
            SSLContext sc = SSLContext.getInstance(protocol);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);
            sc.init(null, tmf.getTrustManagers(), null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sc, supportedProtocols,
                    supportedCipherSuites, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", sslsf)
                    .build();
            connectionManager = new PoolingHttpClientConnectionManager(registry);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            logger.error("buildConnectionManager failed,error:{}", e.getMessage());
        }
        return connectionManager;
    }
}
