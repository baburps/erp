package com.software.erp.view.programlist.adapter

import java.io.Serializable

data class ProgramChildAdapterPO(
    val programKey: String,
    val title: String,
    val desc: String,
    val entryObject: Any
) : Serializable
