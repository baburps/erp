package com.software.erp.view.programlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.software.erp.databinding.ItemKnittingProgramListBinding
import com.software.erp.view.knitting.KnittingProgramPO

class KnittingProgramListAdapter(
    private val knittingProgramPOList: List<KnittingProgramPO>,
    private val onItemSelectionListener: ItemSelectionListener?
) :
    RecyclerView.Adapter<KnittingProgramListAdapter.KnittingProgramListHolder>() {

    open class KnittingProgramListHolder(val binding: ItemKnittingProgramListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnittingProgramListHolder {
        val binding =
            ItemKnittingProgramListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KnittingProgramListHolder(binding)
    }

    override fun onBindViewHolder(holder: KnittingProgramListHolder, position: Int) {
        val knittingProgramPO = knittingProgramPOList[position]
        knittingProgramPO.let {
            it.dcNo.let { dcNo ->
                holder.binding.mTVItemKnittingDCNo.text = dcNo
            }

            it.lotTrackName.let { lotTrackName ->
                holder.binding.mTVItemKnittingLotTrackName.text = lotTrackName
            }

            it.goodsDesc.let { goodsDesc ->
                holder.binding.mTVItemKnittingDesc.text = goodsDesc
            }

            it.qtyInKgs.let { qtyInKgs ->
                holder.binding.mTVItemKnittingQtyInKgs.text = qtyInKgs
            }
        }

        holder.binding.root.setOnClickListener {
            onItemSelectionListener?.onItemSelection(knittingProgramPO)
        }
    }

    override fun getItemCount(): Int {
        return knittingProgramPOList.size
    }

    interface ItemSelectionListener {
        fun onItemSelection(knittingProgramPO: KnittingProgramPO)
    }
}