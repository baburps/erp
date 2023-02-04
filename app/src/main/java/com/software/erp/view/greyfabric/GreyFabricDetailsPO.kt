package com.software.erp.view.greyfabric

import androidx.room.*
import com.software.erp.view.knitting.KnittingProgramPO
import java.io.Serializable

@Entity(
    tableName = "grey_fabric" , indices = [Index(value = ["knittingCompanyDCNo"] , unique = true)] ,
    foreignKeys = [ForeignKey(
        entity = KnittingProgramPO::class ,
        parentColumns = arrayOf("srkwDCNo") ,
        childColumns = arrayOf("knittingProgramSRKWDCNo") ,
        onDelete = ForeignKey.CASCADE
    )]
)
data class GreyFabricDetailsPO constructor(
    @PrimaryKey(autoGenerate = true) var id: Int = 0 ,
    @ColumnInfo var knittingProgramSRKWDCNo: String = "" ,//Used by self to track fabric
    @ColumnInfo var knittingCompanyDCNo: String = "" ,//Used by knitting company
    @Ignore var date: String = "" ,
    @ColumnInfo var spinningMill: String = "" ,
    @ColumnInfo var lotTrackName: String = "" ,
    @ColumnInfo var goodsDesc: String = "" ,
    @ColumnInfo var orderRefNo: String = "" ,
    @ColumnInfo var fabricStructure: String = "" ,
    @ColumnInfo var machineGage: String = "" ,
    @ColumnInfo var loopLength: String = "" ,
    @ColumnInfo var dia: String = "" ,
    @ColumnInfo var programmedQtyInKgs: String = "" ,
    @Ignore var remainingQtyInKgs: String = "" ,
    //Available Grey fabric in Stock, can be used in Dyeing program
    @ColumnInfo var receivedQtyInKgs: String = "" ,
    //Available Grey fabric in Stock, can be used in Dyeing program
    @ColumnInfo var stockQtyInKgs: String = "" ,
    @ColumnInfo var shortageInKgs: String = "" ,
    @ColumnInfo var shortagePercentage: String = "" ,
    @Ignore var showKnittingDetails: Boolean = false ,
    @Ignore var showShortageDetails: Boolean = false ,
) : Serializable {}