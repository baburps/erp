package com.software.erp.view.programlist

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.software.erp.R
import com.software.erp.base.BaseFragment
import com.software.erp.common.utils.LoggerUtils
import com.software.erp.databinding.FragmentProgramDetailsBinding
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.KNITTING_PROGRAM_PO
import com.software.erp.view.dashboard.viewmodel.DashboardViewModel.Companion.YARN_PURCHASE_PO
import com.software.erp.view.greyfabric.GreyFabricDetailsPO
import com.software.erp.view.greyfabric.GreyFabricListAdapter
import com.software.erp.view.knitting.KnittingProgramPO
import com.software.erp.view.yarnpurchase.YarnPurchasePO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramDetailsFragment : BaseFragment<FragmentProgramDetailsBinding>() {

    private val viewModel: ProgramDetailsViewModel by viewModels()

    override fun onSetUp() {
        binding?.viewModel = viewModel
        val programKey = arguments?.getString(DashboardViewModel.PROGRAM_DETAIL_KEY)
        setTitle(programKey)

        binding?.mBTProgramDetailsAdd?.setOnClickListener {
            handleNavigation(programKey)
        }

        programKey?.let { fetchProgramList(it) }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_program_details
    }

    override fun getViewTag(): String {
        return "ProgramDetailsFragment"
    }

    private fun handleNavigation(programKey: String?) {
        when (programKey) {
            DashboardViewModel.YARN_PURCHASE -> {
                navigateToYarnPurchaseFragment(null)
            }
            DashboardViewModel.KNITTING_PROGRAM -> {
                navigateToKnittingProgramFragment(null)
            }
            DashboardViewModel.GREY_FABRIC_STOCK -> {
                navigateToGreyFabricFragment(null)
            }
            DashboardViewModel.DYING_PROGRAM -> {
            }
            DashboardViewModel.DYED_FABRIC -> {
            }
            DashboardViewModel.CUTTING -> {
            }
            DashboardViewModel.FUSING -> {
            }
            DashboardViewModel.STITCHING -> {
            }
            DashboardViewModel.CHECKING -> {
            }
            DashboardViewModel.IRON_AND_PACK -> {
            }
            DashboardViewModel.SHIPMENT_READY -> {
            }
        }
    }

    private fun fetchProgramList(programKey: String) {

        binding?.mRVProgramDetails?.layoutManager = LinearLayoutManager(activity)

        when (programKey) {
            DashboardViewModel.YARN_PURCHASE -> {
                handleYarnPurchaseList()
            }
            DashboardViewModel.KNITTING_PROGRAM -> {
                handleKnittingProgramList()
            }
        }

    }

    private fun handleYarnPurchaseList() {
        //Update list titles
        updateListTitles(
            resources.getString(R.string.yarn_purchase_mill_name),
            resources.getString(R.string.description),
            resources.getString(R.string.yarn_purchase_no_of_bags),
            resources.getString(R.string.qty_in_kgs)
        )
        viewModel.fetchYarnStockDetails()

        viewModel.yarnPurchasePOListLiveData.observe(this) {
            //populate recycler view
            it.let {
                binding?.mRVProgramDetails?.adapter =
                    YarnStockListAdapter(it, object : YarnStockListAdapter.ItemSelectionListener {
                        override fun onItemSelection(yarnPurchasePO: YarnPurchasePO) {
                            LoggerUtils.debug(TAG, "onItemSelection")
                            val bundle = Bundle()
                            bundle.putSerializable(YARN_PURCHASE_PO, yarnPurchasePO)
                            navigateToYarnPurchaseFragment(bundle)
                        }
                    })
            }
        }
    }

    private fun updateListTitles(field1: String, field2: String, field3: String, field4: String) {
        binding?.mTVProgramListField1?.text = field1
        binding?.mTVProgramListField2?.text = field2
        binding?.mTVProgramListField3?.text = field3
        binding?.mTVProgramListField4?.text = field4
    }

    private fun navigateToYarnPurchaseFragment(bundle: Bundle?) {
        findNavController().navigate(
            R.id.action_ProgramDetailsFragment_to_YarnPurchaseFragment,
            bundle
        )
    }

    private fun handleKnittingProgramList() {
        //Update list titles
        updateListTitles(
            resources.getString(R.string.SRKW_DC_no),
            resources.getString(R.string.lot_track_name),
            resources.getString(R.string.description),
            resources.getString(R.string.qty_in_kgs)
        )

        viewModel.fetchKnittingProgramDetails()

        viewModel.knittingProgramPOListLiveData.observe(this) {
            //populate recycler view
            it.let {
                binding?.mRVProgramDetails?.adapter =
                    KnittingProgramListAdapter(it, object : KnittingProgramListAdapter.ItemSelectionListener {
                        override fun onItemSelection(knittingProgramPO: KnittingProgramPO) {
                            LoggerUtils.debug(TAG, "onItemSelection")
                            val bundle = Bundle()
                            bundle.putSerializable(KNITTING_PROGRAM_PO, knittingProgramPO)
                            navigateToKnittingProgramFragment(bundle)
                        }
                    })
            }
        }
    }

    private fun navigateToKnittingProgramFragment(bundle: Bundle?) {
        findNavController().navigate(R.id.action_ProgramDetailsFragment_to_KnittingDetailsFragment, bundle)
    }

    private fun handleGreyFabricList() {
        //Update list titles
        updateListTitles(
            resources.getString(R.string.SRKW_DC_no),
            resources.getString(R.string.lot_track_name),
            resources.getString(R.string.description),
            resources.getString(R.string.qty_in_kgs)
        )

        viewModel.fetchGreyFabricDetails()

        viewModel.greyFabricDetailsPOListLiveData.observe(this) {
            //populate recycler view
            it.let {
                binding?.mRVProgramDetails?.adapter =
                    GreyFabricListAdapter(it, object : GreyFabricListAdapter.ItemSelectionListener {
                        override fun onItemSelection(greyFabricDetailsPO: GreyFabricDetailsPO) {
                            LoggerUtils.debug(TAG, "onItemSelection")
                            val bundle = Bundle()
                            bundle.putSerializable(KNITTING_PROGRAM_PO, greyFabricDetailsPO)
                            navigateToGreyFabricFragment(bundle)
                        }
                    })
            }
        }
    }

    private fun navigateToGreyFabricFragment(bundle: Bundle?) {
        findNavController().navigate(R.id.action_ProgramDetailsFragment_to_greyFabricDetailsFragment, bundle)
    }


}