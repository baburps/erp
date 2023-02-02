package com.software.erp.view.programlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.software.erp.databinding.ItemProgramChildListBinding

class ProgramListChildAdapter(
    private val childAdapterPOList: List<ProgramChildAdapterPO>,
    private val onItemSelectionListener: ItemSelectionListener?
): RecyclerView.Adapter<ProgramListChildAdapter.ProgramListChildHolder>() {

    open class ProgramListChildHolder(val binding: ItemProgramChildListBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ItemSelectionListener {
        fun onItemSelection(programChildAdapterPO: ProgramChildAdapterPO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramListChildHolder {
        val binding =
            ItemProgramChildListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgramListChildHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramListChildHolder, position: Int) {
        val childAdapterPO = childAdapterPOList[position]
        holder.binding.model = childAdapterPO
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener {
            onItemSelectionListener?.onItemSelection(childAdapterPO)
        }
    }

    override fun getItemCount(): Int {
        return childAdapterPOList.size
    }
}