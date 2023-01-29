package com.software.erp.domain.room

import androidx.room.*
import com.software.erp.view.yarnpurchase.YarnPurchasePO

@Dao
interface ERPRoomDAO {

    @Query("SELECT * FROM yarn_purchase_list")
    fun fetchAllYarnPurchases(): List<YarnPurchasePO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(yarnPurchasePO: YarnPurchasePO): Long

    @Update
    fun update(yarnPurchasePO: YarnPurchasePO)

    @Delete
    fun delete(yarnPurchasePO: YarnPurchasePO)

    @Delete
    fun deleteYarnPurchaseList(yarnPurchasePOList: List<YarnPurchasePO>)
}