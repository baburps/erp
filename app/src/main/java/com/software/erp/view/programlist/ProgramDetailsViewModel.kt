package com.software.erp.view.programlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.erp.R
import com.software.erp.base.ERPApplication
import com.software.erp.domain.room.ERPRoomDAO
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
        return application.applicationContext.resources.getString(stringId)
    }

    val yarnPurchasePOListLiveData = MutableLiveData<List<YarnPurchasePO>>()
    val knittingProgramPOListLiveData = MutableLiveData<List<KnittingProgramPO>>()
    val greyFabricAdapterPOLiveData = MutableLiveData<MutableList<ProgramParentAdapterPO>>()

    fun fetchYarnStockDetails() {
        viewModelScope.launch {
            val list = erpRoomDAO.fetchAllYarnPurchases()
            yarnPurchasePOListLiveData.postValue(list)
        }
    }

    fun fetchKnittingProgramDetails() {
        viewModelScope.launch {
            knittingProgramPOListLiveData.postValue(erpRoomDAO.fetchAllKnittingProgram())
        }
    }

    fun fetchGreyFabricDetails(programKey: String) {
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
                                getString(R.string.knitting_dia) , knittingProgramPO.dia , greyFabricDetailsPO
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

                    greyFabricAdapterPOLiveData.postValue(listOfParentEntries)
                }
            }
        }
    }


}