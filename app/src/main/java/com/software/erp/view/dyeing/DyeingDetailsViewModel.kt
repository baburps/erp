package com.software.erp.view.dyeing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.erp.common.customviews.CustomSpinnerBox
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.repo.ERPRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DyeingDetailsViewModel @Inject constructor(private val erpRepo: ERPRepo) : ViewModel() {

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
                    viewModelScope.launch {
                        erpRepo.fetchGoodsDescFromGreyFabricStock(selectedItem).collect { result ->
                            //Populate goods desc
                            result.data?.let { goodsDescList ->
                                goodsDescListLiveData.postValue(goodsDescList)
                            }
                        }
                    }
                }
            }
        }

        //TODO check if more than one goods desc will be available
        goodsDescSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "goodsDescSelectionListener-- onSpinnerItemSelection--$selectedItem")
                selectedGoodsDesc = selectedItem
                selectedItem?.let {
                    selectedSpinningMill?.let { it1 ->
                        viewModelScope.launch {
                            erpRepo.fetchFabricStructureFromGreyFabricStock(it1 , selectedItem).collect { result ->
                                result.data?.let { fabricStructureList ->
                                    fabricStructureListLiveData.postValue(fabricStructureList)
                                }
                            }
                        }
                    }
                }
            }
        }

        fabricStructureSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "fabricStructureSelectionListener-- onSpinnerItemSelection--$selectedItem")
                selectedFabricStructure = selectedItem
                selectedItem?.let {
                    selectedSpinningMill?.let { spinningMill ->
                        selectedGoodsDesc?.let { goodsDesc ->
                            selectedFabricStructure?.let { fabricStructure ->
                                viewModelScope.launch {
                                    erpRepo.fetchMachineGageFromGreyFabricStock(spinningMill , goodsDesc , fabricStructure).collect { result ->
                                        result.data?.let { gageList ->
                                            gageListLiveData.postValue(gageList)
                                        }
                                    }
                                }
                            }
                        }
                    }
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
                selectedItem?.let {}
            }
        }
        diaSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "diaSelectionListener-- onSpinnerItemSelection--$selectedItem")
                selectedItem?.let {}
            }
        }
    }

    private fun populateSpinningMillList() {
        viewModelScope.launch {
            erpRepo.fetchSpinningMillsFromGreyFabricStock().collect { result ->
                result.data?.let {
                    //Populate spinning mill list
                    spinningMillListLiveData.postValue(it)
                }
            }
        }
    }

    private fun getAlreadyReceivedQty() {
    }


    fun onSubmitClick() {
        LoggerUtils.debug(TAG , "onSubmitClick")
    }

}