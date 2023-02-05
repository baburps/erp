package com.software.erp.view.programlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.erp.R
import com.software.erp.base.ERPApplication
import com.software.erp.domain.model.ResultHandler
import com.software.erp.domain.repo.ERPRepo
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import com.software.erp.view.greyfabric.GreyFabricDetailsPO
import com.software.erp.view.greyfabric.GreyFabricWrapper
import com.software.erp.view.knitting.KnittingProgramPO
import com.software.erp.view.programlist.adapter.ProgramChildAdapterPO
import com.software.erp.view.programlist.adapter.ProgramParentAdapterPO
import com.software.erp.view.yarnpurchase.YarnPurchasePO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramDetailsViewModel @Inject constructor(
    private val application: ERPApplication ,
    private val erpRepo: ERPRepo
) : ViewModel() {

    companion object {
        const val TAG = "ProgramDetailsViewModel"
    }

    fun getString(stringId: Int): String {
        return application.applicationContext.getString(stringId)
    }

    val recyclerViewAdapterPOLiveData = MutableLiveData<MutableList<ProgramParentAdapterPO>>()

    fun fetchYarnStockDetails() {
        viewModelScope.launch {
            erpRepo.fetchAllYarnPurchases().collect { result ->
                when (result.status) {
                    ResultHandler.Status.SUCCESS -> {
                        populateYarnList(result.data)
                    }

                    ResultHandler.Status.ERROR -> {
                        //TODO handle error
                    }
                }
            }
        }
    }

    private fun populateYarnList(listOfEntries: List<YarnPurchasePO>?) {
        val programKey = DashboardViewModel.YARN_PURCHASE
        val listOfParentEntries = mutableListOf<ProgramParentAdapterPO>()

        listOfEntries?.forEach { yarnPurchasePO ->
            val listOfChildEntries = mutableListOf<ProgramChildAdapterPO>()
            listOfChildEntries.add(
                ProgramChildAdapterPO(
                    programKey ,
                    getString(R.string.spinning_mill) , yarnPurchasePO.spinningMill , yarnPurchasePO
                )
            )
            listOfChildEntries.add(
                ProgramChildAdapterPO(
                    programKey ,
                    getString(R.string.description) , yarnPurchasePO.goodsDesc , yarnPurchasePO
                )
            )
            listOfChildEntries.add(
                ProgramChildAdapterPO(
                    programKey ,
                    getString(R.string.no_of_bags) , yarnPurchasePO.noOfBags , yarnPurchasePO
                )
            )
            listOfChildEntries.add(
                ProgramChildAdapterPO(
                    programKey ,
                    getString(R.string.qty_in_kgs) , yarnPurchasePO.qtyInKgs , yarnPurchasePO
                )
            )

            listOfParentEntries.add(ProgramParentAdapterPO(programKey , listOfChildEntries))
        }
        recyclerViewAdapterPOLiveData.postValue(listOfParentEntries)
    }

    fun fetchKnittingProgramDetails() {
        viewModelScope.launch {
            erpRepo.fetchAllKnittingProgram().collect { result ->
                when (result.status) {
                    ResultHandler.Status.SUCCESS -> {
                        populateKnittingList(result.data)
                    }

                    ResultHandler.Status.ERROR -> {
                        //TODO handle error
                    }
                }
            }
        }
    }

    private fun populateKnittingList(listOfEntries: List<KnittingProgramPO>?) {
        val programKey = DashboardViewModel.KNITTING_PROGRAM
        val listOfParentEntries = mutableListOf<ProgramParentAdapterPO>()

        listOfEntries?.forEach { knittingProgramPO ->
            val listOfChildEntries = mutableListOf<ProgramChildAdapterPO>()
            listOfChildEntries.add(
                ProgramChildAdapterPO(
                    programKey ,
                    getString(R.string.SRKW_DC_no) , knittingProgramPO.srkwDCNo , knittingProgramPO
                )
            )
            listOfChildEntries.add(
                ProgramChildAdapterPO(
                    programKey ,
                    getString(R.string.lot_track_name) , knittingProgramPO.lotTrackName , knittingProgramPO
                )
            )
            listOfChildEntries.add(
                ProgramChildAdapterPO(
                    programKey ,
                    getString(R.string.description) , knittingProgramPO.goodsDesc , knittingProgramPO
                )
            )
            listOfChildEntries.add(
                ProgramChildAdapterPO(
                    programKey ,
                    getString(R.string.qty_in_kgs) , knittingProgramPO.qtyInKgs , knittingProgramPO
                )
            )

            listOfParentEntries.add(ProgramParentAdapterPO(programKey , listOfChildEntries))
        }

        recyclerViewAdapterPOLiveData.postValue(listOfParentEntries)

    }

    fun fetchGreyFabricDetails() {
        viewModelScope.launch {
            erpRepo.fetchGreyFabricBasedOnDCNo().collect { result ->
                when (result.status) {
                    ResultHandler.Status.SUCCESS -> {
                        populateGreyFabricList(result.data)
                    }

                    ResultHandler.Status.ERROR -> {
                        //TODO handle error
                    }
                }
            }
        }
    }

    private fun populateGreyFabricList(fabricList: List<GreyFabricWrapper>?) {
        val programKey = DashboardViewModel.GREY_FABRIC_STOCK
        fabricList.let {
            it.let { wrapperList ->
                val listOfParentEntries = mutableListOf<ProgramParentAdapterPO>()
                wrapperList?.forEach { wrapper ->
                    val knittingProgramPO = wrapper.knittingProgramPO
                    val greyFabricDetailsPO = GreyFabricDetailsPO()

                    var receivedQty = 0.0

                    if (!wrapper.greyFabricList.isNullOrEmpty()) {
                        wrapper.greyFabricList.forEach { greyFabricDetailsPOs ->
                            receivedQty += greyFabricDetailsPOs.receivedQtyInKgs.toDouble()
                        }
                    }

                    val actualQty = knittingProgramPO.qtyInKgs.toDouble()
                    val shortage = actualQty - receivedQty
                    val shortagePercentage = shortage / (actualQty / 100)

                    val listOfChildEntries = mutableListOf<ProgramChildAdapterPO>()
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.knitting_DC_no) , knittingProgramPO.srkwDCNo , greyFabricDetailsPO
                        )
                    )

                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.spinning_mill) , knittingProgramPO.spinningMill , greyFabricDetailsPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.goods_desc) , knittingProgramPO.goodsDesc , greyFabricDetailsPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.fabric_structure) , knittingProgramPO.fabricStructure , greyFabricDetailsPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.machine_gage) , knittingProgramPO.machineGage , greyFabricDetailsPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.loop_length) , knittingProgramPO.loopLength , greyFabricDetailsPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.dia) , knittingProgramPO.dia , greyFabricDetailsPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.programmed_qty) , knittingProgramPO.qtyInKgs , greyFabricDetailsPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.received_qty_kgs) , receivedQty.toString() , greyFabricDetailsPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.shortage) , shortage.toString() , greyFabricDetailsPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            getString(R.string.shortage_percent) , shortagePercentage.toString() , greyFabricDetailsPO
                        )
                    )

                    listOfParentEntries.add(ProgramParentAdapterPO(programKey , listOfChildEntries))
                }

                recyclerViewAdapterPOLiveData.postValue(listOfParentEntries)
            }
        }

    }

    fun fetchDyeingProgramList() {
    }


}