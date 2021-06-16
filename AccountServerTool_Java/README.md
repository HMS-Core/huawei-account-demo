# accountservertool sample

English | [中文](https://github.com/HMS-Core/huawei-account-demo/blob/master/Account-Client-Java-Demo/Account_Demo_AndroidStudio/README_ZH.md) 


## Table of Contents

 * [Introduction](#introduction)
 * [Supported Environments](#supported-environments)
 * [Getting Started](#getting-started)
 *  [Sample Code](# Sample Code)
 * [License](#license)

 
## Introduction
accountservertool provides Java-based Code mode server-side rest interface encapsulation and ID-Token mode server-side verification tool classes,
Allows you to quickly complete server-side code development. You can also refer to this part of the source code to implement the corresponding functions in your own way.

## Supported Environments
JDK version >= 1.8 is recommended.

## Getting Started
1.Create a application on AGC, configure rediret uri if you want to use Code mode
2. Obtain code or idtoken on the server side according to different scenarios.
 
## Sample Code
The accountservertool sample code provides the following usage scenarios:
1. Use Authorization Code mode to access the HUAWEI ID scene. The code provides an example of the encapsulation and usage process of the rest interface of the Code mode server, including error code processing.
2. Use the server ID-Token mode to access the HUAWEI ID scene. The ID-Token server-side verification tool is provided in the code for developers' reference or direct use.

## License
accountservertool sample is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
