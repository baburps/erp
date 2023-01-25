package com.software.erp.domain.room

import androidx.room.*
import com.software.erp.view.yarnpurchase.YarnPurchasePO

@Dao
interface YarnPurchaseRoomDAO {

    @Query("SELECT * FROM yarn_purchase_list")
    fun fetchAllYarnPurchases(): List<YarnPurchasePO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(yarnPurchasePO: YarnPurchasePO): Long

    @Update
    fun update(vararg yarnPurchasePO: YarnPurchasePO)

    @Delete
    fun delete(yarnPurchasePO: YarnPurchasePO)

    @Delete
    fun deleteYarnPurchaseList(yarnPurchasePOList: List<YarnPurchasePO>)
}