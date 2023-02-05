package com.software.erp.view.yarnpurchase

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentYarnPurchaseBinding
import com.software.erp.domain.model.ResultHandler
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YarnPurchaseFragment : BaseFragment<FragmentYarnPurchaseBinding>() {

    val viewModel: YarnPurchaseViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel

        viewModel.onYarnStockAddSuccess.observe(this) {
            when (it.status) {
                ResultHandler.Status.SUCCESS -> {
                    showToast(getString(R.string.yarn_purchase_saved_successfully))
                    //Pop backstack to go back to list page
                    findNavController().popBackStack()
                }
                ResultHandler.Status.ERROR -> {
                    showToast(getString(R.string.error))
                }
            }
        }

        arguments?.let {
            if (it.containsKey(DashboardViewModel.YARN_PURCHASE_PO)) {
                val yarnPurchasePO =
                    it.getSerializable(DashboardViewModel.YARN_PURCHASE_PO) as YarnPurchasePO
                viewModel.updateEditScreenPO(yarnPurchasePO)
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_yarn_purchase
    }

    override fun getViewTag(): String {
        return "YarnPurchaseFragment"
    }
}