package com.software.erp.view.yarnpurchase

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.room.YarnPurchaseRoomDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YarnPurchaseViewModel @Inject constructor(private val yarnPurchaseRoomDAO: YarnPurchaseRoomDAO) :
    ViewModel() {

    companion object {
        const val TAG = "YarnPurchaseViewModel"
    }

    val yarnPurchasePO = MutableLiveData<YarnPurchasePO>()

    init {
        //fetch all yarn purchase list
        val yarnPurchaseList: List<YarnPurchasePO> = yarnPurchaseRoomDAO.fetchAllYarnPurchases()
        yarnPurchaseList.let {
            yarnPurchasePO.postValue(it[0])
        }
    }

    val onSubmitClick: View.OnClickListener = View.OnClickListener {
        //Get all data & validate
        //TODO validation
        if (yarnPurchasePO != null) {

        }
        LoggerUtils.debug(TAG, "onSubmitClick")
    }
}