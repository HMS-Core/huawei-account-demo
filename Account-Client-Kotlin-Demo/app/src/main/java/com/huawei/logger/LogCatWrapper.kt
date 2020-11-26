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

/**
 * Function Description
 *
 * @since 2020-08-27
 */
class LogCatWrapper : Log.LogNode {
    private var mNext: Log.LogNode? = null

    fun getNext(): Log.LogNode? {
        return mNext
    }

    fun setNext(node: Log.LogNode) {
        mNext = node
    }

    override fun println(priority: Int, tag: String, msg: String?, tr: Throwable?) {
        var useMsg = msg
        if (useMsg == null) {
            useMsg = ""
        }

        if (tr != null) {
            useMsg += "\n" + android.util.Log.getStackTraceString(tr)
        }

        android.util.Log.println(priority, tag, useMsg)

        if (mNext != null) {
            mNext!!.println(priority, tag, msg, tr)
        }
    }
}