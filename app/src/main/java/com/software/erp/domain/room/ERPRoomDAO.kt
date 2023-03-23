package com.software.erp.domain.room

import androidx.room.*
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.view.greyfabric.GreyFabricDetailsPO
import com.software.erp.view.greyfabric.GreyFabricDia
import com.software.erp.view.greyfabric.GreyFabricStructurePO
import com.software.erp.view.greyfabric.GreyFabricWrapper
import com.software.erp.view.greyfabric.model.GreyFabricStructureWrapper
import com.software.erp.view.knitting.model.FabricDia
import com.software.erp.view.knitting.model.FabricStructurePO
import com.software.erp.view.knitting.model.KnittingProgramFabricStructureWrapper
import com.software.erp.view.knitting.model.KnittingProgramPO
import com.software.erp.view.yarnpurchase.YarnPurchasePO

@Dao
interface ERPRoomDAO {

    @Query("SELECT * FROM yarn_purchase")
    fun fetchAllYarnPurchases(): List<YarnPurchasePO>

    @Query("SELECT spinningMill FROM yarn_purchase")
    fun fetchSpinningMills(): List<String>

    @Query("SELECT lotTrackName FROM yarn_purchase where spinningMill = :millName")
    fun fetchLotTrackName(millName: String): List<String>

    @Query("SELECT goodsDesc FROM yarn_purchase where lotTrackName = :lotTrackName")
    fun fetchGoodsDesc(lotTrackName: String): List<String>

    @Query("SELECT * FROM yarn_purchase where lotTrackName = :lotTrackName")
    fun fetchYarnPurchasesPO(lotTrackName: String): YarnPurchasePO

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertYarnPurchaseDetails(yarnPurchasePO: YarnPurchasePO): Long

    @Update
    fun updateYarnPurchaseDetails(yarnPurchasePO: YarnPurchasePO): Int

    @Delete
    fun deleteYarnPurchaseDetails(yarnPurchasePO: YarnPurchasePO): Int

    @Delete
    fun deleteYarnPurchaseList(yarnPurchasePOList: List<YarnPurchasePO>)

//    ********Knitting********

    @Query("SELECT * FROM knitting_program")
    fun fetchAllKnittingProgram(): List<KnittingProgramPO>

    @Query("SELECT * FROM knitting_program where srkwDCNo = :dcNo")
    fun searchKnittingProgramWithDCNo(dcNo: String): KnittingProgramPO?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertKnittingDetails(knittingProgramPO: KnittingProgramPO)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertKnittingFabricStructure(fabricStructurePO: FabricStructurePO): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertKnittingFabricDia(fabricDia: FabricDia)

    fun insertKnittingDetailsWithFabricList(knittingProgramPO: KnittingProgramPO) {
        //TODO add logic to remove added row incase of failure in child table
        insertKnittingDetails(knittingProgramPO)

        knittingProgramPO.fabricStructureList.forEach { fabricStructurePo ->
            //Map foreign key to Fabric Structure PO
            fabricStructurePo.knittingProgramSRKWDCNo = knittingProgramPO.srkwDCNo
            val id = insertKnittingFabricStructure(fabricStructurePo)

            fabricStructurePo.fabricDiaList.forEach { fabricDia ->
                //Map foreign key to Dia PO
                fabricDia.fabricStructureId = id.toInt()
                insertKnittingFabricDia(fabricDia)
            }
        }
    }

    @Update
    fun updateKnittingDetails(knittingProgramPO: KnittingProgramPO)

    @Transaction
    @Query("SELECT * FROM fabric_structure")
    fun getKnittingFabricStructureList(): List<KnittingProgramFabricStructureWrapper>

    @Transaction
    @Query("SELECT * FROM fabric_structure where fabric_structure.knittingProgramSRKWDCNo = :srkwDCNO")
    fun getKnittingFabricStructureList(srkwDCNO: String): List<GreyFabricStructureWrapper>

    //    ********Grey Fabric********

    @Insert
    fun insertGreyFabricDetails(gretFabricDetailsPO: GreyFabricDetailsPO): Long

    fun insertGreyFabricDetailsWithFabricList(greyFabricDetailsPO: GreyFabricDetailsPO) {
        //TODO add logic to remove added row incase of failure in child table
        val fabricPOId = insertGreyFabricDetails(greyFabricDetailsPO)
        LoggerUtils.debug("insertGreyFabricDetailsWithFabricList", "fabricPOId--$fabricPOId")

        greyFabricDetailsPO.fabricStructureList.forEach { fabricStructurePo ->
            //Map foreign key to Fabric Structure PO
            fabricStructurePo.greyFabricId = fabricPOId.toInt()
            val fabricStructuredId = insertGreyFabricStructure(fabricStructurePo)
            LoggerUtils.debug("insertGreyFabricStructure", "fabricStructuredId--$fabricStructuredId")

            fabricStructurePo.fabricDiaList.forEach { fabricDia ->
                //Map foreign key to Dia PO
                fabricDia.fabricStructureId = fabricStructuredId.toInt()
                insertGreyFabricDia(fabricDia)
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGreyFabricStructure(fabricStructurePO: GreyFabricStructurePO): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGreyFabricDia(fabricDia: GreyFabricDia)

    @Query("SELECT * FROM grey_fabric")
    fun fetchAllGreyFabricList(): List<GreyFabricDetailsPO>

    @Transaction
    @Query("SELECT * FROM knitting_program")
    fun fetchGreyFabricBasedOnDCNo(): List<GreyFabricWrapper>

    @Transaction
    @Query("SELECT * FROM knitting_program where knitting_program.srkwDCNo = :srkwDCNo")
    fun fetchGreyFabricBasedOnDCNo(srkwDCNo: String): GreyFabricWrapper?

    @Query("SELECT DISTINCT spinningMill FROM grey_fabric")
    fun fetchSpinningMillsFromGreyFabricStock(): List<String>?

    @Query("SELECT DISTINCT goodsDesc FROM grey_fabric where spinningMill = :spinningMill")
    fun fetchGoodsDescFromGreyFabricStock(spinningMill: String): List<String>?

    /* TODO after fabric structure changes in grey fabric
    @Query("SELECT DISTINCT fabricStructure FROM grey_fabric WHERE spinningMill = :spinningMill AND goodsDesc = :goodsDesc")
    fun fetchFabricStructureFromGreyFabricStock(spinningMill: String , goodsDesc: String): List<String>?*/

/* TODO after fabric structure changes in grey fabric*/
/*@Query(
    "SELECT DISTINCT machineGage " +
            "FROM grey_fabric " +
            "WHERE spinningMill = :spinningMill " +
            "AND goodsDesc = :goodsDesc " +
            "AND fabricStructure = :fabricStructure"
)
fun fetchMachineGageFromGreyFabricStock(spinningMill: String , goodsDesc: String , fabricStructure: String): List<String>?*/

/*@Query(
    "SELECT DISTINCT loopLength " +
            "FROM grey_fabric " +
            "WHERE spinningMill = :spinningMill " +
            "AND goodsDesc = :goodsDesc " +
            "AND fabricStructure = :fabricStructure" +
            "AND machineGage = :machineGage"
)
fun fetchLoopLengthFromGreyFabricStock(spinningMill: String ,
                                       goodsDesc: String ,
                                       fabricStructure: String,
                                       machineGage: String): List<String>?*/
}