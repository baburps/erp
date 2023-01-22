package com.software.erp.common.utils

class TextFormatUtil {

    companion object Test {

        fun usernameFromEmail(email: String): String {
            return if (email.contains("@")) {
                email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            } else {
                email
            }
        }

    }
}