package com.software.erp.view.programlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.software.erp.databinding.ItemProgramParentListBinding

class ProgramListParentAdapter(
    private val parentAdapterPoList: List<ProgramParentAdapterPO>,
    private val onItemSelectionListener: ProgramListChildAdapter.ItemSelectionListener?,
    private val context: Context
) : RecyclerView.Adapter<ProgramListParentAdapter.ProgramListParentHolder>() {

    val TAG: String = "ProgramListParentAdapter"

    open class ProgramListParentHolder(val binding: ItemProgramParentListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramListParentHolder {
        val binding =
            ItemProgramParentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgramListParentHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramListParentHolder, position: Int) {
        val parentAdapterPO = parentAdapterPoList[position]
        holder.binding.mRVProgramParent.layoutManager = LinearLayoutManager(context)
        holder.binding.mRVProgramParent.isNestedScrollingEnabled = false

        parentAdapterPO.let {
            //Populate child view
            holder.binding.mRVProgramParent.adapter =
                ProgramListChildAdapter(parentAdapterPO.listOfEntries, onItemSelectionListener)
        }
    }

    override fun getItemCount(): Int {
        return parentAdapterPoList.size
    }
}