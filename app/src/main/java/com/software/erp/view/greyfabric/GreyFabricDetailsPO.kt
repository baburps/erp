package com.software.erp.view.greyfabric

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "grey_fabric_list")
data class GreyFabricDetailsPO constructor(
    @PrimaryKey(autoGenerate = true) var trackingID: Int = 0,
    @ColumnInfo var knittingDCNo: String = "",
    @ColumnInfo var SRKWDCNo: String = "",
    @ColumnInfo var date: String = "",
    @ColumnInfo var spinningMill: String = "",
    @ColumnInfo var lotTrackName: String = "",
    @ColumnInfo var goodsDesc: String = "",
    @ColumnInfo var orderRefNo: String = "",
    @ColumnInfo var fabricStructure: String = "",
    @ColumnInfo var machineGage: String = "",
    @ColumnInfo var loopLength: String = "",
    @ColumnInfo var dia: String = "",
    @ColumnInfo var programmedQtyInKgs: String = "",
    @ColumnInfo var receivedQtyInKgs: String = "",
    @ColumnInfo var shortageInKgs: String = "",
    @ColumnInfo var shortagePercentage: String = "",
    var showKnittingDetails: Boolean = false,
    var showShortageDetails: Boolean = false,
) : Serializable {
}