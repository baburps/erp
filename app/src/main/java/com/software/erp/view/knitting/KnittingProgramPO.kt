package com.software.erp.view.knitting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "knitting_program_list")
data class KnittingProgramPO constructor(
    @PrimaryKey(autoGenerate = true) var trackingID: Int,
    @ColumnInfo var dcNo: String,
    @ColumnInfo var date: String,
    @ColumnInfo var spinningMill: String,
    @ColumnInfo var lotTrackName: String,
    @ColumnInfo var goodsDesc: String,
    @ColumnInfo var orderRefNo: String,
    @ColumnInfo var fabricStructure: String = "",
    @ColumnInfo var machineGage: String = "",
    @ColumnInfo var loopLength: String = "",
    @ColumnInfo var dia: String = "",
    @ColumnInfo var qtyInKgs: String = ""
// TODO   @ColumnInfo var fabricStructureList: ArrayList<FabricStructurePO> = ArrayList()
) : Serializable {
    constructor() : this(0, "", "", "", "", "", "", "", "", "", "", "")
}

data class FabricStructurePO constructor(
    @ColumnInfo var fabricStructure: String = "",
    @ColumnInfo var machineGage: String = "",
    @ColumnInfo var loopLength: String = "",
    @ColumnInfo var fabricDiaList: ArrayList<FabricDia> = ArrayList()
) : Serializable

data class FabricDia constructor(
    @ColumnInfo var dia: String = "",
    @ColumnInfo var qtyInKgs: String = ""
) : Serializable