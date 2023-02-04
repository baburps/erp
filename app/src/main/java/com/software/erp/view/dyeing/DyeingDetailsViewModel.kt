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

    private var selectedSpinningMill: String? = null
    private var selectedGoodsDesc: String? = null
    private var selectedFabricStructure: String? = null
    private var selectedMachineGage: String? = null
    private var selectedLoopLength: String? = null
    private var selectedDia: String? = null

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
                LoggerUtils.debug(TAG , "spinningMillSelectionListener-- onSpinnerItemSelection--$selectedItem")
                selectedSpinningMill = selectedItem
                selectedItem?.let {
                    //Populate goods desc
                    goodsDescListLiveData.postValue(erpRoomDAO.fetchGoodsDescFromGreyFabricStock(selectedItem))
                }
            }
        }

        //TODO check if more than one goods desc will be available
        goodsDescSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "goodsDescSelectionListener-- onSpinnerItemSelection--$selectedItem")
                selectedGoodsDesc = selectedItem
                selectedItem?.let {
                    fabricStructureListLiveData.postValue(selectedSpinningMill?.let { it1 ->
                        erpRoomDAO.fetchFabricStructureFromGreyFabricStock(
                            it1 ,
                            selectedItem
                        )
                    })
                }
            }
        }

        fabricStructureSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "fabricStructureSelectionListener-- onSpinnerItemSelection--$selectedItem")
                selectedFabricStructure = selectedItem
                selectedItem?.let {
                    gageListLiveData.postValue(selectedSpinningMill?.let { spinningMill ->
                        selectedGoodsDesc?.let { goodsDesc ->
                            selectedFabricStructure?.let { fabricStructure ->
                                erpRoomDAO.fetchMachineGageFromGreyFabricStock(
                                    spinningMill ,
                                    goodsDesc ,
                                    fabricStructure
                                )
                            }
                        }
                    })

                }
            }
        }
        gageSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "gageSelectionListener-- onSpinnerItemSelection--$selectedItem")
                selectedFabricStructure = selectedItem
                selectedItem?.let {

                }
            }
        }
        loopLengthSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "loopLengthSelectionListener-- onSpinnerItemSelection--$selectedItem")
                selectedItem?.let {
                }
            }
        }
        diaSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "diaSelectionListener-- onSpinnerItemSelection--$selectedItem")
                selectedItem?.let {
                }
            }
        }
    }

    private fun populateSpinningMillList() {
        val spinnerList = erpRoomDAO.fetchSpinningMillsFromGreyFabricStock()

        spinnerList?.let {
            //Populate spinning mill list
            spinningMillListLiveData.postValue(it)
        }
    }

    private fun getAlreadyReceivedQty() {
    }


    fun onSubmitClick() {
        LoggerUtils.debug(TAG , "onSubmitClick")
    }

}