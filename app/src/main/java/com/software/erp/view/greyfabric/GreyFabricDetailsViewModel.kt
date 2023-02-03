package com.software.erp.view.greyfabric

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.room.ERPRoomDAO
import com.software.erp.view.knitting.KnittingProgramPO
import com.software.erp.view.yarnpurchase.YarnPurchaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GreyFabricDetailsViewModel @Inject constructor(private val erpRoomDAO: ERPRoomDAO) : ViewModel() {

    companion object {
        const val TAG = "GreyFabricDetailsViewModel"
    }

    val onGretFabricAddSuccess = MutableLiveData<Boolean>()
    val showToastMessage = MutableLiveData<String>()
    val greyFabricDetailsPOLiveData = MutableLiveData<GreyFabricDetailsPO>()


    init {
        greyFabricDetailsPOLiveData.postValue(GreyFabricDetailsPO())
    }

    fun updateEditScreenPO(knittingProgramPO: KnittingProgramPO) {
        LoggerUtils.debug(YarnPurchaseViewModel.TAG , "updateEditScreenPO")
    }

    fun onKnittingDCSearchClick() {
        LoggerUtils.debug(TAG , "onKnittingDCSearchClick")
        val srkwDCNO = greyFabricDetailsPOLiveData.value?.srkwDCNo
        if (srkwDCNO != null) {
            val knittingProgramPO = erpRoomDAO.searchKnittingProgramWithDCNo(srkwDCNO)
            knittingProgramPO?.let { _knittingProgramPO ->
                LoggerUtils.debug(TAG , "onKnittingDCSearchClick-date--${_knittingProgramPO.date}")
                LoggerUtils.debug(TAG , "onKnittingDCSearchClick-orderRefNo--${_knittingProgramPO.orderRefNo}")
                LoggerUtils.debug(TAG , "onKnittingDCSearchClick-goodsDesc--${_knittingProgramPO.goodsDesc}")
                LoggerUtils.debug(TAG , "onKnittingDCSearchClick-fabricStructure--${_knittingProgramPO.fabricStructure}")
                LoggerUtils.debug(TAG , "onKnittingDCSearchClick-machineGage--${_knittingProgramPO.machineGage}")
                LoggerUtils.debug(TAG , "onKnittingDCSearchClick-loopLength--${_knittingProgramPO.loopLength}")
                LoggerUtils.debug(TAG , "onKnittingDCSearchClick-dia--${_knittingProgramPO.dia}")
                LoggerUtils.debug(TAG , "onKnittingDCSearchClick-qtyInKgs--${_knittingProgramPO.qtyInKgs}")

                val greyFabricDetailsPO = greyFabricDetailsPOLiveData.value
                greyFabricDetailsPO?.let { _greyFabricPo ->
                    _greyFabricPo.spinningMill = _knittingProgramPO.spinningMill
                    _greyFabricPo.srkwDCNo = _knittingProgramPO.srkwDCNo
                    _greyFabricPo.date = _knittingProgramPO.date
                    _greyFabricPo.orderRefNo = _knittingProgramPO.orderRefNo
                    _greyFabricPo.goodsDesc = _knittingProgramPO.goodsDesc
                    _greyFabricPo.fabricStructure = _knittingProgramPO.fabricStructure
                    _greyFabricPo.machineGage = _knittingProgramPO.machineGage
                    _greyFabricPo.loopLength = _knittingProgramPO.loopLength
                    _greyFabricPo.dia = _knittingProgramPO.dia
                    _greyFabricPo.programmedQtyInKgs = _knittingProgramPO.qtyInKgs
                    _greyFabricPo.showKnittingDetails = true

                    greyFabricDetailsPOLiveData.postValue(_greyFabricPo)
                }
            }
        }
    }

    fun onCalculateShortageClick() {
        greyFabricDetailsPOLiveData.value?.let { greyFabricPO ->
            val receivedQty = greyFabricPO.receivedQtyInKgs.toDouble()
            val actualQty = greyFabricPO.programmedQtyInKgs.toDouble()

            if (receivedQty > actualQty) {
                //Show error
                showToastMessage.postValue("Received Qty is more the programmed Qty")
            } else {
                //Calculate shortage
                val shortage = actualQty - receivedQty
                greyFabricPO.shortageInKgs = shortage.toString()
                val shortagePercentage = shortage / (actualQty / 100)
                greyFabricPO.shortagePercentage = shortagePercentage.toString()
                greyFabricPO.showShortageDetails = true

                greyFabricDetailsPOLiveData.postValue(greyFabricPO)
            }
        }
    }

    fun onSubmitClick() {
        LoggerUtils.debug(TAG , "onSubmitClick")
        LoggerUtils.debug(TAG , "onSubmitClick--knittingDCNo--${greyFabricDetailsPOLiveData.value?.knittingCompanyDCNo}")
        LoggerUtils.debug(TAG , "onSubmitClick--SRKWDCNo--${greyFabricDetailsPOLiveData.value?.srkwDCNo}")
        LoggerUtils.debug(TAG , "onSubmitClick--spinningMill--${greyFabricDetailsPOLiveData.value?.spinningMill}")
        LoggerUtils.debug(TAG , "onSubmitClick--date--${greyFabricDetailsPOLiveData.value?.date}")
        LoggerUtils.debug(TAG , "onSubmitClick--goodsDesc--${greyFabricDetailsPOLiveData.value?.goodsDesc}")
        LoggerUtils.debug(TAG , "onSubmitClick--orderRefNo--${greyFabricDetailsPOLiveData.value?.orderRefNo}")
        LoggerUtils.debug(TAG , "onSubmitClick--fabricStructure--${greyFabricDetailsPOLiveData.value?.fabricStructure}")
        LoggerUtils.debug(TAG , "onSubmitClick--machineGage--${greyFabricDetailsPOLiveData.value?.machineGage}")
        LoggerUtils.debug(TAG , "onSubmitClick--dia--${greyFabricDetailsPOLiveData.value?.dia}")
        LoggerUtils.debug(TAG , "onSubmitClick--loopLength--${greyFabricDetailsPOLiveData.value?.loopLength}")
        LoggerUtils.debug(TAG , "onSubmitClick--programmedQtyInKgs--${greyFabricDetailsPOLiveData.value?.programmedQtyInKgs}")
        LoggerUtils.debug(TAG , "onSubmitClick--receivedQtyInKgs--${greyFabricDetailsPOLiveData.value?.receivedQtyInKgs}")
        LoggerUtils.debug(TAG , "onSubmitClick--shortageInKgs--${greyFabricDetailsPOLiveData.value?.shortageInKgs}")
        LoggerUtils.debug(TAG , "onSubmitClick--shortagePercentage--${greyFabricDetailsPOLiveData.value?.shortagePercentage}")

        greyFabricDetailsPOLiveData.value?.let { erpRoomDAO.insertGreyFabricDetails(it) }

        onGretFabricAddSuccess.postValue(true)

    }

}