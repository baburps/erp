package com.software.erp.view.greyfabric.model

import androidx.room.DatabaseView

@DatabaseView(
    "SELECT student_table.id, student_table.name, student_table.departmentId," +
            "department.name AS departmentName FROM student_table " +
            "INNER JOIN department ON student_table.departmentId = department.id"
)
data class FetchGreyFabricDetailsPO(
    var id: Int = 0 ,
    var knittingProgramSRKWDCNo: String = "" ,
    var fabricStructure: String = "" ,
    var machineGage: String = "" ,
    var loopLength: String = "" ,
)
