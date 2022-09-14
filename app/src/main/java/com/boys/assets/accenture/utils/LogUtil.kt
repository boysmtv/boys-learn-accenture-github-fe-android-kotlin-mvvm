package com.boys.assets.accenture.utils

import io.michaelrocks.paranoid.Obfuscate
import android.text.TextUtils
import android.util.Log
import java.lang.StringBuilder
import java.util.*

@Obfuscate
object LogUtil {
    const val VERBOSE = 1
    const val DEBUG = 2
    const val INFO = 3
    const val WARN = 4
    const val ERROR = 5
    const val NOTHING = 6
    var LEVEL = VERBOSE
    fun setLevel(Level: Int) {
        LEVEL = Level
    }

    fun v(TAG: String, msg: String?) {
        if (LEVEL <= VERBOSE && !TextUtils.isEmpty(msg)) {
            MyLog(VERBOSE, TAG, msg)
        }
    }

    fun d(TAG: String, msg: String?) {
        if (LEVEL <= DEBUG && !TextUtils.isEmpty(msg)) {
            MyLog(DEBUG, TAG, msg)
        }
    }

    fun i(TAG: String, msg: String?) {
        if (LEVEL <= INFO && !TextUtils.isEmpty(msg)) {
            MyLog(INFO, TAG, msg)
        }
    }

    fun w(TAG: String, msg: String?) {
        if (LEVEL <= WARN && !TextUtils.isEmpty(msg)) {
            MyLog(WARN, TAG, msg)
        }
    }

    fun e(TAG: String, msg: String?) {
        if (LEVEL <= ERROR && !TextUtils.isEmpty(msg)) {
            MyLog(ERROR, TAG, msg)
        }
    }

    private fun MyLog(type: Int, TAG: String, msg: String?) {
        val stackTrace = Thread.currentThread().stackTrace
        val index = 4
        val className = stackTrace[index].fileName
        var methodName = stackTrace[index].methodName
        val lineNumber = stackTrace[index].lineNumber
        methodName =
            methodName.substring(0, 1).uppercase(Locale.getDefault()) + methodName.substring(1)
        val stringBuilder = StringBuilder()
        stringBuilder.append("[ (")
            .append(className)
            .append(":")
            .append(lineNumber)
            .append(")#")
            .append(methodName)
            .append(" ] ")
        stringBuilder.append(msg)
        val logStr = stringBuilder.toString()
        when (type) {
            VERBOSE -> Log.v(TAG, logStr)
            DEBUG -> Log.d(TAG, logStr)
            INFO -> Log.i(TAG, logStr)
            WARN -> Log.w(TAG, logStr)
            ERROR -> Log.e(TAG, logStr)
            else -> {}
        }
    }
}