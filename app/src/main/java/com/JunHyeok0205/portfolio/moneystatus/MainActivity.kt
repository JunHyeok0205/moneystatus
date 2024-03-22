package com.JunHyeok0205.portfolio.moneystatus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.JunHyeok0205.portfolio.moneystatus.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.Month

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var backKeyPressedTime: Long = 0

    var helper = DBHelper(this)
    var adapter = ItemAdapter()

    @SuppressLint("NotifyDataSetChanged", "ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // 스플래시 화면을 띄워줌 (Android 12 이상)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 지출작성 액티비티에서 메인액티비티로 넘어왔을 때, 가져온 데이터를 저장
        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {

        }

        val date: LocalDate = LocalDate.now()
        val year: Int = date.year
        val monthInstance: Month? = date.month
        val month: Int = monthInstance!!.value

        binding.mainDate.text = "${year}년 ${month}월" // 날짜 변경 버튼 기본 값(연/월)

        binding.mainDate.setOnClickListener {
            val mainDateYearString = binding.mainDate.text.substring(0 until 4)
            val dialog = DateDialog(this)
            dialog.myDig(mainDateYearString)
            dialog.setOnClickedListener(object : DateDialog.ButtonClickListener {
                override fun onClicked(content: String, value: String) { // 클릭시 해당하는 날짜의 리사이클러뷰를 새로 띄움
                    binding.mainDate.text = "$value $content"
                    adapter.listData.clear()
                    adapter.listData.addAll(helper.selectDatabase(binding.mainDate.text.toString()))
                    adapter.notifyDataSetChanged()
                }
            })
        }

        binding.moneyPencil.setOnClickListener {
            val intent = Intent(this, MoneySpendingWrite::class.java)
            requestLauncher.launch(intent)
        }

        binding.mainSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.mainGraph.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.layoutManager = layoutManager
        binding.mainRecyclerView.adapter = adapter

    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    fun mainActivityData() { // onResume에 들어가는 함수
        val date: LocalDate = LocalDate.now()
        val year: Int = date.year
        val monthInstance: Month? = date.month
        val month: Int = monthInstance!!.value
        val day: Int = date.dayOfMonth

        val database = helper.writableDatabase
        var todayPriceSum = 0
        var monthPriceSum = 0
        val today = "${year}년 ${month}월 ${day}일"
        val todayYM = "${year}년 ${month}월"

        val todayPriceDB = helper.selectPrice(database) // DB에 저장된 가격들의 배열
        val todayDateDB = helper.selectDate(database) // DB에 저장된 날짜들의 배열
        val todayMonthDB = helper.selectMonth(database) // DB에 저장된 날짜 중 월의 배열

        for (i in 0 until todayDateDB.size) {
            if (today == todayDateDB[i]) {
                todayPriceSum += todayPriceDB[i]
            }
            if (todayYM == todayMonthDB[i]) {
                monthPriceSum += todayPriceDB[i]
            }
        }

        val decUp = DecimalFormat("#,###")
        val decimalMonthPriceSum = decUp.format(monthPriceSum)
        val decimalTodayPriceSum = decUp.format(todayPriceSum)

        binding.monthExpend.text = decimalMonthPriceSum.toString()
        binding.todayExpend.text = decimalTodayPriceSum.toString()

        val layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.layoutManager = layoutManager
        binding.mainRecyclerView.adapter = adapter
        adapter.listData.clear()
        adapter.listData.addAll(helper.selectDatabase(binding.mainDate.text.toString()))
        adapter.notifyDataSetChanged()
    }

    override fun onResume() { // 리사이클러뷰가 새로 갱신되면 입력을 받아 저절로 새로고침이 됨
        mainActivityData()
        super.onResume()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() { // 뒤로가기
        if (backKeyPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            ActivityCompat.finishAffinity(this) // 한번에 모든 액티비티를 종료
        } else Toast.makeText(applicationContext, "한번 더 버튼을 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backKeyPressedTime = System.currentTimeMillis()
    }
}