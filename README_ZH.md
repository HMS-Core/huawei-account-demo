# 华为帐号服务示例代码

中文 | [English](README.md) 

## 目录
* [简介](#简介)
* [安装](#安装)
* [环境要求](#环境要求)
* [配置](#配置)
* [示例代码](#示例代码)
* [运行结果](#运行结果)
* [技术支持](#技术支持)
* [授权许可](#授权许可)

## 简介
Android示例代码对华为帐号服务（Account Kit）的服务端接口进行封装，包含丰富的示例程序，方便您参考或直接使用。详情如下：
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

## 示例代码
华为帐号服务的服务端Java示例代码提供了以下使用场景：
1. 在TokenAPIDemo.java中，使用Authorization Code获取Access Token。
2. 在IDTokenAPIDemo.java中，使用公钥验证ID Token。
3. 在GetTokenInfoAPIDemo.java中，解析Access Token。

华为帐号服务的客户端Java示例代码提供了以下使用场景：
1. ID Token模式登录。
2. Authorization Code模式登录。
3. 静默登录。
4. 退出帐号。
5. 帐号取消授权。

具体业务逻辑：在[AccountActivity.java](https://github.com/HMS-Core/huawei-account-demo/blob/master/Account-Client-Java-Demo/Account_Demo_AndroidStudio/app/src/main/java/com/huawei/hmssample/AccountActivity.java)中实现ID Token模式登录和Authorization Code登录。

## 运行结果
华为帐号服务的服务端Java示例代码打印Access Token和JWT（JSON Web Toke）的结果。

## 技术支持
如需了解更多HMS Core相关信息，请前往[Reddit](https://www.reddit.com/r/HuaweiDevelopers/)社区获取HMS Core最新资讯，参与开发者讨论。
如您对示例代码使用有疑问，请前往：
* [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services?tab=Votes)提问，上传问题时请打上huawei-mobile-services标签。
* [华为开发者论坛](https://developer.huawei.com/consumer/cn/forum/block/hms-core?ha_source=hms1)，获得更多意见与建议。
如您在运行示例代码时出现错误，请到GitHub提交[issue](https://github.com/HMS-Core/huawei-account-demo/issues)或[pull request](https://github.com/HMS-Core/huawei-account-demo/pulls)。

## 授权许可
华为帐号服务Android示例代码经过[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)授权许可。