package com.JunHyeok0205.portfolio.moneystatus

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.JunHyeok0205.portfolio.moneystatus.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding

    var adapter = SearchItemAdapter()
    var helper = DBHelper(this)

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailBackImageView.setOnClickListener {
            finish()
        }

        binding.searchAppcompatButton.setOnClickListener {
            if (binding.searchEditText.text.isEmpty()) {
                Toast.makeText(this, "검색할 단어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val searchEditText = binding.searchEditText.text
                binding.searchResultText.text = "'$searchEditText'에 대한 검색 결과입니다."

                val layoutManager = LinearLayoutManager(this)
                binding.searchRecyclerView.layoutManager = layoutManager
                binding.searchRecyclerView.adapter = adapter
                adapter.listData.clear()
                adapter.listData.addAll(helper.selectSearchData(searchEditText.toString()))
                adapter.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        val searchEditText = binding.searchEditText.text
        adapter.listData.clear()
        adapter.listData.addAll(helper.selectSearchData(searchEditText.toString()))
        adapter.notifyDataSetChanged()

        super.onResume()
    }
}