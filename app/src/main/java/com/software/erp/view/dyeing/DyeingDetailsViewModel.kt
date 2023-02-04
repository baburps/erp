package com.software.erp.view.dyeing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.erp.common.customviews.CustomSpinnerBox
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.room.ERPRoomDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DyeingDetailsViewModel @Inject constructor(private val erpRoomDAO: ERPRoomDAO) : ViewModel() {

    companion object {
        const val TAG = "DyeingDetailsViewModel"
    }

    val onDyeingAddSuccess = MutableLiveData<Boolean>()
    val showToastMessage = MutableLiveData<String>()
    val dyingDetailsPOLiveData = MutableLiveData<DyeingDetailsPO>()

    val spinningMillListLiveData = MutableLiveData<List<String>>()
    lateinit var spinningMillSelectionListener: CustomSpinnerBox.SpinnerSelection

    val goodsDescListLiveData = MutableLiveData<List<String>>()
    lateinit var goodsDescSelectionListener: CustomSpinnerBox.SpinnerSelection

    val fabricStructureListLiveData = MutableLiveData<List<String>>()
    lateinit var fabricStructureSelectionListener: CustomSpinnerBox.SpinnerSelection

    val gageListLiveData = MutableLiveData<List<String>>()
    lateinit var gageSelectionListener: CustomSpinnerBox.SpinnerSelection

    val loopLengthListLiveData = MutableLiveData<List<String>>()
    lateinit var loopLengthSelectionListener: CustomSpinnerBox.SpinnerSelection

    val diaListLiveData = MutableLiveData<List<String>>()
    lateinit var diaSelectionListener: CustomSpinnerBox.SpinnerSelection

    init {
        dyingDetailsPOLiveData.postValue(DyeingDetailsPO())

        initSpinnerListeners()
        //Populate spinning mill list
        populateSpinningMillList()
    }

    private fun initSpinnerListeners() {
        //Update Lot track no spinner when Spinning mill is selected
        spinningMillSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "spinningMillSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                    val lotTrackNameList = erpRoomDAO.fetchLotTrackName(selectedItem)
                }
            }
        }

        goodsDescSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "goodsDescSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                }
            }
        }
        fabricStructureSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "fabricStructureSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                }
            }
        }
        gageSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "gageSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                }
            }
        }
        loopLengthSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "loopLengthSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                }
            }
        }
        diaSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "diaSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                }
            }
        }
    }

    private fun populateSpinningMillList() {
    }

    private fun getAlreadyReceivedQty() {
    }


    fun onSubmitClick() {
        LoggerUtils.debug(TAG , "onSubmitClick")
    }

}