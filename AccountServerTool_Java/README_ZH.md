# 华为帐号服务accountservertool示例代码

中文 | [English](https://github.com/HMS-Core/huawei-account-demo/blob/master/Account-Client-Java-Demo/Account_Demo_AndroidStudio/README.md) 

## 目录
* [简介](#简介)
* [环境要求](#环境要求)
* [开发准备](#开发准备)
* [示例代码](#示例代码)
* [授权许可](#授权许可)

## 简介
accountservertool提供了基于Java语言的Code模式服务器端rest接口的封装和ID-Token模式服务器端校验工具类，
让您快速完成服务器端代码开发，您也可以参考该部分源码用自己的方式实现对应功能.

## 环境要求
推荐使用JDK 1.8及以上版本。

## 开发准备
1.在AGC上创建对应的应用，如使用code模式，配置好rediret uri 
2.根据不同的场景在服务器端获取code或者idtoken。

## 示例代码
accountservertool示例代码提供了以下使用场景：
1. 使用Authorization Code模式接入华为帐号场景，代码中提供了Code模式服务器端rest接口的封装和使用过程样例，包括错误码的处理。
2. 使用有服务器ID-Token模式接入华为帐号场景，代码中提供了ID-Token服务器端校验工具类，供开发者参考或直接使用。

## 授权许可
华为帐号服务Android示例代码经过[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)授权许可。