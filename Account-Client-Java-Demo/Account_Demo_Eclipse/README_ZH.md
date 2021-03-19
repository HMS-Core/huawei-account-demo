# 华为帐号服务客户端Eclipse示例代码

中文 | [English](https://github.com/HMS-Core/huawei-account-demo/blob/master/Account-Client-Java-Demo/Account_Demo_Eclipse/README.md) 

## 目录
* [简介](#简介)
* [安装](#安装)
* [环境要求](#环境要求)
* [配置](#配置)
* [快速上手](#快速上手)
* [示例代码](#示例代码)
* [授权许可](#授权许可)

## 简介
Android示例代码对华为帐号服务（HUAWEI Account Kit）的客户端接口进行封装，包含丰富的示例程序，方便您参考或直接使用。详情如下：
hmssample：示例代码包，可实现登录、授权登录和退出帐号功能。
logger：可实现日志记录。

## 安装
如需使用本示例代码中的各种功能，请确保您的设备上已安装华为移动服务（HMS Core）4.0。

## 环境要求
推荐使用Android SDK 23及以上版本、JDK 1.8及以上版本。

## 配置
Contant.java包括如下参数：
CLIENT_ID：即app ID，可从AppGallery Connect上获取。
CERT_URL：可从jwks_uri字段获取公钥URI，访问公钥URI获取公钥。
ID_TOKEN_ISSUE：与ID Token的iss字段的值相同。

## 快速上手
1. 添加项目依赖。具体请参考在Eclipse项目中集成HMS Core SDK。

2. 在Eclipse IDE中右击打开项目工程，并点击“Export”。
![image.png](http://image.huawei.com/tiny-lts/v1/images/d9a59bb5b945f8c95979be3b7b3b1d37_511x542.png@900-0-90-f.png)

3. 点击“Next”进入“Keystore selection”页面。

4. 选择“Use existing keystore”，点击“Browse”，选择目录下的“lightregion.jks”文件，并输入密码“android”。
![image.png](http://image.huawei.com/tiny-lts/v1/images/f614cfefc03ee9dbbc1dba2daeedc304_510x542.png@900-0-90-f.png)

5. 选择“Use existing key”，“Alias”设置为“androiddebugkey”，并输入密码“android”。
![image.png](http://image.huawei.com/tiny-lts/v1/images/d70ab5ecddbd53eb5ba63c239f7efa1f_510x542.png@900-0-90-f.png)

6. 点击“Browse”，选择APK导出位置并点击“Finish”。
![image.png](http://image.huawei.com/tiny-lts/v1/images/db9fd9f9b2ebd23f3f99685bacc30192_511x540.png@900-0-90-f.png)

7. 安装并启动示例应用，可以看到如下效果。
![image.png](http://image.huawei.com/tiny-lts/v1/images/b3e3cf07c5da4ddd419559ba7e76fd30_276x599.png@900-0-90-f.png)

## 示例代码
本示例代码提供了以下使用场景：
1. ID Token模式登录。此模式包含“登录+ID Token验证”。
2. Authorization Code模式登录。
3. 退出帐号。

具体业务逻辑：在HuaweiIdActivity.java中实现ID Token模式登录和Authorization Code登录；在IDTokenParse.java中实现ID Token验证。

## 授权许可
华为帐号服务Android示例代码经过[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)授权许可。