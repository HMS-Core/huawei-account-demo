# HMS Core Account Kit Sample Code

English | [中文](README_ZH.md)

## Contents
* [Introduction](#Introduction)
* [Installation](#Installation)
* [Environment Requirements](#Environment-Requirements)
* [Configuration](#Configuration)
* [Sample Code](#Sample-Code)
* [Result](#Result)
* [Technical Support](#Technical-Support)
* [License](#License)

## Introduction
The sample code for Android encapsulates the server-side APIs of Account Kit, containing multiple demo apps for your reference or direct use. The sample code offers:
**hmssample**: sample code package, which implements the following functions: sign-in, authorized sign-in, and sign-out
**logger**: records logs.

## Installation
To use capabilities offered in the sample code, make sure that HMS Core (APK) 4.0 has been installed on your device.
## Environment Requirements
Android SDK version: 23 or later

JDK version: 1.8 or later

## Configuration
Parameters in **Contant.java** include:
**CLIENT_ID**: app ID, which can be obtained from AppGallery Connect
**CERT_URL**: public key. To get it, obtain the public key URI from **jwks_uri** and then access this URI.
**ID_TOKEN_ISSUE**: same as the value of **iss** in the ID token

## Sample Code
The Java sample code for the server-side shows how to implement the following functions:
1. Use the authorization code to obtain the access token in **TokenAPIDemo.java**.
2. Use the public key to verify the ID token in **IDTokenAPIDemo.java**.
3. Parse the access token in **GetTokenInfoAPIDemo.java**.

The Java sample code for the client-side shows how to implement the following functions:
1. Sign-in in ID token mode.
2. Sign-in in authorization code mode.
3. Silent sign-in.
4. Sign-out from an ID.
5. Authorization revocation.

Service logic: Implement sign-in in ID token mode and authorization code mode in [AccountActivity.java](https://github.com/HMS-Core/huawei-account-demo/blob/master/Account-Client-Java-Demo/Account_Demo_AndroidStudio/app/src/main/java/com/huawei/hmssample/AccountActivity.java).

## Result
The server-side Java sample code prints the access token and JSON Web Token.

## Technical Support
You can visit the [Reddit community](https://www.reddit.com/r/HuaweiDevelopers/) to obtain the latest information about HMS Core and communicate with other developers.
If you have any questions about the sample code, try the following:
* Visit [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services?tab=Votes), submit your questions, and tag them with `huawei-mobile-services`. Huawei experts will answer your questions.

* Visit the HMS Core section in the [HUAWEI Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001?ha_source=hms1) and communicate with other developers. 

  If you encounter any issues when using the sample code, submit your [issues](https://github.com/HMS-Core/huawei-account-demo/issues) or submit a [pull request](https://github.com/HMS-Core/huawei-account-demo/pulls).

## License
The sample code is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
