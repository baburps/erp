package com.software.erp.view.dashboard.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val TAG = "DashboardViewModel"

        const val YARN_PURCHASE = "YARN_PURCHASE"
        const val KNITTING_PROGRAM = "KNITTING_PROGRAM"
        const val GREY_FABRIC_STOCK = "GREY_FABRIC_STOCK"
        const val DYING_PROGRAM = "DYING_PROGRAM"
        const val DYED_FABRIC = "DYED_FABRIC"
        const val CUTTING = "CUTTING"
        const val FUSING = "FUSING"
        const val STITCHING = "STITCHING"
        const val CHECKING = "CHECKING"
        const val IRON_AND_PACK = "IRON_AND_PACK"
        const val SHIPMENT_READY = "SHIPMENT_READY"
    }

    //Handle button click
    fun onButtonClickSelection(flow: String) {
        Log.d(TAG, "onButtonClickSelection--$flow")

    }
}