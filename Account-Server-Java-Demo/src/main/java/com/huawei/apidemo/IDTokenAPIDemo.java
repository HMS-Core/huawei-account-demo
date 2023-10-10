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

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class IDTokenAPIDemo {
    public static void main(String[] args) throws IOException {

        //common parameters
        String urlTokenInfo = "https://oauth-login.cloud.huawei.com/oauth2/v3/tokeninfo";

        //<please update> idToken which server have got.
        String idToken
            = "eyJraWQiOiI0***hYmExZDM3MmQ5NWVjNmRiZmMxZDU3MjMyZDRlZjQ3ZWU0MmZlNGIwNjQyMjVjIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoidTVkZDVJcWxaWXIwaFlnU3otTjBEQSIsImF1ZCI6IjEwMTQ4NDA2MSIsInN1YiI6Ik1ERWZTdktHdUM1cTJCaWNuQlA0MmdCeTl2U05yaGJuVWI5VG1YNmpNTlVjbEVRIiwiYXpwIjoiMTAxNDg0MDYxIiwiaXNzIjoiaHR0cHM6Ly9hY2NvdW50cy5odWF3ZWkuY29tIiwibmFtZSI6IkpUSiIsImV4cCI6MTU3OTA4NDM3MCwiZ2l2ZW5fbmFtZSI6IkpUSiIsImRpc3BsYXlfbmFtZSI6IuW4kOWPt-W8gOWPkea0nua0nuW5uiIsImlhdCI6MTU3OTA4MDc3MCwicGljdHVyZSI6Imh0dHBzOi8vdXBmaWxlLWRyY24ucGxhdGZvcm0uaGljbG91ZC5jb20vRmlsZVNlcnZlci9pbWFnZS9iLjAwMTAwODYwMDAzMDAxNTkxODUuMjAxOTA3MTQxNjI3NDMuMzgzMzA5MDg5MTQ4NzEwMzY2NzkyODc2OTgxMDkzMjYuMTAwMC5CQkE4RDAwMkQ1NkQ0RDI1NzFCQTA3NjZBODU5ODg1MzJCRTRCNTY4Qzk0Nzk2RTkzQUJCN0FDMjRFNTU5QUE0LmpwZyJ9.fwQQzgS73h0yifJDY_hD5syAK3YUO6M9BegzaObSOGB-FwokVUmXG0WSMtzO1Saoy3-IB6v1k9XFl90vtt6lxT6OvTkt1DN4nCUhClB_-bfQWGHdhCW0kkyDEcNJclW4frZ3v1fkYtal7QfF6s0JoTsNg2sdL8JztYapt5EIoz8zyB_T00okeX75bJ7acKY1CEymYwJ5lslpE6SmmXFNtmW88LezvdJBRhUUZl90ZESPeORcwtxiRiEjUn7ZabH-FkZvsIyuOj0jGshvblWBj_HFag9MinuLLsIC5-zkOLWAN7kRPGK_sh-LVxWDZE0yeGH1kHu_AzAS0GEekfMt8g";

        JSONObject tokenInfo = getDetailByIDToken(urlTokenInfo,idToken);
        System.out.println("the output of /oauth2/v3/tokeninfo is : \n" + tokenInfo.toJSONString());
    }

    private static JSONObject getDetailByIDToken(String urlTokenInfo, String idToken) throws IOException {
        HttpPost httpPost = new HttpPost(urlTokenInfo);
        List<NameValuePair> request = new ArrayList<>();
        request.add(new BasicNameValuePair("id_token", idToken));
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
            new String[] {
                "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256",
                "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA"
            });
        connectionManager.setMaxTotal(400);
        connectionManager.setDefaultMaxPerRoute(400);
        RequestConfig config = RequestConfig.custom()
            .setConnectionRequestTimeout(100)
            .setRedirectsEnabled(false)
            .build();

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
