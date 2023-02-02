package com.software.erp.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.software.erp.view.knitting.KnittingProgramPO
import com.software.erp.view.yarnpurchase.YarnPurchasePO

@Database(entities = [YarnPurchasePO::class, KnittingProgramPO::class], version = 2)
abstract class ERPRoomDatabase : RoomDatabase() {

    abstract fun yarnPurchaseDao(): ERPRoomDAO

    companion object {
        const val DATABASE_NAME = "DATA_BASE_ERP"
    }
}