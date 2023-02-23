package com.software.erp.view.knitting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.erp.common.customviews.CustomSpinnerBox
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.domain.model.ResultHandler
import com.software.erp.domain.repo.ERPRepo
import com.software.erp.view.yarnpurchase.YarnPurchasePO
import com.software.erp.view.yarnpurchase.YarnPurchaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KnittingDetailsViewModel @Inject constructor(private val erpRepo: ERPRepo) : ViewModel() {

    companion object {
        const val TAG = "KnittingDetailsViewModel"
    }

    val knittingDetailsPOLiveData = MutableLiveData<KnittingProgramPO>()

    lateinit var spinningMillSelectionListener: CustomSpinnerBox.SpinnerSelection
    lateinit var lotTrackNameSelectionListener: CustomSpinnerBox.SpinnerSelection
    val availableYarnQtyLiveData = MutableLiveData<String>()
    lateinit var goodsDescSelectionListener: CustomSpinnerBox.SpinnerSelection

    val spinningMillListLiveData = MutableLiveData<List<String>>()

    //TODO handle unique entry of track no
    val lotTrackNoListLiveData = MutableLiveData<List<String>>()
    val goodsDescListLiveData = MutableLiveData<List<String>>()

    val onKnittingAddSuccess = MutableLiveData<Boolean>()
    val showToastMessage = MutableLiveData<String>()

    //To update Fabric structure Recycler view
    val fabricStructurePOListLiveData = MutableLiveData<MutableList<FabricStructurePO>>()

    private var yarnPurchasePO: YarnPurchasePO? = null

    init {
        initializeDefaultValuesForAdd()
    }

    fun updateEditScreenPO(knittingProgramPO: KnittingProgramPO) {
        LoggerUtils.debug(YarnPurchaseViewModel.TAG , "updateEditScreenPO")
        knittingDetailsPOLiveData.postValue(knittingProgramPO)
    }

    private fun initializeDefaultValuesForAdd() {
        knittingDetailsPOLiveData.postValue(KnittingProgramPO())

        viewModelScope.launch {
            erpRepo.fetchSpinningMills().collect { result ->
                result.data?.let {
                    spinningMillListLiveData.postValue(it)
                }
            }
        }

        //Update Lot track no spinner when Spinning mill is selected
        spinningMillSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "spinningMillSelectionListener-- onSpinnerItemSelection")
                selectedItem?.let {
                    viewModelScope.launch {
                        erpRepo.fetchLotTrackName(selectedItem).collect { result ->
                            result.data?.let { lotTrackNameList ->
                                lotTrackNoListLiveData.postValue(lotTrackNameList)
                                knittingDetailsPOLiveData.value?.spinningMill = selectedItem
                            }
                        }
                    }
                }
            }
        }

        //Update Goods desc spinner when Lot track no is selected
        lotTrackNameSelectionListener = object : CustomSpinnerBox.SpinnerSelection {
            override fun onSpinnerItemSelection(selectedItem: String?) {
                LoggerUtils.debug(TAG , "lotTrackNameSelectionListener-- onSpinnerItemSelection")
                yarnPurchasePO = null
                selectedItem?.let {
                    viewModelScope.launch {
                        erpRepo.fetchGoodsDesc(selectedItem).collect { result ->
                            result.data?.let { goodsDescList ->
                                goodsDescListLiveData.postValue(goodsDescList)
                                knittingDetailsPOLiveData.value?.lotTrackName = selectedItem

                                //Get yarn details of track name
                                erpRepo.fetchYarnPurchasesPO(selectedItem).collect { resultPurchasePO ->
                                    resultPurchasePO.data?.let { yarnPurchasePo1 ->
                                        yarnPurchasePO = yarnPurchasePo1
                                        //Update available qty
                                        availableYarnQtyLiveData.postValue(yarnPurchasePo1.currentQtyInKgs)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        goodsDescSelectionListener =
            object : CustomSpinnerBox.SpinnerSelection {
                override fun onSpinnerItemSelection(selectedItem: String?) {
                    LoggerUtils.debug(TAG , "goodsDescSelectionListener-- onSpinnerItemSelection")
                    selectedItem?.let {
                        knittingDetailsPOLiveData.value?.goodsDesc = selectedItem
                    }
                }
            }

        //Default Add one fabric structure item
        addFabricStructureList()
    }

    fun addFabricStructureList() {
        var list: MutableList<FabricStructurePO>? = fabricStructurePOListLiveData.value
        list?.add(FabricStructurePO()) ?: run {
            list = mutableListOf(FabricStructurePO())
        }
        fabricStructurePOListLiveData.postValue(list!!)
    }


    fun onSubmitClick(fabricStructurePOList: List<FabricStructurePO>) {
        LoggerUtils.debug(TAG , "onSubmitClick")

        try {
            LoggerUtils.debug(TAG , "onSubmitClick-dcNo--${knittingDetailsPOLiveData.value?.srkwDCNo}")
            LoggerUtils.debug(TAG , "onSubmitClick-date--${knittingDetailsPOLiveData.value?.date}")
            LoggerUtils.debug(TAG , "onSubmitClick-lotTrackName--${knittingDetailsPOLiveData.value?.lotTrackName}")
            LoggerUtils.debug(TAG , "onSubmitClick-goodsDesc--${knittingDetailsPOLiveData.value?.goodsDesc}")
            LoggerUtils.debug(TAG , "onSubmitClick-orderRefNo--${knittingDetailsPOLiveData.value?.orderRefNo}")
            LoggerUtils.debug(TAG , "onSubmitClick-spinningMill--${knittingDetailsPOLiveData.value?.spinningMill}")

            fabricStructurePOList.forEach { fabricStructurePO ->
                LoggerUtils.debug(TAG , "onSubmitClick-fabricStructure--${fabricStructurePO.fabricStructure}")
                LoggerUtils.debug(TAG , "onSubmitClick-machineGage--${fabricStructurePO.machineGage}")
                LoggerUtils.debug(TAG , "onSubmitClick-loopLength--${fabricStructurePO.loopLength}")

                fabricStructurePO.fabricDiaList.forEach { diaPo ->
                    LoggerUtils.debug(TAG , "onSubmitClick-dia--${diaPo.dia}")
                    LoggerUtils.debug(TAG , "onSubmitClick-qtyInKgs--${diaPo.qtyInKgs}")
                }
            }
        } catch (exception: Exception) {
            LoggerUtils.error(TAG , "onSubmitClick" , exception)
        }

        yarnPurchasePO?.let { yarnPurchasePO_ ->
            knittingDetailsPOLiveData.value?.let { knittingPO ->
                val availableQty = yarnPurchasePO_.currentQtyInKgs.toDouble()
                var qtyToBeReduced = 0.0
                fabricStructurePOList.forEach { fabricStructurePO ->
                    fabricStructurePO.fabricDiaList.forEach { fabricDia ->
                        qtyToBeReduced += fabricDia.qtyInKgs.toDouble()
                    }
                }

                LoggerUtils.debug(TAG , "availableQty$availableQty")
                LoggerUtils.debug(TAG , "qtyToBeReduced$qtyToBeReduced")

                if (availableQty < qtyToBeReduced) {
                    showToastMessage.postValue("Given qty is more than available qty")
                } else {
                    yarnPurchasePO_.currentQtyInKgs = (availableQty - qtyToBeReduced).toString()
                    //Add fabricStructurePOList to Knitting po
                    knittingPO.fabricStructureList = fabricStructurePOList
                    insertKnittingDetails(knittingPO , yarnPurchasePO_)
                }
            }
        }
    }

    private fun insertKnittingDetails(
        knittingPO: KnittingProgramPO ,
        yarnPurchasePO_: YarnPurchasePO
    ) = viewModelScope.launch {
        erpRepo.insertKnittingDetails(knittingPO).collect { insertResult ->
            when (insertResult.status) {
                ResultHandler.Status.SUCCESS -> {
                    LoggerUtils.debug(TAG , "insertKnittingDetails-success")
                    erpRepo.updateYarnPurchaseDetails(yarnPurchasePO_).collect { yarnUpdateResult ->
                        when (yarnUpdateResult.status) {
                            ResultHandler.Status.SUCCESS -> {
                                LoggerUtils.debug(TAG , "updateYarnPurchaseDetails-success")
                                onKnittingAddSuccess.postValue(true)
                            }
                            ResultHandler.Status.ERROR -> {
                                LoggerUtils.debug(TAG , "updateYarnPurchaseDetails-error")
                                //TODO handle error
                            }
                        }
                    }
                }
                ResultHandler.Status.ERROR -> {
                    LoggerUtils.debug(TAG , "insertKnittingDetails-error")
                    //TODO handle error
                }
            }
        }
    }
}