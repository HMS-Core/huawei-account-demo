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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class TokenAPIDemo {
    public static void main(String[] args) throws IOException {

        //common parameters
        String urlToken = "https://oauth-login.cloud.huawei.com/oauth2/v3/token";
        //set value as "authorization_code" meaning Use Authorization Code to get Access Token and ID Token
        String grant_type = "authorization_code";

        //<please update> Callback url of application configuration
        String redirect_uri = "hms://redirect_url";
        //<please update> Appid of the application registered on the developer Alliance.
        String client_id = "101484061";
        //<please update> Public key assigned to the application by Huawei developer alliance after application creation,please refrencing the interface description for more details
        String client_secret = "14275e4b570993e065119f8d423b489d7b460ccaee3ddf13d0939af19ebb4799";
        //<please update> code which server have got.
        //By requesting the following URL to get code returned by the user to confirm the authorization
        // (url:https://oauth-login.cloud.huawei.com/oauth2/v3/authorize?response_type=code&access_type=offline&state=state_parameter_passthrough_value&client_id=101484061&redirect_uri=hms://redirect_url&scope=openid+profile)
        String code = "CV4e3VYs22cDEVydKG7KlKbz4UrU1G78V9f7i4wqMzjzQ4lcYa90XRw37fWrYoPz8L%2B%2BqH8QHZW82bsPQ3eSPrdpF585YrXsNR%2BcuEKGKKlalq6ref5%2FX7V2%2BZzwIF0yUrRwmxqHoM4kpj4kcGZ0rTrpOy7xG4%2B41rpEdF7tZPod0WeDkSTv0gDWWq%2B64%2BAFyxDv17gDsm0%3D";
        // If the code is encoded by urlEncode once (eg. the value of the the preceding code ), please run the following to urlDecode once. 
       code = java.net.URLDecoder.decode(code,   "utf-8");

        JSONObject tokens = getTokenByCode(redirect_uri, urlToken, code, client_secret, client_id, grant_type);
        System.out.println("the output of oauth2/v3/token is : \n" + tokens.toJSONString());
    }

    private static JSONObject getTokenByCode(String redirect_uri, String urlToken, String code, String client_secret,
                                             String client_id, String grant_type) throws IOException {
        HttpPost httpPost = new HttpPost(urlToken);
        List<NameValuePair> request = new ArrayList<>();
        request.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        request.add(new BasicNameValuePair("code", code));
        request.add(new BasicNameValuePair("client_secret", client_secret));
        request.add(new BasicNameValuePair("client_id", client_id));
        request.add(new BasicNameValuePair("grant_type", grant_type));
        httpPost.setEntity(new UrlEncodedFormEntity(request));

        CloseableHttpResponse response = getClient().execute(httpPost);

        try {
            HttpEntity responseEntity = response.getEntity();
            String ret = responseEntity != null ? EntityUtils.toString(responseEntity) : null;
            JSONObject jsonObject = (JSONObject) JSON.parse(ret);
            EntityUtils.consume(responseEntity);
            return jsonObject;
        } finally {
            response.close();
        }
    }
    /**
     * get httpclient
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
