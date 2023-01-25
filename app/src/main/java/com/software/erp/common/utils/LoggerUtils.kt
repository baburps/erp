package com.software.erp.common.utils

import android.util.Log

class LoggerUtils {

    companion object {
        fun debug(tag: String?, message: String?) {
            message?.let { Log.d(tag, it) }
        }

        fun warn(tag: String?, message: String?) {
            message?.let { Log.w(tag, it) }
        }
    }
}