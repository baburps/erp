package com.software.erp.view.greyfabric

import androidx.room.*
import com.software.erp.view.knitting.KnittingProgramPO
import java.io.Serializable

@Entity(
    tableName = "grey_fabric_list" , indices = [Index(value = ["knittingCompanyDCNo"] , unique = true)] ,
    foreignKeys = [ForeignKey(
        entity = KnittingProgramPO::class ,
        parentColumns = arrayOf("srkwDCNo") ,
        childColumns = arrayOf("srkwDCNo") ,
        onDelete = ForeignKey.CASCADE ,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class GreyFabricDetailsPO constructor(
    @PrimaryKey var srkwDCNo: String = "" ,//Used by self to track fabric
    @ColumnInfo var knittingCompanyDCNo: String = "" ,//Used by knitting company
    @ColumnInfo var date: String = "" ,
    @ColumnInfo var spinningMill: String = "" ,
    @ColumnInfo var lotTrackName: String = "" ,
    @ColumnInfo var goodsDesc: String = "" ,
    @ColumnInfo var orderRefNo: String = "" ,
    @ColumnInfo var fabricStructure: String = "" ,
    @ColumnInfo var machineGage: String = "" ,
    @ColumnInfo var loopLength: String = "" ,
    @ColumnInfo var dia: String = "" ,
    @ColumnInfo var programmedQtyInKgs: String = "" ,
    @ColumnInfo var receivedQtyInKgs: String = "" ,
    @ColumnInfo var shortageInKgs: String = "" ,
    @ColumnInfo var shortagePercentage: String = "" ,
    var showKnittingDetails: Boolean = false ,
    var showShortageDetails: Boolean = false ,
) : Serializable {}