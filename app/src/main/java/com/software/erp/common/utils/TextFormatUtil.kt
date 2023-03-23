package com.software.erp.common.utils

import java.math.RoundingMode
import java.text.DecimalFormat

class TextFormatUtil {

    companion object Test {

        fun usernameFromEmail(email: String): String {
            return if (email.contains("@")) {
                email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            } else {
                email
            }
        }

        fun roundOffToTwoDigit(value: Double): String {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            return df.format(value)
        }
    }
}