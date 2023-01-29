package com.software.erp.view.knitting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.erp.common.customviews.CustomSpinnerBox
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.room.ERPRoomDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KnittingDetailsViewModel @Inject constructor(private val erpRoomDAO: ERPRoomDAO) : ViewModel() {

    companion object {
        const val TAG = "KnittingDetailsViewModel"
    }

    val knittingDetailsPOLiveData = MutableLiveData<KnittingProgramPO>()
    val fabricStructurePOLiveData = MutableLiveData<FabricStructurePO>()
    val fabricDiaPOLiveData = MutableLiveData<FabricDia>()

    val spinningMillSelectionListener: CustomSpinnerBox.SpinnerSelection
    val lotTrackNameSelectionListener: CustomSpinnerBox.SpinnerSelection
    val goodsDescSelectionListener: CustomSpinnerBox.SpinnerSelection
    val fabricStructureSelectionListener: CustomSpinnerBox.SpinnerSelection

    val spinningMillListLiveData = MutableLiveData<List<String>>()
    val lotTrackNoListLiveData = MutableLiveData<List<String>>()
    val goodsDescListLiveData = MutableLiveData<List<String>>()
    val fabricStructureListLiveData = MutableLiveData<List<String>>()

    init {
        knittingDetailsPOLiveData.postValue(KnittingProgramPO())
        fabricStructurePOLiveData.postValue(FabricStructurePO())
        fabricDiaPOLiveData.postValue(FabricDia())

        val listOfSpinningMills = erpRoomDAO.fetchSpinningMills()
        spinningMillListLiveData.postValue(listOfSpinningMills)

        //Update Lot track no spinner when Spinning mill is selected
        spinningMillSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG, "spinningMillSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                    val lotTrackNameList = erpRoomDAO.fetchLotTrackName(selectedItem)
                    lotTrackNoListLiveData.postValue(lotTrackNameList)
                    knittingDetailsPOLiveData.value?.spinningMill = selectedItem
                }
            }
        }

        //Update Goods desc spinner when Lot track no is selected
        lotTrackNameSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG, "lotTrackNameSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                    val goodsDescList = erpRoomDAO.fetchGoodsDesc(selectedItem)
                    goodsDescListLiveData.postValue(goodsDescList)
                    knittingDetailsPOLiveData.value?.lotTrackName = selectedItem
                }
            }
        }

        goodsDescSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG, "goodsDescSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                    knittingDetailsPOLiveData.value?.goodsDesc = selectedItem
                }
            }
        }

        fabricStructureSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG, "goodsDescSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                    //TODO after multiple fabric structure change
                    fabricStructurePOLiveData.value?.fabricStructure = selectedItem
                }
            }
        }

        //TODO dummy list
        val fabricList = ArrayList<String>()
        fabricList.add("Circular")
        fabricList.add("Flat")
        fabricStructureListLiveData.postValue(fabricList)
    }


    fun onSubmitClick() {
        LoggerUtils.debug(TAG, "onSubmitClick")

        LoggerUtils.debug(TAG, "onSubmitClick-dcNo--${knittingDetailsPOLiveData.value?.dcNo}")
        LoggerUtils.debug(TAG, "onSubmitClick-date--${knittingDetailsPOLiveData.value?.date}")
        LoggerUtils.debug(TAG, "onSubmitClick-lotTrackName--${knittingDetailsPOLiveData.value?.lotTrackName}")
        LoggerUtils.debug(TAG, "onSubmitClick-goodsDesc--${knittingDetailsPOLiveData.value?.goodsDesc}")
        LoggerUtils.debug(TAG, "onSubmitClick-orderRefNo--${knittingDetailsPOLiveData.value?.orderRefNo}")
        LoggerUtils.debug(TAG, "onSubmitClick-spinningMill--${knittingDetailsPOLiveData.value?.spinningMill}")

        LoggerUtils.debug(TAG, "onSubmitClick-fabricStructure--${fabricStructurePOLiveData.value?.fabricStructure}")
        LoggerUtils.debug(TAG, "onSubmitClick-machineGage--${fabricStructurePOLiveData.value?.machineGage}")
        LoggerUtils.debug(TAG, "onSubmitClick-loopLength--${fabricStructurePOLiveData.value?.loopLength}")

        LoggerUtils.debug(TAG, "onSubmitClick-dia--${fabricDiaPOLiveData.value?.dia}")
        LoggerUtils.debug(TAG, "onSubmitClick-qtyInKgs--${fabricDiaPOLiveData.value?.qtyInKgs}")

        val knittingProgramPO = knittingDetailsPOLiveData.value
        fabricStructurePOLiveData.value?.let { fabricStructurePO ->
            fabricDiaPOLiveData.value?.let { fabricDiaPO ->
                fabricStructurePO.fabricDiaList.add(fabricDiaPO)
            }
            knittingProgramPO?.fabricStructureList?.add(fabricStructurePO)
        }
        viewModelScope.launch {
//            val affectRows = knittingProgramPO?.let { erpRoomDAO.insertKnittingDetails(it) }
//            LoggerUtils.debug(TAG, "insertKnittingDetails--$affectRows")
        }
    }

}