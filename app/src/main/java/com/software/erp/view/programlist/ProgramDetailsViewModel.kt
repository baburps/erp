package com.software.erp.view.programlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.erp.domain.room.ERPRoomDAO
import com.software.erp.view.greyfabric.GreyFabricDetailsPO
import com.software.erp.view.knitting.KnittingProgramPO
import com.software.erp.view.yarnpurchase.YarnPurchasePO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramDetailsViewModel @Inject constructor(
    private val erpRoomDAO: ERPRoomDAO
) : ViewModel() {

    val yarnPurchasePOListLiveData = MutableLiveData<List<YarnPurchasePO>>()
    val knittingProgramPOListLiveData = MutableLiveData<List<KnittingProgramPO>>()
    val greyFabricDetailsPOListLiveData = MutableLiveData<List<GreyFabricDetailsPO>>()

    fun fetchYarnStockDetails() {
        viewModelScope.launch {
            val list = erpRoomDAO.fetchAllYarnPurchases()
            yarnPurchasePOListLiveData.postValue(list)
        }
    }

    fun fetchKnittingProgramDetails() {
        viewModelScope.launch {
            knittingProgramPOListLiveData.postValue(erpRoomDAO.fetchAllKnittingProgram())
        }
    }

    fun fetchGreyFabricDetails() {
        viewModelScope.launch {
            greyFabricDetailsPOListLiveData.postValue(erpRoomDAO.fetchAllGreyFabricList())
        }
    }


}