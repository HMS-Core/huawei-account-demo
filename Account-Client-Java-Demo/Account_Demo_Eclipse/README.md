# HMS Core Account Kit Client-Side Sample Code (Eclipse)

English | [中文](README_ZH.md)

## Contents
* [Introduction](#Introduction)
* [Installation](#Installation)
* [Environment Requirements](#Environment-Requirements)
* [Configuration](#Configuration)
* [Quick Tutorial](#Quick-Tutorial)
* [Sample Code](#Sample-Code)
* [License](#License)

## Introduction
The sample code for Android encapsulates the client-side APIs of Account Kit, containing multiple demo apps for your reference or direct use. The sample code offers:
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

## Quick Tutorial
1. Add build dependencies. For details, please refer to [Integrating the HMS Core SDK into Your App Project in the Eclipse IDE](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/ep-integration-hms-core-sdk-0000001050309714?ha_source=hms1).

2. Right-click the project in Eclipse. Click **Export**.
![image.png](http://image.huawei.com/tiny-lts/v1/images/d9a59bb5b945f8c95979be3b7b3b1d37_511x542.png@900-0-90-f.png)

3. Click **Next**. The **Keystore selection** page is displayed.

4. Select **Use existing keystore** and click **Browse**. Select the **lightregion.jks** file and enter **android** as the password.
![image.png](http://image.huawei.com/tiny-lts/v1/images/f614cfefc03ee9dbbc1dba2daeedc304_510x542.png@900-0-90-f.png)

5. Select **Use existing key**. Set **Alias** to **androiddebugkey**. Enter **android** as the password.
![image.png](http://image.huawei.com/tiny-lts/v1/images/d70ab5ecddbd53eb5ba63c239f7efa1f_510x542.png@900-0-90-f.png)

6. Click **Browse**. Specify a path for the demo app (APK) to be exported. Click **Finish**.
![image.png](http://image.huawei.com/tiny-lts/v1/images/db9fd9f9b2ebd23f3f99685bacc30192_511x540.png@900-0-90-f.png)

7. Install and launch the demo app. A screen will show as follows.
![image.png](http://image.huawei.com/tiny-lts/v1/images/b3e3cf07c5da4ddd419559ba7e76fd30_276x599.png@900-0-90-f.png)

## Sample Code
The sample code shows how to implement the following functions:
1. Sign-in in ID token mode. This mode includes sign-in and ID token verification.
2. Sign-in in authorization code mode.
3. Sign-out from an ID.

Service logic: Implement sign-in in ID token mode and authorization mode in **HuaweiIdActivity.java**. Implement ID token verification in **IDTokenParse.java**.

## License
The sample code is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
