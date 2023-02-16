package com.software.erp.view.knitting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.software.erp.databinding.ItemKnittingMachineDetailsBinding

class FabricDiaAdapter(private var fabricDiaList: List<FabricDia>) :
    RecyclerView.Adapter<FabricDiaAdapter.FabricDiaViewHolder>() {

    companion object {
        const val TAG: String = "FabricDiaAdapter"
    }

    fun updateList(fabricDiaList: List<FabricDia>) {
        this.fabricDiaList = fabricDiaList
        //Update last position only
        notifyItemInserted(fabricDiaList.size - 1)
    }

    open class FabricDiaViewHolder(val binding: ItemKnittingMachineDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): FabricDiaViewHolder {
        val binding =
            ItemKnittingMachineDetailsBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return FabricDiaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FabricDiaViewHolder , position: Int) {
        val fabricDiaPO = fabricDiaList[position]
        holder.binding.fabricDia = fabricDiaPO
    }

    override fun getItemCount(): Int {
        return fabricDiaList.size
    }
}