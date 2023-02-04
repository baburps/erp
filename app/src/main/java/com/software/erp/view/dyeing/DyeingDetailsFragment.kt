package com.software.erp.view.dyeing

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentDyeingDetailsBinding
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DyeingDetailsFragment : BaseFragment<FragmentDyeingDetailsBinding>() {

    private val viewModel: DyeingDetailsViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel

        viewModel.onDyeingAddSuccess.observe(this) {
            showToast(getString(R.string.knitting_saved_successfully))
            //Pop backstack to go back to list page
            findNavController().popBackStack()
        }

        arguments?.let {
            if (it.containsKey(DashboardViewModel.DYING_PROGRAM_PO)) {
                val dyeingDetailsPO =
                    it.getSerializable(DashboardViewModel.DYING_PROGRAM_PO) as DyeingDetailsPO
                //TODO
            }
        }

        viewModel.showToastMessage.observe(this) {
            showToast(it)
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_dyeing_details
    }

    override fun getViewTag(): String {
        return "GreyFabricDetailsFragment"
    }
}