# Android Serverless ID-Token Demo

English | [中文]() 


## Table of Contents

 * [Introduction](#introduction)
 * [Installation](#installation)
 * [Configurations](#configurations)
 * [Supported Environments](#supported-environments)
 * [Sample Code](#sample-code)
 * [Result](#result)
 * [License](#license)
 
## Introduction
This example provides code examples for quick access to Huawei IDs in HarmonyOS using common APIs of HUAWEI Account Kit for reference or use.
The sample code package demonstrates how to initialize the Huawei ID SDK and log in to the Huawei ID to obtain user information. (nickname, avatar, email address, UnionID, OpenID) . Logging out of an account and canceling authorization

## Installation
To use functions provided by examples, please make sure Huawei Mobile Service 5.0 has been installed on your cellphone.
## Supported Environments
HarmonyOS 2.0 or later and JDK 1.8 or later are used.

	
## Configurations  

	
## Sample Code
This example provides code examples for quick access to Huawei IDs in HarmonyOS using common APIs of HUAWEI Account Kit for reference or use.
The sample code package demonstrates how to initialize the Huawei ID SDK and log in to the Huawei ID to obtain user information. (nickname, avatar, email address, UnionID, OpenID) . Logging out of an account and canceling authorization

## Code Description
This demo encapsulates and displays the account SDK capabilities and usage methods.
Main classes and interfaces: HuaweiAccountSDKProxy and HwidPresent
After reading the demo, you can directly copy the two classes to integrate the code of the account SDK.

### public final class HuaweiAccountSDKProxy
The demonstration of Huawei ID SDK capabilities is encapsulated in the HuaweiAccountSDKProxy proxy class.
com.huawei.account.harmony.sz.hwid.HuaweiAccountSDKProxy
#### Method Description：
**HuaweiAccountSDKProxy#initHuaweiAccountSDK**
Initialize the Huawei ID SDK.
In this method, the initialization method AccountAuthManager.init() of the Huawei ID SDK is invoked for initialization.
This interface is invoked once during application initialization to initialize the Huawei ID SDK.

**HuaweiAccountSDKProxy#signIn**
Log in to a Huawei ID. This section describes how to log in using a Huawei ID.
Demonstrated login process: Login in silent mode. If the login is successful, the system obtains the AuthAccount user information from the success callback method. In this case, the user successfully logs in in in silent mode and obtains the user information. (nickname, avatar, email address, UnionID, OpenID) The login process ends.
If the silent login fails, the login authorization signIn interface is invoked for login authorization in the failure callback method.

**HuaweiAccountSDKProxy#signOut**
Log out of Huawei ID

**HuaweiAccountSDKProxy#cancelAuthorization**
Cancel the authorization.
After the authorization of Huawei ID is canceled, the authorization page will be displayed when you log in in silently again.

### public Interface HwidPresent
Interface class, which is used to provide interaction interfaces with the UI layer after the account-related SDK is executed.
When the HuaweiAccountSDKProxy proxy class is initialized, injection is performed through the constructor function.
com.huawei.account.harmony.sz.hwid.HwidPresent
#### Method Description：
**com.huawei.account.harmony.sz.hwid.HwidPresent#updateUI**
Updates the UI based on the transferred parameter AuthAccount object.

**com.huawei.account.harmony.sz.hwid.HwidPresent#showInfo**
Logs or dialog boxes are printed, which can be customized by developers.

## Result
The demo runs and obtains the account object AuthAccount, including the nickname, avatar, email address, UnionID, and OpenID.

## Question or issues
If you want to evaluate more about HMS Core,
[r/HMSCore on Reddit](https://www.reddit.com/r/HuaweiDevelopers/) is for you to keep up with latest news about HMS Core, and to exchange insights with other developers.

If you have questions about how to use HMS samples, try the following options:
- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with 
`huawei-mobile-services`.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.

If you run into a bug in our samples, please submit an [issue](https://github.com/HMS-Core/huawei-account-demo/issues) to the Repository. Even better you can submit a [Pull Request](https://github.com/HMS-Core/huawei-account-demo/pulls) with a fix.

##  License
Android Serverless ID-Token Demo is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
