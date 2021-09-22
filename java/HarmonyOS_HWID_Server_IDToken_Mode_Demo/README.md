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
This sample code provides a sample of common HUAWEI Account Kit APIs for accessing HUAWEI ID in idToken mode when HarmonyOS servers are deployed. In this case, the application integrated with the Huawei ID has its own application server. You need to upload the idToken to the server for verification.
The sample code package demonstrates the following functions: Initializing the Huawei ID SDK, logging in to the Huawei ID to obtain the IDToken, logging out of the account, and canceling authorization.

## Installation
To use functions provided by examples, please make sure Huawei Mobile Service 5.0 has been installed on your cellphone.
## Supported Environments
HarmonyOS 2.0 or later and JDK 1.8 or later are used.

	
## Configurations  

	
## Sample Code
The Java sample code for the Harmony application to connect to a Huawei ID in idToken mode provides the following examples:
1. Initialize the HUAWEI ID SDK during app initialization.
2. Silent login + Frontend login + Obtaining user information (including obtaining the IDToken)
3. Log out of the account.
4. Cancel the authorization of the account.

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
Log in to a Huawei ID. This section describes how to log in to a Huawei ID.
Demonstrate the login process. If the login is successful, the system obtains the AuthAccount user information from the success callback method. In this case, the user successfully logs in in silent mode and obtains the user information (including the idToken). The login process ends.
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
The IDToken of the user is obtained after the demo is executed.

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
