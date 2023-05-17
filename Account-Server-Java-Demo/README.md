## **HMS Core Account Kit Server-Side Sample Code**

English | [中文](README_ZH.md)


## **Contents**

 * [Introduction](#Introduction)
 * [Installation](#Installation)
 * [Configuration](#Configuration)
 * [Environment Requirements](#Environment-Requirements)
 * [Sample Code](#Sample-Code)
 * [License](#License)


## **Introduction**
The sample code for Android encapsulates the server-side APIs of Account Kit, containing multiple demo apps for your reference or direct use. The sample code offers:

**hmssample**: sample code package, which implements the following functions: sign-in, authorized sign-in, and sign-out

**logger**: records logs.

## **Installation**
To use capabilities offered in the sample code, make sure that HMS Core (APK) 4.0 has been installed on your device.
## **Environment Requirements**
Android SDK version: 23 or later

JDK version: 1.8 or later

## **Configuration**
Parameters in **Contant.java** include:

**CLIENT_ID**: app ID, which can be obtained from AppGallery Connect

**CERT_URL**: public key. To get it, obtain the public key URI from **jwks_uri** and then access this URI.

**ID_TOKEN_ISSUE**: same as the value of **iss** in the ID token

## **Sample Code**
The sample code shows how to implement the following functions:

1. Use the authorization code to obtain the access token in **TokenAPIDemo.java**.
2. Use the public key to verify the ID token in **IDTokenAPIDemo.java**.
3. Parse the access token in **GetTokenInfoAPIDemo.java**.
4. Parse and verify the ID token locally in **IDTokenParser.java**.

##  **License**
The sample code is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
