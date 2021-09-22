# 鸿蒙应用无服务器场景使用idToken模式接入华为帐号示例代码

中文 | [English]() 

## 目录
* [简介](#简介)
* [安装](#安装)
* [环境要求](#环境要求)
* [配置](#配置)
* [示例代码](#示例代码)
* [运行结果](#运行结果)
* [授权许可](#授权许可)

## 简介
本示例代码对华为帐号服务（HUAWEI Account Kit）常用接口在鸿蒙系统有服务器场景下的使用idToken模式接入华为帐号提供了代码样例，方便您参考或直接使用。此时集成华为帐号的应用有自己的应用服务器，需要将idToken传到服务器上进行校验。
示例代码包中演示了：初始化华为帐号SDK，登录华为帐号获取授权码idToken、退出帐号和取消授权功能。

## 安装
如需使用本示例代码中的各种功能，请确保您的设备上已安装华为移动服务（HMS Core）5.0。

## 环境要求
使用HarmonyOS2.0及以上版本、JDK 1.8及以上版本。

## 配置

## 示例代码
鸿蒙应用使用idToken模式接入华为帐号场景的客户端Java示例代码提供了以下使用场景的演示：
1. 在应用初始化时，完成华为帐号SDK的初始化。
2. 静默登录+前台登录+获取用户信息（包括获取idToken）
3. 退出帐号
4. 帐号取消授权

## 代码说明
本Demo主要封装展示了账号SDK的相关能力，以及使用方法。
主要类和接口为：HuaweiAccountSDKProxy和HwidPresent
开发者看完演示后，可以直接拷贝这2个类，就能完成对帐号SDK的代码集成，即拷即用。

### public final class HuaweiAccountSDKProxy
华为帐号SDK能力的演示，主要封装在HuaweiAccountSDKProxy代理类中。
com.huawei.account.harmony.sz.hwid.HuaweiAccountSDKProxy
#### 方法说明：
**HuaweiAccountSDKProxy#initHuaweiAccountSDK**
初始化华为帐号SDK。
此方法中调用华为帐号SDK的初始化方法AccountAuthManager.init()进行初始化
在Application初始化时就调用一次，完成华为帐号SDK的初始化。

**HuaweiAccountSDKProxy#signIn**
登录华为帐号，演示了如何使用华为帐号登录。
演示的登录过程：先静默登录，如果静默登录成功，则从成功回调方法中获取AuthAccount用户信息，此时用户静默登录成功，获取到用户信息(包括idToken)，登录流程结束；
如果静默登录失败，则在失败回调方法中，显示调用前台登录授权signIn接口，进行登录授权。

**HuaweiAccountSDKProxy#signOut**
登出华为帐号

**HuaweiAccountSDKProxy#cancelAuthorization**
取消授权。
华为帐号取消授权后，再次静默登录将拉起授权页面

### public Interface HwidPresent
主要是接口类，用于在帐号相关SDK执行完毕后，提供和UI层的交互接口。
在HuaweiAccountSDKProxy代理类初始化时，通过构造函数注入。
com.huawei.account.harmony.sz.hwid.HwidPresent
#### 方法说明：
**com.huawei.account.harmony.sz.hwid.HwidPresent#updateUI**
更新UI，通过传递的参数AuthAccount对象，更新UI

**com.huawei.account.harmony.sz.hwid.HwidPresent#showInfo**
打印日志或者弹框，开发者可以自定义

## 运行结果
demo运行最后获取到用户的idToken。

## 更多详情
如需了解更多HMS Core相关信息，请前往[Reddit](https://www.reddit.com/r/HuaweiDevelopers/)社区获取HMS Core最新资讯，参与开发者讨论。
如您对示例代码使用有疑问，请前往：
* [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services)提问，上传问题时请打上huawei-mobile-services标签。
* [华为开发者论坛](https://developer.huawei.com/consumer/cn/forum/block/hms-core)，获得更多意见与建议。
如您在运行示例代码时出现错误，请到GitHub提交[issue](https://github.com/HMS-Core/huawei-account-demo/issues)或[pull request](https://github.com/HMS-Core/huawei-account-demo/pulls)。

## 授权许可
Android_Serverless_IdToken_Demo示例代码经过[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)授权许可。