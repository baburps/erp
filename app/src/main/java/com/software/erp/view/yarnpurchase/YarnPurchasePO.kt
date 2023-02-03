package com.software.erp.view.yarnpurchase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "yarn_purchase")
data class YarnPurchasePO constructor(
    @ColumnInfo var invoiceNo: String,
    @ColumnInfo var date: String,
    @ColumnInfo var spinningMill: String,
    @ColumnInfo var goodsDesc: String,
    @ColumnInfo var noOfBags: String,
    @ColumnInfo var qtyInKgs: String,
    @ColumnInfo var price: String,
    @ColumnInfo var gst: String,
    @ColumnInfo var value: String,
    @ColumnInfo var lotTrackName: String,
    @ColumnInfo var currentQtyInKgs: String,
    @PrimaryKey(autoGenerate = true) var trackingID: Int
) : Serializable {
    constructor() :
            this("", "",
                "", "",
                "", "",
                "", "", "",
                "","", 0)
}
