/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.huawei.logger

object Log {

    interface LogNode {
        fun println(priority: Int, tag: String, msg: String?, tr: Throwable?)
    }

    const val DEBUG = android.util.Log.DEBUG
    const val INFO = android.util.Log.INFO
    const val WARN = android.util.Log.WARN
    const val ERROR = android.util.Log.ERROR

    private var mLogNode: LogNode? = null

    fun getLogNode(): LogNode? {
        return mLogNode
    }

    fun setLogNode(node: LogNode) {
        mLogNode = node
    }

    fun d(tag: String, msg: String, tr: Throwable?) {
        println(DEBUG, tag, msg, tr)
    }

    fun d(tag: String, msg: String) {
        d(tag, msg, null)
    }

    fun i(tag: String, msg: String, tr: Throwable?) {
        println(INFO, tag, msg, tr)
    }

    fun i(tag: String, msg: String) {
        i(tag, msg, null)
    }

    fun w(tag: String, msg: String?, tr: Throwable?) {
        println(WARN, tag, msg, tr)
    }

    fun w(tag: String, msg: String) {
        w(tag, msg, null)
    }

    fun w(tag: String, tr: Throwable) {
        w(tag, null, tr)
    }

    fun e(tag: String, msg: String, tr: Throwable?) {
        println(ERROR, tag, msg, tr)
    }

    fun e(tag: String, msg: String) {
        e(tag, msg, null)
    }

    fun println(priority: Int, tag: String, msg: String?, tr: Throwable?) {
        if (mLogNode != null) {
            mLogNode!!.println(priority, tag, msg, tr)
        }
    }

    fun println(priority: Int, tag: String, msg: String) {
        println(priority, tag, msg, null)
    }
}