package com.software.erp.view.knitting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.software.erp.databinding.ItemGreyfabricMachineDetailsBinding
import com.software.erp.databinding.ItemKnittingMachineDetailsBinding
import com.software.erp.view.greyfabric.GreyFabricDia
import com.software.erp.view.knitting.model.FabricDia

class FabricDiaAdapter(
    private var fabricDiaList: List<FabricDia>? ,
    private var greyFabricDiaList: List<GreyFabricDia>?
) :
    RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val TAG: String = "FabricDiaAdapter"
    }

    fun updateList(fabricDiaList: List<FabricDia>) {
        this.fabricDiaList = fabricDiaList
        //Update last position only
        notifyItemInserted(fabricDiaList.size - 1)
    }

    open class FabricDiaViewHolder(val binding: ItemKnittingMachineDetailsBinding) : ViewHolder(binding.root)

    open class GreyFabricDiaViewHolder(val binding: ItemGreyfabricMachineDetailsBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder {
        fabricDiaList?.let {
            val binding =
                ItemKnittingMachineDetailsBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
            return FabricDiaViewHolder(binding)
        } ?: run {
            val binding =
                ItemGreyfabricMachineDetailsBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
            return GreyFabricDiaViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder , position: Int) {
        if (holder is FabricDiaViewHolder) {
            fabricDiaList?.let { fabricDiaList_ ->
                val fabricDiaPO = fabricDiaList_[position]
                val fabricDiaHolder = holder as FabricDiaViewHolder
                fabricDiaHolder.binding.fabricDia = fabricDiaPO
            }
        } else {
            greyFabricDiaList?.let { greyFabricDiaList_ ->
                val fabricDiaPO = greyFabricDiaList_[position]
                val fabricDiaHolder = holder as GreyFabricDiaViewHolder
                fabricDiaHolder.binding.fabricDia = fabricDiaPO
            }
        }
    }

    override fun getItemCount(): Int {
        fabricDiaList?.let { dia ->
            return dia.size
        } ?: run {
            greyFabricDiaList?.let { greyDia ->
                return greyDia.size
            }
        }
        return 0
    }
}