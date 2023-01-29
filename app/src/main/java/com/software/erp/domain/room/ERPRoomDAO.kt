package com.software.erp.domain.room

import androidx.room.*
import com.software.erp.view.yarnpurchase.YarnPurchasePO

@Dao
interface ERPRoomDAO {

    @Query("SELECT * FROM yarn_purchase_list")
    fun fetchAllYarnPurchases(): List<YarnPurchasePO>

    @Query("SELECT spinningMill FROM yarn_purchase_list")
    fun fetchSpinningMills(): List<String>

    @Query("SELECT lotTrackName FROM yarn_purchase_list where spinningMill = :millName")
    fun fetchLotTrackName(millName: String): List<String>

    @Query("SELECT goodsDesc FROM yarn_purchase_list where lotTrackName = :lotTrackName")
    fun fetchGoodsDesc(lotTrackName: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(yarnPurchasePO: YarnPurchasePO): Long

    @Update
    fun update(yarnPurchasePO: YarnPurchasePO)

    @Delete
    fun delete(yarnPurchasePO: YarnPurchasePO)

    @Delete
    fun deleteYarnPurchaseList(yarnPurchasePOList: List<YarnPurchasePO>)
}