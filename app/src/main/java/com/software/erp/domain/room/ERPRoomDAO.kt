package com.software.erp.domain.room

import androidx.room.*
import com.software.erp.view.knitting.KnittingProgramPO
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

    @Query("SELECT * FROM yarn_purchase_list where lotTrackName = :lotTrackName")
    fun fetchYarnPurchasesPO(lotTrackName: String): YarnPurchasePO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertYarnPurchaseDetails(yarnPurchasePO: YarnPurchasePO): Long

    @Update
    fun updateYarnPurchaseDetails(yarnPurchasePO: YarnPurchasePO): Int

    @Delete
    fun deleteYarnPurchaseDetails(yarnPurchasePO: YarnPurchasePO): Int

    @Delete
    fun deleteYarnPurchaseList(yarnPurchasePOList: List<YarnPurchasePO>)

//    ********Knitting********

    @Query("SELECT * FROM knitting_program_list")
    fun fetchAllKnittingProgram(): List<KnittingProgramPO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKnittingDetails(knittingProgramPO: KnittingProgramPO)

    @Update
    fun updateKnittingDetails(knittingProgramPO: KnittingProgramPO)
}