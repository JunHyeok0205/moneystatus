package com.JunHyeok0205.portfolio.moneystatus

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Dimension
import com.JunHyeok0205.portfolio.moneystatus.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    var calendarYearMonth: String = null.toString()
    var calendarDateString = ""
    var calendardate = ""
    var calendarDateOfDay = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // itemAdapter에서 클릭한 아이템뷰의 데이터
        val id = intent.getIntExtra("id", 1)
        val today = intent.getStringExtra("todaydata")
        val yearMonth = intent.getStringExtra("yearandmonthdata")
        val date = intent.getStringExtra("datedata")
        val menu = intent.getStringExtra("menudata")
        val category = intent.getStringExtra("categorydata")
        val price = intent.getStringExtra("pricedata")
        val impression = intent.getStringExtra("impressiondata")

        binding.detailCalenderText.text = today
        binding.detailMenu.setText(menu)
        binding.detailCategory.setText(category)
        binding.detailPrice.setText(price)
        binding.detailImpression.setText(impression)
        calendarYearMonth = yearMonth!!
        calendarDateOfDay = date!!

        binding.detailCalenderImage.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    calendarDateString = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    binding.detailCalenderText.setTextSize(Dimension.SP, 17F)
                    binding.detailCalenderText.setTextColor(Color.parseColor("#000000"))
                    binding.detailCalenderText.text = calendarDateString
                    calendardate = dayOfMonth.toString()
                    calendarYearMonth = "${year}년 ${month + 1}월"
                    calendarDateOfDay = "${dayOfMonth}일"
                }
            DatePickerDialog(
                this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()
        }

        binding.detailBackImageView.setOnClickListener {
            this.setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.modifyButton.setOnClickListener {
            Toast.makeText(this@DetailActivity, "수정되었습니다.", Toast.LENGTH_SHORT).show()
            DBHelper(this).updateData(
                binding.detailCalenderText.text.toString(),
                calendarYearMonth,
                calendarDateOfDay,
                binding.detailMenu.text.toString(),
                binding.detailCategory.text.toString(),
                binding.detailPrice.text.toString().toInt(),
                binding.detailImpression.text.toString(),
                id
            )
            finish()
        }

        binding.deleteButton.setOnClickListener {
            Toast.makeText(this@DetailActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            DBHelper(this).deleteData(id)
            finish()
        }
    }
}