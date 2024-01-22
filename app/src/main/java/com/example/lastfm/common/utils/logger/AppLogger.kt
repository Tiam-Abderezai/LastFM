package com.example.lastfm.common.utils.logger

import android.util.Log
import com.example.lastfm.BuildConfig

class AppLogger(private val tag: String) : BaseLogger {

    override fun debug(message: String) {
        if (BuildConfig.DEBUG) Log.d(tag, message)
    }

    override fun info(message: String) {
        Log.i(tag, message)
    }

    override fun warn(message: String) {
        Log.w(tag, message)
    }

    override fun error(message: String) {
        Log.e(tag, message)
    }

    override fun error(message: String, throwable: Throwable) {
        Log.e(tag, message, throwable)
    }
}
