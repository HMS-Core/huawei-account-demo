package com.huawei.logger

import android.app.Activity
import com.huawei.hmssample.LogFragment
import com.huawei.hmssample.R

open class LoggerActivity :Activity() {

    override fun onStart() {
        // TODO Auto-generated method stub
        super.onStart()
        initializeLogging()
    }

    private fun initializeLogging() {
        val logFragment =  fragmentManager.findFragmentById(R.id.framelog) as LogFragment
        val logcat = LogCatWrapper()
        logcat.setNext(logFragment.getLogView()!!)
        Log.setLogNode(logcat)
    }
}