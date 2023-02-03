package com.software.erp.view.programlist

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.FragmentProgramDetailsBinding
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import com.software.erp.view.programlist.adapter.ProgramChildAdapterPO
import com.software.erp.view.programlist.adapter.ProgramListChildAdapter
import com.software.erp.view.programlist.adapter.ProgramListParentAdapter
import com.software.erp.view.programlist.adapter.ProgramParentAdapterPO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramDetailsFragment : BaseFragment<FragmentProgramDetailsBinding>() {

    private val viewModel: ProgramDetailsViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel
        val programKey = arguments?.getString(DashboardViewModel.PROGRAM_DETAIL_KEY)
        setTitle(programKey)

        binding?.mBTProgramDetailsAdd?.setOnClickListener {
            handleNavigation(programKey , null)
        }

        programKey?.let { fetchProgramList(it) }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_program_details
    }

    override fun getViewTag(): String {
        return "ProgramDetailsFragment"
    }

    private fun handleNavigation(programKey: String? , bundle: Bundle?) {
        when (programKey) {
            DashboardViewModel.YARN_PURCHASE -> {
                navigateToYarnPurchaseFragment(bundle)
            }
            DashboardViewModel.KNITTING_PROGRAM -> {
                navigateToKnittingProgramFragment(bundle)
            }
            DashboardViewModel.GREY_FABRIC_STOCK -> {
                navigateToGreyFabricFragment(bundle)
            }
            DashboardViewModel.DYING_PROGRAM -> {
            }
            DashboardViewModel.DYED_FABRIC -> {
            }
            DashboardViewModel.CUTTING -> {
            }
            DashboardViewModel.FUSING -> {
            }
            DashboardViewModel.STITCHING -> {
            }
            DashboardViewModel.CHECKING -> {
            }
            DashboardViewModel.IRON_AND_PACK -> {
            }
            DashboardViewModel.SHIPMENT_READY -> {
            }
        }
    }

    private fun fetchProgramList(programKey: String) {

        binding?.mRVProgramDetails?.layoutManager = LinearLayoutManager(activity)

        when (programKey) {
            DashboardViewModel.YARN_PURCHASE -> {
                populateYarnProgramList()
            }
            DashboardViewModel.KNITTING_PROGRAM -> {
                handleKnittingProgramList()
            }
            DashboardViewModel.GREY_FABRIC_STOCK -> {
                handleGreyFabricList()
            }
        }

    }

    private fun navigateToYarnPurchaseFragment(bundle: Bundle?) {
        findNavController().navigate(
            R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment ,
            bundle
        )
    }

    private fun handleKnittingProgramList() {
        val programKey = DashboardViewModel.KNITTING_PROGRAM
        viewModel.fetchKnittingProgramDetails()

        viewModel.knittingProgramPOListLiveData.observe(this) {
            //populate recycler view
            it.let { listOfEntries ->
                val listOfParentEntries = mutableListOf<ProgramParentAdapterPO>()

                listOfEntries.forEach { knittingProgramPO ->
                    val listOfChildEntries = mutableListOf<ProgramChildAdapterPO>()
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            resources.getString(R.string.SRKW_DC_no) , knittingProgramPO.srkwDCNo , knittingProgramPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            resources.getString(R.string.lot_track_name) , knittingProgramPO.lotTrackName , knittingProgramPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            resources.getString(R.string.description) , knittingProgramPO.goodsDesc , knittingProgramPO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            resources.getString(R.string.qty_in_kgs) , knittingProgramPO.qtyInKgs.toString() , knittingProgramPO
                        )
                    )

                    listOfParentEntries.add(ProgramParentAdapterPO(programKey , listOfChildEntries))
                }
                populateRecyclerView(listOfParentEntries)
            }
        }
    }

    private fun navigateToKnittingProgramFragment(bundle: Bundle?) {
        findNavController().navigate(R.id.action_ProgramDetailsFragment_to_KnittingDetailsFragment , bundle)
    }

    private fun handleGreyFabricList() {
        viewModel.fetchGreyFabricDetails(DashboardViewModel.GREY_FABRIC_STOCK)

        viewModel.greyFabricAdapterPOLiveData.observe(this) {
            populateRecyclerView(it)
        }
    }

    private fun navigateToGreyFabricFragment(bundle: Bundle?) {
        findNavController().navigate(R.id.action_ProgramDetailsFragment_to_greyFabricDetailsFragment , bundle)
    }

    private fun populateYarnProgramList() {
        val programKey = DashboardViewModel.YARN_PURCHASE
        viewModel.fetchYarnStockDetails()

        viewModel.yarnPurchasePOListLiveData.observe(this) {
            //populate recycler view
            it.let { listOfEntries ->
                val listOfParentEntries = mutableListOf<ProgramParentAdapterPO>()

                listOfEntries.forEach { yarnPurchasePO ->
                    val listOfChildEntries = mutableListOf<ProgramChildAdapterPO>()
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            context?.resources?.getString(R.string.spinning_mill)!! , yarnPurchasePO.spinningMill , yarnPurchasePO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            context?.resources?.getString(R.string.description)!! , yarnPurchasePO.goodsDesc , yarnPurchasePO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            context?.resources?.getString(R.string.yarn_purchase_no_of_bags)!! , yarnPurchasePO.noOfBags.toString() , yarnPurchasePO
                        )
                    )
                    listOfChildEntries.add(
                        ProgramChildAdapterPO(
                            programKey ,
                            context?.resources?.getString(R.string.qty_in_kgs)!! , yarnPurchasePO.qtyInKgs.toString() , yarnPurchasePO
                        )
                    )

                    listOfParentEntries.add(ProgramParentAdapterPO(programKey , listOfChildEntries))
                }
                populateRecyclerView(listOfParentEntries)
            }
        }
    }

    private fun populateRecyclerView(listOfParentEntries: MutableList<ProgramParentAdapterPO>) {
        binding?.mRVProgramDetails?.adapter =
            ProgramListParentAdapter(listOfParentEntries , object : ProgramListChildAdapter.ItemSelectionListener {
                override fun onItemSelection(programChildAdapterPO: ProgramChildAdapterPO) {
                    LoggerUtils.debug(TAG , "onItemSelection")
                    //TODO edit & delete logic
                    /*  val bundle = Bundle()
                      bundle.putSerializable(programChildAdapterPO.programKey , programChildAdapterPO)
                      handleNavigation(programChildAdapterPO.programKey , bundle)*/
                }
            } , requireContext())
    }


}