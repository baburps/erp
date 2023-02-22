package com.software.erp.view.knitting

import androidx.room.*
import java.io.Serializable

@Entity(
    tableName = "knitting_program" ,
    indices = [Index(value = ["srkwDCNo"] , unique = true)]
)
data class KnittingProgramPO constructor(
    @PrimaryKey var srkwDCNo: String ,//Used by self to track fabric
    @ColumnInfo var date: String ,
    @ColumnInfo var spinningMill: String ,
    @ColumnInfo var lotTrackName: String ,
    @ColumnInfo var goodsDesc: String ,
    @ColumnInfo var orderRefNo: String ,
    @ColumnInfo var fabricStructure: String = "" ,
    @ColumnInfo var machineGage: String = "" ,
    @ColumnInfo var loopLength: String = "" ,
    @ColumnInfo var dia: String = "" ,
    @ColumnInfo var qtyInKgs: String = "" ,
    @Ignore var fabricStructureList: List<FabricStructurePO> = mutableListOf()
) : Serializable {
    constructor() : this(
        "" , "" , "" , "" ,
        "" , "" , "" , "" , "" , ""
    )
}

@Entity(
    tableName = "fabric_structure" ,
    foreignKeys = [ForeignKey(
        entity = KnittingProgramPO::class ,
        parentColumns = arrayOf("srkwDCNo") ,
        childColumns = arrayOf("knittingProgramSRKWDCNo") ,
        onDelete = ForeignKey.CASCADE
    )]
)
data class FabricStructurePO constructor(
    @PrimaryKey(autoGenerate = true) var id: Int = 0 ,
    @ColumnInfo var knittingProgramSRKWDCNo: String = "" ,//Used by self to track fabric
    @ColumnInfo var fabricStructure: String = "" ,
    @ColumnInfo var machineGage: String = "" ,
    @ColumnInfo var loopLength: String = "" ,
    @Ignore var fabricDiaList: MutableList<FabricDia> = mutableListOf()
) : Serializable

@Entity(
    tableName = "fabric_dia" ,
    foreignKeys = [ForeignKey(
        entity = FabricStructurePO::class ,
        parentColumns = arrayOf("id") ,
        childColumns = arrayOf("fabricStructureId") ,
        onDelete = ForeignKey.CASCADE
    )]
)
data class FabricDia constructor(
    @PrimaryKey(autoGenerate = true) var diaId: Int = 0 ,
    @ColumnInfo var fabricStructureId: Int = 0 ,
    @ColumnInfo var dia: String = "" ,
    @ColumnInfo var qtyInKgs: String = ""
) : Serializable