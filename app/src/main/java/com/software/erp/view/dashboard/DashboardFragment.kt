package com.software.erp.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.software.erp.R
import com.software.erp.databinding.FragmentDashboardBinding
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.CHECKING
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.CUTTING
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.DYED_FABRIC
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.DYING_PROGRAM
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.FUSING
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.GREY_FABRIC_STOCK
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.IRON_AND_PACK
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.KNITTING_PROGRAM
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.SHIPMENT_READY
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.STITCHING
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.YARN_PURCHASE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel


        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_Dashboard_to_YarnPurchaseFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}