package com.software.erp.view.yarnpurchase

import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentYarnPurchaseBinding

class YarnPurchaseFragment : BaseFragment<FragmentYarnPurchaseBinding>() {

    override fun onSetUp() {

    }

    override fun layoutId(): Int {
        return R.layout.fragment_yarn_purchase
    }

    override fun getViewTag(): String {
        return "YarnPurchaseFragment"
    }
}