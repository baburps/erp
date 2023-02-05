package com.software.erp.domain.repo

import com.software.erp.domain.model.ResultHandler
import com.software.erp.domain.room.ERPRoomDAO
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

}