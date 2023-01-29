package com.software.erp.view.knitting

import androidx.fragment.app.viewModels
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.databinding.FragmentKnittingDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KnittingDetailsFragment : BaseFragment<FragmentKnittingDetailsBinding>() {

    private val viewModel: KnittingDetailsViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel
    }

    override fun layoutId(): Int {
        return R.layout.fragment_knitting_details
    }

    override fun getViewTag(): String {
        return "KnittingDetailsFragment"
    }
}