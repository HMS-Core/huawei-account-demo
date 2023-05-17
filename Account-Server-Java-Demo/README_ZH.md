﻿## **华为帐号服务服务端示例代码**

中文 | [English](README.md)

## **目录**

 * [简介](#简介)
 * [安装](#安装)
 * [配置](#配置)
 * [环境要求](#环境要求)
 * [示例代码](#示例代码)
 * [授权许可](#授权许可)


## **简介**
Android示例代码对华为账号服务（Account Kit）的服务端接口进行封装，包含丰富的示例程序，方便您参考或直接使用。详情如下：

hmssample：示例代码包，可实现登录、授权登录和登出功能。

logger：可实现日志记录。    

## **安装**
如需使用本示例代码中的各种功能，请确保您的设备上已安装华为移动服务（HMS Core）4.0。
## **环境要求**
推荐使用Android SDK 23及以上版本、JDK 1.8及以上版本。
## **配置**
Contant.java包括如下参数：

CLIENT_ID：即app ID，可从AppGallery Connect上获取。

CERT_URL：可从jwks_uri字段获取公钥URI，访问公钥URI获取公钥。

ID_TOKEN_ISSUE：与ID Token的iss字段的值相同。	

## **示例代码**
华为帐号服务的服务端Java示例代码提供了以下使用场景：

1. 在TokenAPIDemo.java中，使用Authorization Code获取Access Token。
2. 在IDTokenAPIDemo.java中，使用公钥验证ID Token。
3. 在GetTokenInfoAPIDemo.java中，解析Access Token。
4. 在IDTokenParser.java中，本地解析验证ID Token。

##  **授权许可**
华为帐号服务服务端示例代码经过[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)授权许可。
