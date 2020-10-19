# Accountkit-Android-sample


## Table of Contents

 * [Introduction](#introduction)
 * [Getting Started](#getting-started)
 * [Supported Environments](#supported-environments)
 * [Result](#result)
 * [License](#license)

 
## Introduction
    Android sample code encapsulates APIs of the HUAWEI Account Kit server. It provides sample program for your reference or usage.
    The following describes of Android sample code.

    hmssample:    Sample code packages. This package  contains code that implements Sign,  SignInCode and SignOut.
    logger:       This packages contains code that implements logger View.


## Getting Started

   1. Check whether the Android studio development environment is ready. Open the sample code project directory with file "build.gradle" in Android Studio. Run TestApp on your divice or simulator which have installed latest Huawei Mobile Service(HMS).
   2. Register a [HUAWEI account](https://developer.huawei.com/consumer/en/).
   3. Create an app and configure the app information in AppGallery Connect. 
        See details: [HUAWEI Account Kit Development Preparations](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/introduction-0000001050048870)
   4. To build this demo, please first import the demo in the Android Studio (3.x+).
   5. Configure the sample code:
        (1) Download the file "agconnect-services.json" of the app on AGC, and add the file to the app root directory(\app) of the demo.
        (2) Change the value of applicationid in the app-level build.gradle file of the sample project to the package name of your app.
   6. Run the sample on your Android device or emulator.


## Supported Environments
    Android SDK Version >= 23 and JDK version >= 1.8 is recommended.
 
##  Result
    This demo provides demonstration for following scenarios:
    1. ID-Token Mode Sign In.  Sign in and Id-Token verification are both included.
    2. Authorization Code Mode Sign In. 
    3. Sign out.
![account sample result](images/account_sample_result.jpg)

##  License
    Account-kit Android sample is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
