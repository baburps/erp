package com.software.erp.view.programlist

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentProgramDetailsBinding
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramDetailsFragment : BaseFragment<FragmentProgramDetailsBinding>() {

    private val viewModel: ProgramDetailsViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel
        val programKey = arguments?.getString(DashboardViewModel.PROGRAM_DETAIL_KEY)
        setTitle(programKey)

        binding?.mBTProgramDetailsAdd?.setOnClickListener {
            handleNavigation(programKey)
        }

        programKey?.let { fetchProgramList(it) }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_program_details
    }

    override fun getViewTag(): String {
        return "ProgramDetailsFragment"
    }

    private fun handleNavigation(programKey: String?) {
        when (programKey) {
            DashboardViewModel.YARN_PURCHASE -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
            DashboardViewModel.KNITTING_PROGRAM -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
            DashboardViewModel.DYING_PROGRAM -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
            DashboardViewModel.DYED_FABRIC -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
            DashboardViewModel.CUTTING -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
            DashboardViewModel.FUSING -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
            DashboardViewModel.STITCHING -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
            DashboardViewModel.CHECKING -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
            DashboardViewModel.IRON_AND_PACK -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
            DashboardViewModel.SHIPMENT_READY -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment)
            }
        }
    }

    private fun fetchProgramList(programKey: String) {

        binding?.mRVProgramDetails?.layoutManager = LinearLayoutManager(activity)

        when (programKey) {
            DashboardViewModel.YARN_PURCHASE -> {
                handleYarnPurchaseList()
            }
        }

    }

    private fun handleYarnPurchaseList() {
        viewModel.fetchYarnStockDetails()

        viewModel.yarnPurchasePOListLiveData.observe(this) {
            //populate recycler view
            it.let {
                binding?.mRVProgramDetails?.adapter = YarnStockListAdapter(it)
            }
        }
    }


}