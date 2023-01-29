package com.software.erp.view.programlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.software.erp.databinding.ItemYarnStockListBinding
import com.software.erp.view.yarnpurchase.YarnPurchasePO

class YarnStockListAdapter constructor(private val yarnStockList: List<YarnPurchasePO>) : RecyclerView.Adapter<YarnStockListAdapter.YarnStockListHolder>() {

    open class YarnStockListHolder(val binding: ItemYarnStockListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YarnStockListHolder {
        val binding = ItemYarnStockListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YarnStockListHolder(binding)
    }

    override fun onBindViewHolder(holder: YarnStockListHolder, position: Int) {
        val yarnPurchasePO = yarnStockList[position]
        yarnPurchasePO.let {
            it.spinnerMill.let { millName ->
                holder.binding.mTVItemYarnMillName.text = millName
            }

            it.goodsDesc.let { goodsDesc ->
                holder.binding.mTVItemYarnDesc.text = goodsDesc
            }

            it.noOfBags.let { noOfBags ->
                holder.binding.mTVItemYarnNoOfBags.text = noOfBags
            }

            it.qtyInKgs.let { qtyInKgs ->
                holder.binding.mTVItemYarnQtyInKgs.text = qtyInKgs
            }
        }
    }

    override fun getItemCount(): Int {
        return yarnStockList.size
    }
}