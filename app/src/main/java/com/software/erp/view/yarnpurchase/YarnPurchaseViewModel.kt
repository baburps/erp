package com.software.erp.view.yarnpurchase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.room.ERPRoomDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YarnPurchaseViewModel @Inject constructor(private val erpRoomDAO: ERPRoomDAO) :
    ViewModel() {

    companion object {
        const val TAG = "YarnPurchaseViewModel"
    }

    val yarnPurchasePO = MutableLiveData<YarnPurchasePO>()
    val onYarnStockAddSuccess = MutableLiveData<Boolean>()

    init {
        yarnPurchasePO.postValue(YarnPurchasePO())
       /* //TODO edit logic fetch all yarn purchase list
        val yarnPurchaseList: List<YarnPurchasePO> = erpRoomDAO.fetchAllYarnPurchases()
        yarnPurchaseList.let {
            if (it.isNotEmpty()) {
                LoggerUtils.debug(TAG, "yarnPurchaseList not null")
            } else {
                LoggerUtils.debug(TAG, "yarnPurchaseList null")
            }
        }*/
    }

    fun onSubmitClick() {
        LoggerUtils.debug(TAG, "onSubmitClick")
        //Get all data & validate
        //TODO validation
        LoggerUtils.debug(TAG, "invoiceNo${yarnPurchasePO.value?.invoiceNo}")
        LoggerUtils.debug(TAG, "date${yarnPurchasePO.value?.date}")
        LoggerUtils.debug(TAG, "spinnerMill${yarnPurchasePO.value?.spinnerMill}")
        LoggerUtils.debug(TAG, "goodsDesc${yarnPurchasePO.value?.goodsDesc}")
        LoggerUtils.debug(TAG, "noOfBags${yarnPurchasePO.value?.noOfBags}")
        LoggerUtils.debug(TAG, "qtyInKgs${yarnPurchasePO.value?.qtyInKgs}")
        LoggerUtils.debug(TAG, "price${yarnPurchasePO.value?.price}")
        LoggerUtils.debug(TAG, "gst${yarnPurchasePO.value?.gst}")
        LoggerUtils.debug(TAG, "value${yarnPurchasePO.value?.value}")
        LoggerUtils.debug(TAG, "lotTrackName${yarnPurchasePO.value?.lotTrackName}")
        LoggerUtils.debug(TAG, "trackingID${yarnPurchasePO.value?.trackingID}")

        if (yarnPurchasePO.value?.trackingID != 0) {
            LoggerUtils.debug(TAG, "onSubmitClick update")
            yarnPurchasePO.value?.let { erpRoomDAO.update(yarnPurchasePO = it) }
        } else {
            LoggerUtils.debug(TAG, "onSubmitClick insert")
            yarnPurchasePO.value?.let { erpRoomDAO.insert(yarnPurchasePO = it) }
        }

        onYarnStockAddSuccess.postValue(true)
    }
}