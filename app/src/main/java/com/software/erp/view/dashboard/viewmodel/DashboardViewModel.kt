package com.software.erp.view.dashboard.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.erp.common.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val TAG = "DashboardViewModel"

        const val PROGRAM_DETAIL_KEY = "PROGRAM_DETAIL"

        const val YARN_PURCHASE = "YARN_PURCHASE"
        const val YARN_PURCHASE_PO = "YarnPurchasePO"

        const val KNITTING_PROGRAM = "KNITTING_PROGRAM"
        const val KNITTING_PROGRAM_PO = "KNITTING_PROGRAM_PO"

        const val GREY_FABRIC_STOCK = "GREY_FABRIC_STOCK"
        const val GREY_FABRIC_STOCK_PO = "GREY_FABRIC_STOCK_PO"

        const val DYING_PROGRAM = "DYING_PROGRAM"
        const val DYING_PROGRAM_PO = "DYING_PROGRAM_PO"

        const val DYED_FABRIC = "DYED_FABRIC"
        const val CUTTING = "CUTTING"
        const val FUSING = "FUSING"
        const val STITCHING = "STITCHING"
        const val CHECKING = "CHECKING"
        const val IRON_AND_PACK = "IRON_AND_PACK"
        const val SHIPMENT_READY = "SHIPMENT_READY"
    }

    val navigationLiveData = MutableLiveData<Event<DashboardNavigation>>()

    //Handle button click
    fun onButtonClickSelection(flow: String) {
        Log.d(TAG, "onButtonClickSelection--$flow")
        navigationLiveData.postValue(Event(DashboardNavigation(flow)))
    }
}