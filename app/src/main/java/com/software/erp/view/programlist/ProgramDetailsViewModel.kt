package com.software.erp.view.programlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.erp.R
import com.software.erp.base.ERPApplication
import com.software.erp.domain.room.ERPRoomDAO
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import com.software.erp.view.greyfabric.GreyFabricDetailsPO
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
    private val erpRoomDAO: ERPRoomDAO
) : ViewModel() {

    companion object {
        const val TAG = "ProgramDetailsViewModel"
    }

    fun getString(stringId: Int): String {
        return application.applicationContext.getString(stringId)
    }

    val yarnPurchasePOListLiveData = MutableLiveData<List<YarnPurchasePO>>()
    val knittingProgramPOListLiveData = MutableLiveData<List<KnittingProgramPO>>()
    val recyclerViewAdapterPOLiveData = MutableLiveData<MutableList<ProgramParentAdapterPO>>()

    fun fetchYarnStockDetails() {
        val programKey = DashboardViewModel.YARN_PURCHASE
        viewModelScope.launch {
            val listOfEntries = erpRoomDAO.fetchAllYarnPurchases()

            val listOfParentEntries = mutableListOf<ProgramParentAdapterPO>()

            listOfEntries.forEach { yarnPurchasePO ->
                val listOfChildEntries = mutableListOf<ProgramChildAdapterPO>()
                listOfChildEntries.add(
                    ProgramChildAdapterPO(
                        programKey ,
                        getString(R.string.spinning_mill)!! , yarnPurchasePO.spinningMill , yarnPurchasePO
                    )
                )
                listOfChildEntries.add(
                    ProgramChildAdapterPO(
                        programKey ,
                        getString(R.string.description)!! , yarnPurchasePO.goodsDesc , yarnPurchasePO
                    )
                )
                listOfChildEntries.add(
                    ProgramChildAdapterPO(
                        programKey ,
                        getString(R.string.no_of_bags)!! , yarnPurchasePO.noOfBags.toString() , yarnPurchasePO
                    )
                )
                listOfChildEntries.add(
                    ProgramChildAdapterPO(
                        programKey ,
                        getString(R.string.qty_in_kgs)!! , yarnPurchasePO.qtyInKgs.toString() , yarnPurchasePO
                    )
                )

                listOfParentEntries.add(ProgramParentAdapterPO(programKey , listOfChildEntries))
            }
            recyclerViewAdapterPOLiveData.postValue(listOfParentEntries)
        }
    }

    fun fetchKnittingProgramDetails() {
        val programKey = DashboardViewModel.KNITTING_PROGRAM
        viewModelScope.launch {
            val listOfEntries = erpRoomDAO.fetchAllKnittingProgram()

            val listOfParentEntries = mutableListOf<ProgramParentAdapterPO>()

            listOfEntries.forEach { knittingProgramPO ->
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
    }

    fun fetchGreyFabricDetails() {
        val programKey = DashboardViewModel.GREY_FABRIC_STOCK
        viewModelScope.launch {
            val fabricList = erpRoomDAO.fetchGreyFabricBasedOnDCNo()

            //populate recycler view
            fabricList.let {
                it.let { wrapperList ->
                    val listOfParentEntries = mutableListOf<ProgramParentAdapterPO>()
                    wrapperList.forEach { wrapper ->
                        val knittingProgramPO = wrapper.knittingProgramPO
                        val greyFabricDetailsPO = GreyFabricDetailsPO()

                        var receivedQty = 0.0

                        wrapper.greyFabricList.forEach { greyFabricDetailsPOs ->
                            receivedQty += greyFabricDetailsPOs.receivedQtyInKgs.toDouble()
                        }

                        val actualQty = knittingProgramPO.qtyInKgs.toDouble()
                        val shortage = actualQty - receivedQty
                        val shortagePercentage = shortage / (actualQty / 100)

                        val listOfChildEntries = mutableListOf<ProgramChildAdapterPO>()
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
    }

    fun fetchDyeingProgramList() {
    }


}