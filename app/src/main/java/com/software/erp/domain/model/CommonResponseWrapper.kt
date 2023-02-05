package com.software.erp.domain.model

import java.io.Serializable

data class CommonResponseWrapper<T>(
    val data: T
) : Serializable, ResultBlockWrapper()
