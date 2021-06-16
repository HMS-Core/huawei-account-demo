# Sample Code for Scenario-based Access to Huawei IDs for Mobile and Smart Screen Applications

English | [Chinese](https://github.com/HMS-Core/huawei-account-demo/blob/android_scenarios_demo/README_ZH.md)

## Directory

* [Introduction] (#Introduction)
* [Install] (#Install)
* [Environment requirements] (#Environment requirements)
* [Configure] (#Configure)
* [Sample Code](#Sample Code)
* [Running Result](#Running Result)
* [license] (#license)

## Introduction

This section provides code examples for using common APIs of HUAWEI Account Kit in different scenarios for reference or direct use.

Scenarios include:

1. Quickly access Huawei ID using mobile and smart screen apps.
2. Access Huawei IDs in server-free ID-Token mode for mobile and smart screens.
3. Mobile and smart screen apps can access Huawei IDs in server ID-token mode.
4. Access the Huawei ID in code mode on the mobile and smart screens.

Implemented interfaces and logic:
Account login authorization, silent login, silent login failure processing, account logout, and authorization cancellation.

## Installation

To use the functions in this sample code, ensure that Huawei Mobile Service (HMS Core) 5.0 has been installed on your device.

## Environment Requirements

Android SDK 23 or later and JDK 1.8 or later are recommended.

## Configure

## Sample Code

Scenario-based access to Huawei ID for mobile and smart screens provides the following sample code:

1. Quickly access your Huawei ID using mobile and smart screens to obtain the user's nickname, avatar, email address, UnionID, and OpenID.
2. The mobile and smart screen apps access the Huawei ID in server-free ID-Token mode, obtain the ID-Token, and verify the ID-Token locally.
3. Mobile and smart screen apps access Huawei IDs in server ID-token mode to obtain ID-tokens.
4. Access your Huawei ID in Code mode and obtain your code.

## Running result

In the sample code, when you click the Huawei ID login button, the Huawei ID login authorization page is displayed and user information is obtained. When you click the account exit button, the cache is cleared. When you click the Cancel Authorization button, the application authorization is canceled.

## More Details

To learn more about the HMS Core, visit the [Reddit](https://www.reddit.com/r/HuaweiDevelopers/) community to obtain the latest HMS Core news and participate in the developer discussion.
If you have questions about using the sample code, go to:

* [Stack Overflow] (https://stackoverflow.com/questions/tagged/huawei-mobile-services). When uploading a question, attach the huawei-mobile-services tag.
* [Huawei Developer Forum] (https://developer.huawei.com/consumer/cn/forum/block/hms-core) for more comments and suggestions.
  If an error occurs when you run the sample code, submit a [issue](https://github.com/HMS-Core/huawei-account-demo/issues) or [pull request](https://github.com/HMS-Core/huawei-account-demo/pulls) on GitHub.

## License

The sample code for scenario-based access to Huawei IDs for mobile and smart screens is authorized by [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
