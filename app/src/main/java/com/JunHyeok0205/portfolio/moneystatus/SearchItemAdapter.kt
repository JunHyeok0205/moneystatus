package com.JunHyeok0205.portfolio.moneystatus

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.JunHyeok0205.portfolio.moneystatus.databinding.SearchItemviewBinding
import java.text.DecimalFormat

class SearchItemAdapter(): RecyclerView.Adapter<SearchItemAdapter.Holder>() {

    var listData = mutableListOf<recyclerData>()

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemAdapter.Holder =
        Holder(
            SearchItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        listData.sortByDescending { it.price }
        val data = listData[position]
        holder.setData(data)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("id", listData[position].id)
            intent.putExtra("todaydata", listData[position].today)
            intent.putExtra("yearandmonthdata", listData[position].yearandmonth)
            intent.putExtra("datedata", listData[position].date)
            intent.putExtra("menudata", listData[position].menu)
            intent.putExtra("categorydata", listData[position].category)
            intent.putExtra("pricedata", listData[position].price.toString())
            intent.putExtra("impressiondata", listData[position].impression)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    inner class Holder(private val binding: SearchItemviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: recyclerData) {
            binding.dateSearchItemView.text = data.today
            binding.nameSearchItemView.text = data.menu
            binding.categorySearchItemView.text = data.category

            val decUp = DecimalFormat("#,###")
            val priceData = decUp.format(data.price)
            binding.priceSearchItemView.text = priceData.toString()
        }
    }
}