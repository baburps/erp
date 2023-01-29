package com.software.erp.view.yarnpurchase

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentYarnPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YarnPurchaseFragment : BaseFragment<FragmentYarnPurchaseBinding>() {

    val viewModel: YarnPurchaseViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel

        viewModel.onYarnStockAddSuccess.observe(this) {
            showToast(getString(R.string.yarn_purchase_saved_successfully))
            //Pop backstack to go back to list page
            findNavController().popBackStack()
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_yarn_purchase
    }

    override fun getViewTag(): String {
        return "YarnPurchaseFragment"
    }
}