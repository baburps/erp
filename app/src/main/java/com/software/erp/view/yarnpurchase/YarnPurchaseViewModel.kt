package com.software.erp.view.yarnpurchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YarnPurchaseViewModel @Inject constructor() : ViewModel() {

    val yarnPurchasePO: LiveData<YarnPurchasePO>? = null
}