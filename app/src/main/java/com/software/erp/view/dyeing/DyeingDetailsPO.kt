package com.software.erp.view.dyeing

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dyeing_details" , indices = [Index(value = ["dyeingSRKWDCNo"] , unique = true)] ,
)
data class DyeingDetailsPO(
    @PrimaryKey(autoGenerate = true) var dyeingId: Int = 0 ,
    @ColumnInfo var dyeingSRKWDCNo: String = "" ,//New Separate DC No for self tracking
    @ColumnInfo var date: String = "" ,
    @ColumnInfo var dyeingUnitName: String = "" ,
    @ColumnInfo var orderNo: String = "" ,
    @ColumnInfo var color: String = "" ,
    @ColumnInfo var shade: String = "" ,
    @ColumnInfo var lot: String = "" ,
    @ColumnInfo var spinningMill: String = "" ,
    @ColumnInfo var goodsDesc: String = "" ,
    @ColumnInfo var fabricStructure: String = "" ,
    @ColumnInfo var machineGage: String = "" ,
    @ColumnInfo var loopLength: String = "" ,
    @ColumnInfo var dia: String = "" ,
    @ColumnInfo var qtyInKgs: String = "" ,
)
