package com.boys.assets.accenture.helper

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import com.boys.assets.accenture.R

class TypeValidator {

    companion object {

        fun isNameValid(target: String): Boolean {
            return if (TextUtils.isEmpty(target)) {
                false
            } else {
                target.isNotEmpty()
            }
        }

        fun isEmailValid(target: CharSequence?): Boolean {
            return if (TextUtils.isEmpty(target)) {
                false
            } else {
                Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }

        fun isPhoneNumberValid(username: String): Boolean {
            if (username.isNotEmpty()){
                if (username.length > 1){
                    return username.substring(0, 2) == "62"
                }else{
                    return username.isNotBlank()
                }
            }else{
                return username.isNotBlank()
            }
        }

        fun isPasswordValid(password: String, length: Int): Boolean {
            return password.length > length
        }

        fun isOtpValid(context: Context, strOtp: String): String? {
            var strError : String? = null
            if (strOtp.length < 6){
                strError = context.getString(R.string.invalid_verification_code)
            }
            return strError
        }
    }

}