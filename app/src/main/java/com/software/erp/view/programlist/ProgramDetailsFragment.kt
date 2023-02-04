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
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment , bundle)
            }
            DashboardViewModel.KNITTING_PROGRAM -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_KnittingDetailsFragment , bundle)
            }
            DashboardViewModel.GREY_FABRIC_STOCK -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_greyFabricDetailsFragment , bundle)
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
                viewModel.fetchYarnStockDetails()
            }
            DashboardViewModel.KNITTING_PROGRAM -> {
                viewModel.fetchKnittingProgramDetails()
            }
            DashboardViewModel.GREY_FABRIC_STOCK -> {
                viewModel.fetchGreyFabricDetails()
            }
            DashboardViewModel.DYING_PROGRAM -> {
                viewModel.fetchDyeingProgramList()
            }
        }

        viewModel.recyclerViewAdapterPOLiveData.observe(this) {
            populateRecyclerView(it)
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