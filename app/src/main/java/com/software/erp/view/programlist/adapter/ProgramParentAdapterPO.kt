package com.software.erp.view.programlist.adapter

import java.io.Serializable

data class ProgramParentAdapterPO(
    val programKey: String,
    val listOfEntries: List<ProgramChildAdapterPO>
) : Serializable
