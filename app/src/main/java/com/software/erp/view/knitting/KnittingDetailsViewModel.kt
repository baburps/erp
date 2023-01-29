package com.software.erp.view.knitting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.erp.common.customviews.CustomSpinnerBox
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.room.ERPRoomDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KnittingDetailsViewModel @Inject constructor(private val erpRoomDAO: ERPRoomDAO) :
    ViewModel() {

    companion object {
        const val TAG = "KnittingDetailsViewModel"
    }

    val fabricDetailsPO = MutableLiveData<KnittingProgramPO>()

    val spinningMillSelectionListener: CustomSpinnerBox.SpinnerSelection
    val lotTrackNameSelectionListener: CustomSpinnerBox.SpinnerSelection
    val goodsDescSelectionListener: CustomSpinnerBox.SpinnerSelection

    val spinningMillListLiveData = MutableLiveData<List<String>>()
    val lotTrackNoListLiveData = MutableLiveData<List<String>>()
    val goodsDescListLiveData = MutableLiveData<List<String>>()
    val fabricStructureListLiveData = MutableLiveData<List<String>>()

    init {
        val listOfSpinningMills = erpRoomDAO.fetchSpinningMills()
        spinningMillListLiveData.postValue(listOfSpinningMills)

        //Update Lot track no spinner when Spinning mill is selected
        spinningMillSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG, "spinningMillSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                    val lotTrackNameList = erpRoomDAO.fetchLotTrackName(selectedItem)
                    lotTrackNoListLiveData.postValue(lotTrackNameList)
                    fabricDetailsPO.value?.spinningMill = selectedItem
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
                    fabricDetailsPO.value?.lotTrackName = selectedItem
                }
            }
        }

        goodsDescSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG, "goodsDescSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                    fabricDetailsPO.value?.goodsDesc = selectedItem
                }
            }
        }
    }


    fun onSubmitClick() {
        LoggerUtils.debug(TAG, "onSubmitClick")

        LoggerUtils.debug(TAG, "onSubmitClick-dcNo--${fabricDetailsPO.value?.dcNo}")
        LoggerUtils.debug(TAG, "onSubmitClick-date--${fabricDetailsPO.value?.date}")
        LoggerUtils.debug(TAG, "onSubmitClick-lotTrackName--${fabricDetailsPO.value?.lotTrackName}")
        LoggerUtils.debug(TAG, "onSubmitClick-goodsDesc--${fabricDetailsPO.value?.goodsDesc}")
        LoggerUtils.debug(TAG, "onSubmitClick-orderRefNo--${fabricDetailsPO.value?.orderRefNo}")
        LoggerUtils.debug(TAG, "onSubmitClick-spinningMill--${fabricDetailsPO.value?.spinningMill}")

    }

}