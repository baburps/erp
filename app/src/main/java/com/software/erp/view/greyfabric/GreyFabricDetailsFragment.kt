package com.software.erp.view.greyfabric

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentGreyFabricDetailsBinding
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GreyFabricDetailsFragment : BaseFragment<FragmentGreyFabricDetailsBinding>() {

    private val viewModel: GreyFabricDetailsViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel

        viewModel.onGretFabricAddSuccess.observe(this) {
            showToast(getString(R.string.grey_fabric_saved_successfully))
            //Pop backstack to go back to list page
            findNavController().popBackStack()
        }

        arguments?.let {
            if (it.containsKey(DashboardViewModel.GREY_FABRIC_STOCK_PO)) {
                val greyFabricPO =
                    it.getSerializable(DashboardViewModel.GREY_FABRIC_STOCK_PO) as GreyFabricDetailsPO
                //TODO
            }
        }

        viewModel.showToastMessage.observe(this) {
            showToast(it)
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_grey_fabric_details
    }

    override fun getViewTag(): String {
        return "GreyFabricDetailsFragment"
    }
}