package com.software.erp.domain.model

import com.software.erp.common.utils.LoggerUtils

data class ResultHandler<out T>(val status: Status, val data: T?, val message: String) {

    enum class Status {
        SUCCESS,
        ERROR/*,
        LOADING*/
    }

    companion object {
        fun <T> success(data: T): ResultHandler<T> {
            LoggerUtils.debug("ResultHandler", "success")
            return ResultHandler(Status.SUCCESS, data, "")
        }

        fun <T> error(message: String, data: T? = null): ResultHandler<T> {
            LoggerUtils.debug("ResultHandler", "error")
            return ResultHandler(Status.ERROR, data, message)
        }

/*
        fun <T> loading(data: T? = null): ResultHandler<T> {
            return ResultHandler(Status.LOADING, data, "")
        }
*/
    }
}