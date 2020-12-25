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
package com.huawei.logger

import android.app.Activity
import com.huawei.hmssample.LogFragment
import com.huawei.hmssample.R

open class LoggerActivity : Activity() {

    override fun onStart() {
        super.onStart()
        initializeLogging()
    }

    private fun initializeLogging() {
        val logFragment = fragmentManager.findFragmentById(R.id.framelog) as LogFragment

        val logcat = LogCatWrapper()
        logcat.setNext(logFragment.getLogView()!!)
        Log.setLogNode(logcat)
    }
}