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
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.YARN_PURCHASE_PO
import com.software.erp.view.yarnpurchase.YarnPurchasePO
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
                navigateToYarnPurchaseFragment(null)
            }
            DashboardViewModel.KNITTING_PROGRAM -> {
                findNavController().navigate(R.id.action_ProgramDetailsFragment_to_KnittingDetailsFragment)
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
                binding?.mRVProgramDetails?.adapter =
                    YarnStockListAdapter(it, object : YarnStockListAdapter.ItemSelectionListener {
                        override fun onItemSelection(yarnPurchasePO: YarnPurchasePO) {
                            LoggerUtils.debug(TAG, "onItemSelection")
                            val bundle = Bundle()
                            bundle.putSerializable(YARN_PURCHASE_PO, yarnPurchasePO)
                            navigateToYarnPurchaseFragment(bundle)
                        }
                    })
            }
        }
    }

    private fun navigateToYarnPurchaseFragment(bundle: Bundle?) {
        findNavController().navigate(
            R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment,
            bundle
        )
    }

}