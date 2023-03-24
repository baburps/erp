package com.software.erp.view.greyfabric

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.common.utils.TextFormatUtil
import com.software.erp.domain.model.ResultHandler
import com.software.erp.domain.repo.ERPRepo
import com.software.erp.view.greyfabric.model.FabricStructureWrapper
import com.software.erp.view.knitting.model.KnittingProgramPO
import com.software.erp.view.yarnpurchase.YarnPurchaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreyFabricDetailsViewModel @Inject constructor(private val erpRepo: ERPRepo) : ViewModel() {

    companion object {
        const val TAG = "GreyFabricDetailsViewModel"
    }

    private var existingReceivedQty: Double? = null
    val onGretFabricAddSuccess = MutableLiveData<Boolean>()
    val showToastMessage = MutableLiveData<String>()
    val greyFabricDetailsPOLiveData = MutableLiveData<GreyFabricDetailsPO>()

    //To update Fabric structure Recycler view
    val fabricStructurePOListLiveData = MutableLiveData<List<GreyFabricStructurePO>>()


    init {
        greyFabricDetailsPOLiveData.postValue(GreyFabricDetailsPO())
    }

    fun updateEditScreenPO(knittingProgramPO: KnittingProgramPO) {
        LoggerUtils.debug(YarnPurchaseViewModel.TAG , "updateEditScreenPO")
    }

    fun onKnittingDCSearchClick() {
        LoggerUtils.debug(TAG , "onKnittingDCSearchClick")
        val srkwDCNO = greyFabricDetailsPOLiveData.value?.knittingProgramSRKWDCNo
        if (srkwDCNO != null) {
            existingReceivedQty = getAlreadyReceivedQty(srkwDCNO)
            viewModelScope.launch {
                erpRepo.searchKnittingProgramWithDCNo(srkwDCNO).collect {
                    it.data?.let { _knittingProgramPO ->
                        LoggerUtils.debug(TAG , "onKnittingDCSearchClick-date--${_knittingProgramPO.date}")
                        LoggerUtils.debug(TAG , "onKnittingDCSearchClick-orderRefNo--${_knittingProgramPO.orderRefNo}")
                        LoggerUtils.debug(TAG , "onKnittingDCSearchClick-goodsDesc--${_knittingProgramPO.goodsDesc}")
                        /* LoggerUtils.debug(TAG , "onKnittingDCSearchClick-fabricStructure--${_knittingProgramPO.fabricStructure}")
                         LoggerUtils.debug(TAG , "onKnittingDCSearchClick-machineGage--${_knittingProgramPO.machineGage}")
                         LoggerUtils.debug(TAG , "onKnittingDCSearchClick-loopLength--${_knittingProgramPO.loopLength}")
                         LoggerUtils.debug(TAG , "onKnittingDCSearchClick-dia--${_knittingProgramPO.dia}")
                         LoggerUtils.debug(TAG , "onKnittingDCSearchClick-qtyInKgs--${_knittingProgramPO.qtyInKgs}")*/

                        fetchFabricStructureDetails(_knittingProgramPO)
                    }
                }
            }
        }
    }

    private fun fetchFabricStructureDetails(knittingProgramPO: KnittingProgramPO) {
        viewModelScope.launch {
            erpRepo.fetchFabricStructureWithDia(knittingProgramPO.srkwDCNo).collect { result ->
                when (result.status) {
                    ResultHandler.Status.SUCCESS -> {
                        result.data?.let {
                            populateFabricDetails(knittingProgramPO , result.data)
                        }
                    }

                    ResultHandler.Status.ERROR -> {
                        //TODO handle error
                    }
                }
            }
        }
    }

    private fun populateFabricDetails(
        _knittingProgramPO: KnittingProgramPO ,
        listOfFabricStructureWrapper: List<FabricStructureWrapper>
    ) {
        val greyFabricDetailsPO = greyFabricDetailsPOLiveData.value
        greyFabricDetailsPO?.let { _greyFabricPo ->
            _greyFabricPo.spinningMill = _knittingProgramPO.spinningMill
            _greyFabricPo.knittingProgramSRKWDCNo = _knittingProgramPO.srkwDCNo
            _greyFabricPo.date = _knittingProgramPO.date
            _greyFabricPo.orderRefNo = _knittingProgramPO.orderRefNo
            _greyFabricPo.goodsDesc = _knittingProgramPO.goodsDesc
            /* _greyFabricPo.fabricStructure = _knittingProgramPO.fabricStructure
             _greyFabricPo.machineGage = _knittingProgramPO.machineGage
             _greyFabricPo.loopLength = _knittingProgramPO.loopLength
             _greyFabricPo.dia = _knittingProgramPO.dia
             _greyFabricPo.programmedQtyInKgs = _knittingProgramPO.qtyInKgs
             _greyFabricPo.remainingQtyInKgs = _knittingProgramPO.qtyInKgs*/

            _greyFabricPo.showKnittingDetails = true

            existingReceivedQty?.let {
//                                _greyFabricPo.remainingQtyInKgs = (_knittingProgramPO.qtyInKgs.toDouble() - it).toString()
            }

            greyFabricDetailsPOLiveData.postValue(_greyFabricPo)

            try {
                val lisOfFabricStructureList: MutableList<GreyFabricStructurePO> = mutableListOf()

                listOfFabricStructureWrapper.forEach {
                    val fabricStructurePO = it.fabricStructurePO
                    it.fabricDiaList?.let { fabricDiaList ->
                        val greyDiaList: MutableList<GreyFabricDia> = mutableListOf()
                        //Change Dia PO
                        fabricDiaList.forEach { fabricDia ->
                            val diaPO = GreyFabricDia()
                            diaPO.dia = fabricDia.dia
                            diaPO.qtyInKgs = fabricDia.qtyInKgs
                            greyDiaList.add(diaPO)
                        }

                        //Change FabricDetails PO
                        val greyFabricDetailsPOs = GreyFabricStructurePO()
                        greyFabricDetailsPOs.fabricStructure = fabricStructurePO.fabricStructure
                        greyFabricDetailsPOs.machineGage = fabricStructurePO.machineGage
                        greyFabricDetailsPOs.loopLength = fabricStructurePO.loopLength
                        //Map dia list to FabricDetails PO
                        greyFabricDetailsPOs.fabricDiaList = greyDiaList

                        lisOfFabricStructureList.add(greyFabricDetailsPOs)
                    }
                }
                fabricStructurePOListLiveData.postValue(lisOfFabricStructureList)
            } catch (e: Exception) {
                LoggerUtils.error(TAG , "listOfFabricStructureWrapper" , e)
            }
        }
    }

    private fun getAlreadyReceivedQty(srkwDCNO: String?): Double? {
        var receivedQty = 0.0
        srkwDCNO?.let {
            viewModelScope.launch {
                erpRepo.fetchGreyFabricBasedOnDCNo(it).collect { result ->
                    result.data?.let { wrapper ->
                        if (!wrapper.greyFabricList.isNullOrEmpty()) {
                            wrapper.greyFabricList.forEach { _po ->
                                //TODO
//                                receivedQty += _po.receivedQtyInKgs.toDouble()
                            }
                        }
                    }
                }
            }
        }
        return if (receivedQty > 0.0) receivedQty else null
    }

    fun onCalculateShortageClick() {
        var showError = false
        var enableSaveButton = true
        LoggerUtils.debug(TAG , "onCalculateShortageClick")
        fabricStructurePOListLiveData.value?.let { greyFabricList ->
            greyFabricList.forEach { _greyFabricPO ->
                _greyFabricPO.fabricDiaList.forEach { _fabricDiaPO ->
                    val programmedQty = _fabricDiaPO.qtyInKgs
                    val receivedQty = _fabricDiaPO.receivedQtyInKgs
                    //Clear show shortage flag by default
                    _fabricDiaPO.showShortageDetails = false
                    if (!TextUtils.isEmpty(programmedQty) &&
                        TextUtils.isDigitsOnly(programmedQty) &&
                        !TextUtils.isEmpty(receivedQty) &&
                        TextUtils.isDigitsOnly(receivedQty)
                    ) {
                        if (receivedQty.toDouble()
                            /* TODO + existingReceivedQtyTemp*/
                            > programmedQty.toDouble()
                        ) {
                            //TODO show error in that field
                            showToastMessage.postValue("Received Qty is more the Programmed Qty")
                            showError = true
                        } else {
                            val actualQty = programmedQty.toDouble() /* TODO - existingReceivedQtyTemp*/
                            val shortage = actualQty - receivedQty.toDouble()
                            _fabricDiaPO.shortageInKgs = shortage.toString()
                            val shortagePercentage = shortage / (actualQty / 100)
                            _fabricDiaPO.shortagePercentage = TextFormatUtil.roundOffToTwoDigit(shortagePercentage)
                            _fabricDiaPO.showShortageDetails = true
                        }
                    } else {
                        //If field is empty then don't show save button
                        enableSaveButton = false
                    }
                }
            }

            //Skip updating UI in case of error
            if (!showError) {
                fabricStructurePOListLiveData.postValue(greyFabricList)

                greyFabricDetailsPOLiveData.value?.let { greyFabricPO ->
                    greyFabricPO.showShortageDetails = enableSaveButton
                    greyFabricDetailsPOLiveData.postValue(greyFabricPO)
                }
            }
        }
/*
        greyFabricDetailsPOLiveData.value?.let { greyFabricPO ->
            //TODO
            */
/* val receivedQty = greyFabricPO.receivedQtyInKgs.toDouble()
             val actualQty = greyFabricPO.remainingQtyInKgs.toDouble()*//*




            if (isReceivedQtyValid()) {
                //Calculate shortage
                //TODO
                */
/*val shortage = actualQty - receivedQty
                greyFabricPO.shortageInKgs = shortage.toString()
                val shortagePercentage = shortage / (actualQty / 100)
                greyFabricPO.shortagePercentage = shortagePercentage.toString()
                greyFabricPO.showShortageDetails = true
*//*

                greyFabricDetailsPOLiveData.postValue(greyFabricPO)
            }
        }
*/
    }

    private fun isReceivedQtyValid(): Boolean {
        var existingReceivedQtyTemp = existingReceivedQty
        if (existingReceivedQtyTemp == null) {
            existingReceivedQtyTemp = 0.0
        }

/*//TODO
        if (greyFabricDetailsPOLiveData.value?.receivedQtyInKgs?.toDouble()!! + existingReceivedQtyTemp > greyFabricDetailsPOLiveData.value?.programmedQtyInKgs?.toDouble()!!) {
            showToastMessage.postValue("Received Qty is more the Programmed Qty")
            return false
        }
*/
        return true
    }

    fun onSubmitClick() {
        LoggerUtils.debug(TAG , "onSubmitClick")
        LoggerUtils.debug(TAG , "onSubmitClick--knittingDCNo--${greyFabricDetailsPOLiveData.value?.knittingCompanyDCNo}")
        LoggerUtils.debug(TAG , "onSubmitClick--knittingProgramSRKWDCNo--${greyFabricDetailsPOLiveData.value?.knittingProgramSRKWDCNo}")
        LoggerUtils.debug(TAG , "onSubmitClick--spinningMill--${greyFabricDetailsPOLiveData.value?.spinningMill}")
        LoggerUtils.debug(TAG , "onSubmitClick--date--${greyFabricDetailsPOLiveData.value?.date}")
        LoggerUtils.debug(TAG , "onSubmitClick--goodsDesc--${greyFabricDetailsPOLiveData.value?.goodsDesc}")
        LoggerUtils.debug(TAG , "onSubmitClick--orderRefNo--${greyFabricDetailsPOLiveData.value?.orderRefNo}")


        if (isReceivedQtyValid()) {
            greyFabricDetailsPOLiveData.value?.let { greyFabricPO ->
                fabricStructurePOListLiveData.value?.let { fabricList ->
                    greyFabricPO.fabricStructureList = fabricList
                    viewModelScope.launch {
                        erpRepo.insertGreyFabricDetails(greyFabricPO).collect { result ->
                            when (result.status) {
                                ResultHandler.Status.SUCCESS -> {
                                    onGretFabricAddSuccess.postValue(true)
                                }

                                ResultHandler.Status.ERROR -> {
                                    //TODO handle error
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}