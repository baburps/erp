package com.software.erp.view.knitting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.FragmentKnittingDetailsBinding
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import com.software.erp.view.knitting.FabricStructureAdapter.Companion.PAGE_KEY_KNITTING
import com.software.erp.view.knitting.model.KnittingProgramPO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KnittingDetailsFragment : BaseFragment<FragmentKnittingDetailsBinding>() {

    private var fabricStructureAdapter: FabricStructureAdapter? = null
    private val viewModel: KnittingDetailsViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel

        viewModel.onKnittingAddSuccess.observe(this) {
            showToast(getString(R.string.knitting_saved_successfully))
            //Pop backstack to go back to list page
            findNavController().popBackStack()
        }

        arguments?.let {
            if (it.containsKey(DashboardViewModel.KNITTING_PROGRAM_PO)) {
                val knittingProgramPO = it.getSerializable(DashboardViewModel.KNITTING_PROGRAM_PO) as KnittingProgramPO
                viewModel.updateEditScreenPO(knittingProgramPO)
                //TODO handle fabric structure recycler view
            }
        }

        viewModel.showToastMessage.observe(this) {
            showToast(it)
        }

        binding?.mBTKnittingDetailsSave?.setOnClickListener {
            onSubmitClick()
        }

        handleRecyclerView()
    }

    private fun handleRecyclerView() {
        binding?.mRVKnittingDetailsFabricStructure?.layoutManager = LinearLayoutManager(activity)
        //Disable list scroll. It will use page scroll
        binding?.mRVKnittingDetailsFabricStructure?.isNestedScrollingEnabled = false

        viewModel.fabricStructurePOListLiveData.observe(this) {
            //Update Recycler view
            it?.let {
                fabricStructureAdapter?.updateList(it) ?: run {
                    fabricStructureAdapter = FabricStructureAdapter(it , null , PAGE_KEY_KNITTING)
                    binding?.mRVKnittingDetailsFabricStructure?.adapter = fabricStructureAdapter
                }
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_knitting_details
    }

    override fun getViewTag(): String {
        return "KnittingDetailsFragment"
    }

    fun onSubmitClick() {
        LoggerUtils.debug(TAG , "onSubmitClick")

        fabricStructureAdapter?.let { fabricStructureAdapter ->
            val list = fabricStructureAdapter.getFabricStructureList()
            if (list!!.isNotEmpty()) {
                LoggerUtils.debug(TAG , "fabric structure list")

                viewModel.onSubmitClick(list)
            }
        }
    }
}