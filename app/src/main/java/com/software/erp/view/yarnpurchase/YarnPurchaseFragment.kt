package com.software.erp.view.yarnpurchase

import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentYarnPurchaseBinding

class YarnPurchaseFragment : BaseFragment<FragmentYarnPurchaseBinding>() {

    override fun onSetUp() {

        //TODO remove after room implementation
        val yarnPurchasePO = YarnPurchasePO(
            "100222",
            "12-10-2023",
            "Spinnies",
            "Yarn purchase",
            "20",
            "50",
            "12,000",
            "12",
            "20000",
            "Zoho order",
            "101"
        )

        binding?.yarnPurchasePO = yarnPurchasePO
    }

    override fun layoutId(): Int {
        return R.layout.fragment_yarn_purchase
    }

    override fun getViewTag(): String {
        return "YarnPurchaseFragment"
    }
}