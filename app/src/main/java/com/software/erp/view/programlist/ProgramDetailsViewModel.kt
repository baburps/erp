package com.software.erp.view.programlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.erp.domain.room.ERPRoomDAO
import com.software.erp.view.yarnpurchase.YarnPurchasePO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProgramDetailsViewModel @Inject constructor(
    private val erpRoomDAO: ERPRoomDAO
) : ViewModel() {

    val yarnPurchasePOListLiveData = MutableLiveData<List<YarnPurchasePO>>()

    fun fetchYarnStockDetails() {
        val list = erpRoomDAO.fetchAllYarnPurchases()
        yarnPurchasePOListLiveData.postValue(list)
    }
}