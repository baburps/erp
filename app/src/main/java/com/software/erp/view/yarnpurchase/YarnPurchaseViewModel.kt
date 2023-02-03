package com.software.erp.view.yarnpurchase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.room.ERPRoomDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YarnPurchaseViewModel @Inject constructor(private val erpRoomDAO: ERPRoomDAO) : ViewModel() {

    companion object {
        const val TAG = "YarnPurchaseViewModel"
    }

    val yarnPurchasePOLiveData = MutableLiveData<YarnPurchasePO>()
    val onYarnStockAddSuccess = MutableLiveData<Boolean>()

    init {
        yarnPurchasePOLiveData.postValue(YarnPurchasePO())
    }

    fun updateEditScreenPO(yarnPurchasePO: YarnPurchasePO) {
        LoggerUtils.debug(TAG , "updateEditScreenPO")
        yarnPurchasePOLiveData.postValue(yarnPurchasePO)
    }


    fun onSubmitClick() {
        LoggerUtils.debug(TAG , "onSubmitClick")
        //Get all data & validate
        //TODO validation
        LoggerUtils.debug(TAG , "invoiceNo${yarnPurchasePOLiveData.value?.invoiceNo}")
        LoggerUtils.debug(TAG , "date${yarnPurchasePOLiveData.value?.date}")
        LoggerUtils.debug(TAG , "spinnerMill${yarnPurchasePOLiveData.value?.spinningMill}")
        LoggerUtils.debug(TAG , "goodsDesc${yarnPurchasePOLiveData.value?.goodsDesc}")
        LoggerUtils.debug(TAG , "noOfBags${yarnPurchasePOLiveData.value?.noOfBags}")
        LoggerUtils.debug(TAG , "qtyInKgs${yarnPurchasePOLiveData.value?.qtyInKgs}")
        LoggerUtils.debug(TAG , "price${yarnPurchasePOLiveData.value?.price}")
        LoggerUtils.debug(TAG , "gst${yarnPurchasePOLiveData.value?.gst}")
        LoggerUtils.debug(TAG , "value${yarnPurchasePOLiveData.value?.value}")
        //TODO handle unique entry of track no
        LoggerUtils.debug(TAG , "lotTrackName${yarnPurchasePOLiveData.value?.lotTrackName}")
        LoggerUtils.debug(TAG , "trackingID${yarnPurchasePOLiveData.value?.trackingID}")

        viewModelScope.launch {
            //Save qty to use for reducing in Knitting program
            yarnPurchasePOLiveData.value?.qtyInKgs?.let { qtyInKgs ->
                yarnPurchasePOLiveData.value?.currentQtyInKgs = qtyInKgs
            }

            if (yarnPurchasePOLiveData.value?.trackingID != 0) {
                LoggerUtils.debug(TAG , "onSubmitClick update")
                yarnPurchasePOLiveData.value?.let { erpRoomDAO.updateYarnPurchaseDetails(yarnPurchasePO = it) }
            } else {
                LoggerUtils.debug(TAG , "onSubmitClick insert")
                yarnPurchasePOLiveData.value?.let { erpRoomDAO.insertYarnPurchaseDetails(yarnPurchasePO = it) }
            }

            onYarnStockAddSuccess.postValue(true)
        }
    }

    //TODO remove
    fun addDummyData() = try {
        val list = erpRoomDAO.fetchAllYarnPurchases()
        val id = list.size + 1
        val yarnPurchasePO = YarnPurchasePO(
            "Invoice-$id" ,
            "01-01-2022" ,
            "Spinning mill-$id" ,
            "Desc-$id" ,
            (100 * id).toString() ,
            (1000 * id).toString() ,
            (1000 * id).toString() ,
            10.toString() ,
            (1000 * id).toString() ,
            "Track name-$id" ,
            (1000 * id).toString() ,
            0
        )

        erpRoomDAO.insertYarnPurchaseDetails(yarnPurchasePO)
        onYarnStockAddSuccess.postValue(true)
    } catch (e: Exception) {
        LoggerUtils.error(tag = TAG , exception = e)
    }
}