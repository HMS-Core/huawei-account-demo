# huawei-account-demo

## Table of Contents

 * [Introduction](#introduction)
 * [Installation](#installation)
 * [Configurations](#configurations)
 * [Supported Environments](#supported-environments)
 * [Sample Code](#sample-code)
 * [License](#license)
 
 
## Introduction
Android sample code encapsulates APIs of the HUAWEI Account Kit server. It provides sample program for your reference or usage.
The following describes of Android sample code.

hmssample: Sample code packages. This package  contains code that implements Sign,  SignInCode and SignOut.
logger: This packages contains code that implements logger View.
    

## Installation
To use functions provided by examples, please make sure Huawei Mobile Service 4.0 has been installed on your cellphone.
## Supported Environments
Android SDK Version >= 23 and JDK version >= 1.8 is recommended.
	
## Configurations  
The following describes parameters in Contant.java
CLIENT_ID:  App ID, which can be obtained from AppGallery Connect website, please replace it with your app's client ID.
CERT_URL:  Request CERT_URL to get public key's URL from jwks_uri, then get public key.
ID_TOKEN_ISSUE:  The value that ISS string of ID Token should be equal to.
	
## Sample Code
The Account-Server-Java-Demo provides demonstration for following scenarios:
1. Authorization code to obtain Access Token.
2. Verify the ID Token with public key. 
3. Parsing Access Token.

The business logic of obtain Access Token is implemented in TokenAPIDemo.java, Verify the ID Token is implemented in IDTokenAPIDemo.java, and Parse Access Token is implemented in GetTokenInfoAPIDemo.java.
    

##  License
Account-kit Android sample is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
