package com.software.erp.view.yarnpurchase

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.room.YarnPurchaseRoomDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YarnPurchaseViewModel @Inject constructor(yarnPurchaseRoomDAO: YarnPurchaseRoomDAO) :
    ViewModel() {

    companion object {
        const val TAG = "YarnPurchaseViewModel"
    }

    val yarnPurchasePO = MutableLiveData<YarnPurchasePO>()

    init {
        //fetch all yarn purchase list
        val yarnPurchaseList: List<YarnPurchasePO> = yarnPurchaseRoomDAO.fetchAllYarnPurchases()
        yarnPurchaseList.let {
            LoggerUtils.debug(TAG, "yarnPurchaseList")
            if (it.isNotEmpty()) {
                yarnPurchasePO.postValue(it[0])
            } else {
                //TODO remove after room implementation
                LoggerUtils.debug(TAG, "yarnPurchaseList null")
                val yarnPurchasePO1 = YarnPurchasePO(
                    "100222",
                    "12-10-2023",
                    "Spinnies",
                    "Yarn purchase",
                    "20",
                    "50",
                    "12,000",
                    "12",
                    "20000",
                    "Zoho order",
                    101
                )
                yarnPurchasePO.postValue(yarnPurchasePO1)
            }
        }
    }

    val onSubmitClick: View.OnClickListener = View.OnClickListener {
        LoggerUtils.debug(TAG, "onSubmitClick")
        //Get all data & validate
        //TODO validation
        if (yarnPurchasePO != null) {

        }

    }
}