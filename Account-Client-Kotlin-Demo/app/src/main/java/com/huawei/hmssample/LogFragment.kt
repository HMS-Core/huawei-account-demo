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