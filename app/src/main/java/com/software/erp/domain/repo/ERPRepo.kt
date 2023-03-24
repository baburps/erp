package com.software.erp.domain.repo

import com.software.erp.domain.model.ResultHandler
import com.software.erp.domain.room.ERPRoomDAO
import com.software.erp.view.greyfabric.GreyFabricDetailsPO
import com.software.erp.view.greyfabric.GreyFabricWrapper
import com.software.erp.view.greyfabric.model.FabricStructureWrapper
import com.software.erp.view.greyfabric.model.GreyFabricStructureWrapper
import com.software.erp.view.knitting.model.KnittingProgramFabricStructureWrapper
import com.software.erp.view.knitting.model.KnittingProgramPO
import com.software.erp.view.yarnpurchase.YarnPurchasePO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ERPRepo(
    private val erpRoomDAO: ERPRoomDAO ,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun fetchAllYarnPurchases(): Flow<ResultHandler<List<YarnPurchasePO>>> {
        return flow {
            val response = ResultHandler.success(erpRoomDAO.fetchAllYarnPurchases())
            emit(response)
        }.flowOn(ioDispatcher)
    }

    fun insertYarnPurchaseDetails(yarnPurchasePO: YarnPurchasePO): Flow<ResultHandler<Boolean>> {
        return flow {
            erpRoomDAO.insertYarnPurchaseDetails(yarnPurchasePO)
            emit(ResultHandler.success(true))
        }
    }

    fun updateYarnPurchaseDetails(yarnPurchasePO: YarnPurchasePO): Flow<ResultHandler<Boolean>> {
        return flow {
            erpRoomDAO.updateYarnPurchaseDetails(yarnPurchasePO)
            emit(ResultHandler.success(true))
        }
    }

    fun fetchAllKnittingProgram(): Flow<ResultHandler<List<KnittingProgramPO>>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchAllKnittingProgram()))
        }
    }

    fun fetchAllFabricStructureWithDia(): Flow<ResultHandler<List<KnittingProgramFabricStructureWrapper>>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.getKnittingFabricStructureList()))
        }
    }

    fun fetchFabricStructureWithDia(srkwDCNO: String): Flow<ResultHandler<List<FabricStructureWrapper>>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.getKnittingFabricStructureList(srkwDCNO)))
        }
    }

    fun fetchAllGreyFabricList(): Flow<ResultHandler<List<GreyFabricWrapper>>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchGreyFabricBasedOnDCNo()))
        }
    }

    fun fetchAllGreyFabricStructureWithDia(): Flow<ResultHandler<List<GreyFabricStructureWrapper>>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchAllGreyFabricStructureList()))
        }
    }

    fun fetchGreyFabricBasedOnDCNo(srkwDCNO: String): Flow<ResultHandler<GreyFabricWrapper?>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchGreyFabricBasedOnDCNo(srkwDCNO)))
        }
    }

    fun fetchSpinningMills(): Flow<ResultHandler<List<String>>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchSpinningMills()))
        }

    }

    fun fetchLotTrackName(selectedItem: String): Flow<ResultHandler<List<String>>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchLotTrackName(selectedItem)))
        }
    }

    fun fetchGoodsDesc(selectedItem: String): Flow<ResultHandler<List<String>>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchGoodsDesc(selectedItem)))
        }

    }

    fun fetchYarnPurchasesPO(selectedItem: String): Flow<ResultHandler<YarnPurchasePO>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchYarnPurchasesPO(selectedItem)))
        }
    }

    fun insertKnittingDetails(knittingPO: KnittingProgramPO): Flow<ResultHandler<Boolean>> {
        return flow {
            erpRoomDAO.insertKnittingDetailsWithFabricList(knittingPO)
            emit(ResultHandler.success(true))
        }
    }

    fun searchKnittingProgramWithDCNo(srkwDCNO: String): Flow<ResultHandler<KnittingProgramPO?>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.searchKnittingProgramWithDCNo(srkwDCNO)))
        }
    }

    fun insertGreyFabricDetails(greyFabricPO: GreyFabricDetailsPO): Flow<ResultHandler<Boolean>> {
        return flow {
            erpRoomDAO.insertGreyFabricDetailsWithFabricList(greyFabricPO)
            emit(ResultHandler.success(true))
        }
    }

    fun fetchGoodsDescFromGreyFabricStock(selectedItem: String): Flow<ResultHandler<List<String>?>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchGoodsDescFromGreyFabricStock(selectedItem)))
        }
    }
/* TODO after fabric structure changes in grey fabric
    fun fetchFabricStructureFromGreyFabricStock(spinningMill: String , goodsDesc: String): Flow<ResultHandler<List<String>?>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchFabricStructureFromGreyFabricStock(spinningMill , goodsDesc)))
        }
    }*/

/* TODO after fabric structure changes in grey fabric
    fun fetchMachineGageFromGreyFabricStock(spinningMill: String , goodsDesc: String , fabricStructure: String): Flow<ResultHandler<List<String>?>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchMachineGageFromGreyFabricStock(spinningMill , goodsDesc , fabricStructure)))
        }
    }
*/

    fun fetchSpinningMillsFromGreyFabricStock(): Flow<ResultHandler<List<String>?>> {
        return flow {
            emit(ResultHandler.success(erpRoomDAO.fetchSpinningMillsFromGreyFabricStock()))
        }
    }

}