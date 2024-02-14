package com.JunHyeok0205.portfolio.moneystatus

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.JunHyeok0205.portfolio.moneystatus.databinding.MainItemviewBinding
import java.text.DecimalFormat

class ItemAdapter(): RecyclerView.Adapter<ItemAdapter.Holder>() {

    var listData = mutableListOf<recyclerData>()

    override fun getItemCount(): Int { // 리사이클러뷰에 표시될 아이템의 갯수
        return listData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.Holder =
        Holder( // 뷰 홀더를 생성하는 메소드
            MainItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    // 뷰 홀더를 재사용할때 사용하는 메소드
    // 포지션 값에 따라 데이터를 뿌려주고, 데이터를 클릭 했을 시 이벤트를 실행
   override fun onBindViewHolder(holder: Holder, position: Int) {
        listData.sortByDescending { it.price } // 데이터를 내림차순으로 정렬
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

    inner class Holder(private val binding: MainItemviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: recyclerData) { // 리사이클러뷰에 데이터 클래스의 데이터를 삽입
            binding.dateItemView.text = data.date
            binding.nameItemView.text = data.menu
            binding.categoryItemView.text = data.category

            val decUp = DecimalFormat("#,###")
            val priceData = decUp.format(data.price)
            binding.priceItemView.text = priceData.toString()
        }
    }

}