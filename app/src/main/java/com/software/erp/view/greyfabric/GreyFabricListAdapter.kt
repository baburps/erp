package com.software.erp.view.greyfabric

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.software.erp.databinding.ItemKnittingProgramListBinding

class GreyFabricListAdapter(
    private val greyFabricDetailsPOList: List<GreyFabricDetailsPO>,
    private val onItemSelectionListener: ItemSelectionListener?
) :
    RecyclerView.Adapter<GreyFabricListAdapter.GreyFabricListHolder>() {

    open class GreyFabricListHolder(val binding: ItemKnittingProgramListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreyFabricListHolder {
        val binding =
            ItemKnittingProgramListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GreyFabricListHolder(binding)
    }

    override fun onBindViewHolder(holder: GreyFabricListHolder, position: Int) {
        val knittingProgramPO = greyFabricDetailsPOList[position]
        knittingProgramPO.let {
            it.knittingDCNo.let { dcNo ->
                holder.binding.mTVItemKnittingDCNo.text = dcNo
            }

            it.lotTrackName.let { lotTrackName ->
                holder.binding.mTVItemKnittingLotTrackName.text = lotTrackName
            }

            it.goodsDesc.let { goodsDesc ->
                holder.binding.mTVItemKnittingDesc.text = goodsDesc
            }

            it.programmedQtyInKgs.let { qtyInKgs ->
                holder.binding.mTVItemKnittingQtyInKgs.text = qtyInKgs
            }
        }

        holder.binding.root.setOnClickListener {
            onItemSelectionListener?.onItemSelection(knittingProgramPO)
        }
    }

    override fun getItemCount(): Int {
        return greyFabricDetailsPOList.size
    }

    interface ItemSelectionListener {
        fun onItemSelection(greyFabricDetailsPO: GreyFabricDetailsPO)
    }
}