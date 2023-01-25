package com.software.erp.view.dashboard

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentProgramDetailsBinding
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import com.software.erp.view.dashboard.viewmodel.ProgramDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramDetailsFragment : BaseFragment<FragmentProgramDetailsBinding>() {

    private val viewModel: ProgramDetailsViewModel by viewModels()

    override fun onSetUp() {
//        binding?.viewmodel = viewModel
        val programKey = arguments?.getString(DashboardViewModel.PROGRAM_DETAIL_KEY)
        setTitle(programKey)
        
        binding?.mBTProgramDetailsAdd?.setOnClickListener {
            handleNavigation(programKey)
        }
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

}