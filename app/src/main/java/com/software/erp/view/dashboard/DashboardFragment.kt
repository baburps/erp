package com.software.erp.view.dashboard

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.FragmentDashboardBinding
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.PROGRAM_DETAIL_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewmodel = viewModel

        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { _it ->
                LoggerUtils.debug(TAG, "navigationLiveData--" + _it.navKey)
                val bundle = Bundle()
                bundle.putString(PROGRAM_DETAIL_KEY, _it.navKey)
                findNavController().navigate(
                    R.id.action_Dashboard_to_ProgramDetailsFragment,
                    bundle
                )
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_dashboard
    }

    override fun getViewTag(): String {
        return "DashboardFragment"
    }
}