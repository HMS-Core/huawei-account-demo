package com.huawei.hmssample

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ScrollView
import com.huawei.logger.LogView


class LogFragment : Fragment() {

    private var mLogView: LogView? = null
    private var mScrollView: ScrollView? = null

    private fun inflateViews(): View? {
        mScrollView = ScrollView(activity)
        mScrollView?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        mLogView = LogView(activity)
        mLogView?.isClickable = true
        mScrollView?.addView(mLogView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT))
        return mScrollView
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val result = inflateViews()

        mLogView?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mScrollView?.post(Runnable {
                    mScrollView?.fullScroll(ScrollView.FOCUS_DOWN)
                })
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        val gestureDetector = GestureDetector(object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                mLogView?.text = ""
                return true
            }
        })

        mLogView?.setOnTouchListener(View.OnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
        })
        return result
    }


    fun getLogView(): LogView? {
        return mLogView
    }


}