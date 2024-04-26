/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2024-2024. All rights reserved.
 */

package com.huawei.hms.account.common;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * httpClient显示地设置SSLContext配置
 *
 * @author w30054667
 * @since 2024-04-23 16:46
 */
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * get httpclient
     *
     * @return
     */
    public static CloseableHttpClient getClient() {
        PoolingHttpClientConnectionManager connectionManager = buildConnectionManager("TLSv1.2", new String[] { "TLSv1.2", "TLSv1.3" },
            new String[] { "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384" });connectionManager.setMaxTotal(400);
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
