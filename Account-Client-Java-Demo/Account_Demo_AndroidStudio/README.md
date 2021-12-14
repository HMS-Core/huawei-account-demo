# HMS Core Account Kit Client-Side Sample Code (Java)

English | [中文](README_ZH.md)

## Contents
* [Introduction](#Introduction)
* [Environment Requirements](#Environment-Requirements)
* [Preparations](#Preparations)
* [Result](#Result)
* [License](#License)

## Introduction
The sample code for Android encapsulates the client-side APIs of Account Kit, containing multiple demo apps for your reference or direct use. The sample code offers:

**hmssample**: sample code package, which implements the following functions: sign-in, authorized sign-in, silent sign-in, sign-out, and authorization revoking
**logger**: records logs.

## Environment Requirements
Android SDK version: 23 or later

JDK version: 1.8 or later

## Preparations
1. Install Android Studio on your computer. In Android Studio, open the sample code project directory where the **build.gradle** file is. Run and test the demo app on a simulator or a device where the latest HMS Core (APK) has been installed.
2. [Register a HUAWEI ID](https://developer.huawei.com/consumer/en/).
3. Create an app and configure its information in AppGallery Connect. For details, please refer to [Configuring App Information in AppGallery Connect](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/config-agc-0000001050196065?ha_source=hms1).
4. Import the sample code into Android Studio (3.0 or later) before building the demo app.
5. Configure the sample code.
     (1) Download the **agconnect-services.json** file of your app from AppGallery Connect. Copy the file to your app's root directory.
     (2) Replace **applicationid** in the app-level **build.gradle** file with the package name of your app.
6. Run the demo app on an Android device or a simulator.

## Result
The sample code shows how to implement the following functions:
1. Sign-in in ID token mode
2. Sign-in in authorization code mode
3. Silent sign-in
4. Sign-out from an ID
5. Authorization revocation

![account sample result](images/account_sample_result.jpg)

## License
The sample code is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
