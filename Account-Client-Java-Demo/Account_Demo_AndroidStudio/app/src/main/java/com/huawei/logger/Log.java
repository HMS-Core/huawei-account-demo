/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.huawei.logger;

public class Log {

    public static interface LogNode {

        public void println(int priority, String tag, String msg, Throwable tr);

    }

    public static final int DEBUG = android.util.Log.DEBUG;
    public static final int INFO = android.util.Log.INFO;
    public static final int WARN = android.util.Log.WARN;
    public static final int ERROR = android.util.Log.ERROR;

    private static LogNode mLogNode;

    public static LogNode getLogNode() {
        return mLogNode;
    }

    public static void setLogNode(LogNode node) {
        mLogNode = node;
    }

    public static void d(String tag, String msg, Throwable tr) {
        println(DEBUG, tag, msg, tr);
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    public static void i(String tag, String msg, Throwable tr) {
        println(INFO, tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }

    public static void w(String tag, String msg, Throwable tr) {
        println(WARN, tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(String tag, Throwable tr) {
        w(tag, null, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        println(ERROR, tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void println(int priority, String tag, String msg, Throwable tr) {
        if (mLogNode != null) {
            mLogNode.println(priority, tag, msg, tr);
        }
    }

    public static void println(int priority, String tag, String msg) {
        println(priority, tag, msg, null);
    }

}
