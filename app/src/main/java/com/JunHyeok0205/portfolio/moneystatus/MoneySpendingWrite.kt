package com.JunHyeok0205.portfolio.moneystatus

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.Dimension
import com.JunHyeok0205.portfolio.moneystatus.databinding.ActivityMoneySpendingWriteBinding

class MoneySpendingWrite : AppCompatActivity() {

    var yearandmonth = ""
    var dateString = ""
    var date = ""
    var dateOfDay: String = ""
    var helper = DBHelper(this)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMoneySpendingWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calenderImage.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    // 날짜를 보여주는 다이얼로그를 생성, 그곳에서 삽입되는 데이터들을 view, year, month, dayOfMonth의 순서대로 dateSetListenter에 적용
                    dateString = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    binding.calenderText.setTextSize(Dimension.SP, 17F) // 달력 선택하고 날짜가 텍스트로 나올 때 크기 조정
                    binding.calenderText.setTextColor(Color.parseColor("#000000")) // 색 변경(검은색)
                    binding.calenderText.text = dateString // UI파일 내에 텍스트를 해당하는 날짜로 변경
                    date = dayOfMonth.toString()
                    yearandmonth = "${year}년 ${month + 1}월"
                    dateOfDay = "${dayOfMonth}일"
                }
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show() // Calendar함수에서 필요한 부분을 가져와 show
        }

        binding.spendingBackImageView.setOnClickListener {
            this.setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.checkButton.setOnClickListener {
            Log.d("zzzz", dateString)
            Log.d("dataaaa", yearandmonth)
            if (binding.spendingMenu.text.isEmpty() ||
                binding.spendingCategory.text.isEmpty() ||
                binding.spendingPrice.text.isEmpty() ||
                binding.spendingDetail.text.isEmpty() ||
                dateString.isEmpty()) {
                Toast.makeText(this, "모든 사항을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val inputToday = dateString
                val inputYearMonth = yearandmonth
                val inputDate = dateOfDay
                val inputMenu = binding.spendingMenu.text.toString()
                val inputCategory = binding.spendingCategory.text.toString()
                val inputPrice = binding.spendingPrice.text.toString().toInt()
                val inputImpression = binding.spendingDetail.text.toString()

                helper.insertData(
                    inputToday,
                    inputYearMonth,
                    inputDate,
                    inputMenu,
                    inputCategory,
                    inputPrice,
                    inputImpression
                )
                setResult(Activity.RESULT_OK, intent)
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}