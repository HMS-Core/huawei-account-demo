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
package com.huawei.apidemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
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
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class GetTokenInfoAPIDemo {

    public static void main(String[] args) throws IOException {
        //common parameters
        String urlScope = "https://api.cloud.huawei.com/rest.php";

        //<please update> access token which server have got.
        String accessToken = "CV4e6lKIn6xJiO9Nz1xIqWj8FY0DYmt/wiPXGJbvlDrBq4hOWlFbygdDoMg0htDm0LdlPIrRwAihXFGADOMkddmzhiE01R+99Y71hx/yfzuDb5njDT9bDg==";

        JSONObject tokenInfo = getClientTokenInfo(accessToken, urlScope);
        System.out.println("the output of huawei.oauth2.user.getTokenInfo is : \n" + tokenInfo.toJSONString());

    }

    /**
     *
     * @param accessToken AT
     * @param urlScope
     * @return JSON
     * @throws IOException
     */
    private static JSONObject getClientTokenInfo(String accessToken, String urlScope) throws IOException {
        HttpPost httppost = new HttpPost(urlScope);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("nsp_svc=");
        stringBuffer.append("huawei.oauth2.user.getTokenInfo");
        stringBuffer.append("&open_id=");
        stringBuffer.append("OPENID");
        stringBuffer.append("&access_token=");
        stringBuffer.append(URLEncoder.encode(accessToken, "UTF-8"));
        StringEntity entity = new StringEntity(stringBuffer.toString());

        httppost.setEntity(entity);
        CloseableHttpResponse response = getClient().execute(httppost);
        try {
            HttpEntity entity1 = response.getEntity();
            String ret = entity1 != null ? EntityUtils.toString(entity1, "UTF-8") : null;
            JSONObject jsonObject = (JSONObject) JSON.parse(ret);
            EntityUtils.consume(entity1);
            return jsonObject;
        } finally {
            response.close();
        }
    }

    /**
     * get http client
     * @return
     */
    private static CloseableHttpClient getClient() {
        PoolingHttpClientConnectionManager connectionManager = buildConnectionManager("TLSv1.2", new String[]{"TLSv1.2","TLSv1.1"},
                new String[]{"TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256","TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA"});
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
     *
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
            e.printStackTrace();
        }
        return connectionManager;
    }

}

