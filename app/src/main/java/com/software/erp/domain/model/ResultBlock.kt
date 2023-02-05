package com.software.erp.domain.model

import java.io.Serializable

data class ResultBlock constructor(
    val resultCode: Int? = null,
    val resultDesc: String?=null
) : Serializable

open class ResultBlockWrapper(
    val result: ResultBlock? = null
) : Serializable
