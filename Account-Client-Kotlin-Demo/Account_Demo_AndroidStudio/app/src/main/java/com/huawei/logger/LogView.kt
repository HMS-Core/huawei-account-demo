package com.huawei.logger

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class LogView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), Log.LogNode {

    private var mNext: Log.LogNode? = null

    fun getNext(): Log.LogNode? {
        return mNext
    }

    fun setNext(node: Log.LogNode) {
        mNext = node
    }

    override fun println(priority: Int, tag: String, msg: String?, tr: Throwable?) {

        var priorityStr: String? = null

        when (priority) {
            Log.DEBUG -> priorityStr = "D"
            Log.INFO -> priorityStr = "I"
            Log.WARN -> priorityStr = "W"
            Log.ERROR -> priorityStr = "E"
            else -> {
            }
        }

        var exceptionStr: String? = null
        if (tr != null) {
            exceptionStr = android.util.Log.getStackTraceString(tr)
        }

        val outputBuilder = StringBuilder()

        val formatter = SimpleDateFormat("HH:mm:ss")
        val curDate = Date(System.currentTimeMillis())
        val str = formatter.format(curDate)
        outputBuilder.append(str)
        outputBuilder.append(" ")
        outputBuilder.append(msg)
        outputBuilder.append("\r\n")

        (context as Activity).runOnUiThread(Thread(Runnable { appendToLog(outputBuilder.toString()) }))
        mNext?.println(priority, tag, msg, tr)
    }

    private fun appendToLog(s: String) {
         append("\n" + s)
    }

    private fun appendIfNotNull(source: StringBuilder, addStr: String?, delimiter: String): StringBuilder {
        var delimiter = delimiter
        if (addStr != null) {
            if (addStr.length == 0) {
                delimiter = ""
            }

            return source.append(addStr).append(delimiter)
        }
        return source
    }


}
