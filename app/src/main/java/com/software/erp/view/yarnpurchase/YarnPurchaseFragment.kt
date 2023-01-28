package com.software.erp.view.yarnpurchase

import androidx.fragment.app.viewModels
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentYarnPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YarnPurchaseFragment : BaseFragment<FragmentYarnPurchaseBinding>() {

    val viewModel: YarnPurchaseViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel
    }

    override fun layoutId(): Int {
        return R.layout.fragment_yarn_purchase
    }

    override fun getViewTag(): String {
        return "YarnPurchaseFragment"
    }
}