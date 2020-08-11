# HUAWEI Account Demo

## Table of Contents

 * [Introduction](#introduction)
 * [Installation](#installation)
 * [Configurations](#configurations)
 * [Supported Environments](#supported-environments)
 * [Sample Code](#sample-code)
 * [Result](#result)
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

This Account-Client-Java-Demo provides demonstration for following scenarios:
1. ID-Token Mode Sign In.  Sign in and Id-Token verification are both included.
2. Authorization Code Mode Sign In. 
3. Sign out.

The business logic of ID-Token Mode Sign In and Authorization Code Mode Sign In are implemented in HuaweiIdActivity.java while Id-Token verification are implemented in  IDTokenParse.java.

## Result
The Account-Server-Java-Demo print the result of access token, jwt and content of access token.

## Question or issues
If you have questions about how to use HMS samples, try the following options:
- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with 
**huawei-mobile-services**.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.

If you run into a bug in our samples, please submit an [issue](https://github.com/HMS-Core/huawei-account-demo/issues) to the Repository. Even better you can submit a [Pull Request](https://github.com/HMS-Core/huawei-account-demo/pulls) with a fix.

##  License
Account-kit Android sample is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
