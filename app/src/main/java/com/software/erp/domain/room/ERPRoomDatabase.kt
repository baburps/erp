package com.software.erp.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.software.erp.view.greyfabric.GreyFabricDetailsPO
import com.software.erp.view.greyfabric.GreyFabricDia
import com.software.erp.view.greyfabric.GreyFabricStructurePO
import com.software.erp.view.knitting.model.FabricDia
import com.software.erp.view.knitting.model.FabricStructurePO
import com.software.erp.view.knitting.model.KnittingProgramPO
import com.software.erp.view.yarnpurchase.YarnPurchasePO

@Database(
    version = 7 ,
    entities = [YarnPurchasePO::class ,
        KnittingProgramPO::class ,
        GreyFabricDetailsPO::class ,
        FabricStructurePO::class ,
        FabricDia::class ,
        GreyFabricStructurePO::class ,
        GreyFabricDia::class]
)
abstract class ERPRoomDatabase : RoomDatabase() {

    abstract fun erpDao(): ERPRoomDAO

    companion object {
        const val DATABASE_NAME = "DATA_BASE_ERP"
    }
}