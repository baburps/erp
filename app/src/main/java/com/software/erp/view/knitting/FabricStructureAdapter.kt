package com.software.erp.view.knitting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.software.erp.databinding.ItemGreyFabricStructureBinding
import com.software.erp.databinding.ItemKnittingFabricStructureBinding
import com.software.erp.view.greyfabric.GreyFabricStructurePO
import com.software.erp.view.knitting.model.FabricStructurePO

class FabricStructureAdapter(
    private var fabricStructurePOList: List<FabricStructurePO>? ,
    private var greyFabricStructurePOList: List<GreyFabricStructurePO>? ,
    private val pageKey: String
) :
    RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val TAG: String = "FabricStructureAdapter"
        const val PAGE_KEY_GREY_FABRIC = "PAGE_KEY_GREY_FABRIC"
        const val PAGE_KEY_KNITTING = "PAGE_KEY_KNITTING"
    }

    fun getFabricStructureList(): List<FabricStructurePO>? {
        return fabricStructurePOList
    }

    fun updateList(fabricStructurePOList: List<FabricStructurePO>) {
        this.fabricStructurePOList = fabricStructurePOList
        //Update last position only
        notifyItemInserted(fabricStructurePOList.size - 1)
    }

    open class FabricStructureViewHolder(val binding: ItemKnittingFabricStructureBinding) : ViewHolder(binding.root)

    open class GreyFabricStructureViewHolder(val binding: ItemGreyFabricStructureBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder {
        fabricStructurePOList?.let {
            val binding =
                ItemKnittingFabricStructureBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
            return FabricStructureViewHolder(binding)
        } ?: run {
            val binding =
                ItemGreyFabricStructureBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
            return GreyFabricStructureViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder , position: Int) {
        if (holder is FabricStructureViewHolder) {
            val fabricHolder = holder as FabricStructureViewHolder
            fabricHolder.binding.pageKey = pageKey
            fabricStructurePOList?.let { fabricStructureList ->
                val fabricStructurePO = fabricStructureList[position]
                fabricHolder.binding.fabricStructurePO = fabricStructurePO
            }
        } else {
            val greyFabricHolder = holder as GreyFabricStructureViewHolder
            greyFabricHolder.binding.pageKey = pageKey
            greyFabricStructurePOList?.let { greyFabricStructureList ->
                val fabricStructurePO = greyFabricStructureList[position]
                greyFabricHolder.binding.greyFabricStructurePO = fabricStructurePO
            }
        }
    }

    override fun getItemCount(): Int {
        if (fabricStructurePOList != null) {
            return fabricStructurePOList!!.size
        } else if (greyFabricStructurePOList != null) {
            return greyFabricStructurePOList!!.size
        }
        return 0
    }
}