package com.huawei.logger

/**
 * 功能描述
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