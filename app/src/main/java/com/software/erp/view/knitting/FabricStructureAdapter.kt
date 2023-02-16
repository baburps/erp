package com.software.erp.view.knitting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.software.erp.databinding.ItemKnittingFabricStructureBinding

class FabricStructureAdapter(private val context: Context , private var fabricStructurePOList: List<FabricStructurePO>) :
    RecyclerView.Adapter<FabricStructureAdapter.FabricStructureViewHolder>() {

    companion object {
        const val TAG: String = "FabricStructureAdapter"
    }

    private var fabricDiaAdapter: FabricDiaAdapter? = null

    fun updateList(fabricStructurePOList: List<FabricStructurePO>) {
        this.fabricStructurePOList = fabricStructurePOList
        //Update last position only
        notifyItemInserted(fabricStructurePOList.size - 1)
    }

    open class FabricStructureViewHolder(val binding: ItemKnittingFabricStructureBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): FabricStructureViewHolder {
        val binding =
            ItemKnittingFabricStructureBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return FabricStructureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FabricStructureViewHolder , position: Int) {
        val fabricStructurePO = fabricStructurePOList[position]
        holder.binding.fabricStructurePO = fabricStructurePO
    }

    override fun getItemCount(): Int {
        return fabricStructurePOList.size
    }
}