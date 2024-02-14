package com.JunHyeok0205.portfolio.moneystatus

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.JunHyeok0205.portfolio.moneystatus.databinding.ActivityGraphBinding
import java.text.DecimalFormat
import java.time.LocalDate
import kotlin.collections.ArrayList

class GraphActivity : AppCompatActivity() {

    lateinit var binding: ActivityGraphBinding
    private var helper = DBHelper(this)

    private lateinit var pie: PieChart
    private lateinit var set: PieDataSet
    private lateinit var data: PieData
    private var entries = arrayListOf<PieEntry>()

    var priceAll: Int = 0
    var categoryList: MutableList<String>? = null
    var priceSumList = mutableListOf<Int>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.graphBackImageView.setOnClickListener {
            finish()
        }

        val database = helper.writableDatabase
        val date: LocalDate = LocalDate.now()
        val year: Int = date.year

        binding.graphSubText.text = "${year}년"

        val dbCategoryList: MutableList<String> = helper.graphCategorySearchData(database, year) // 올해의 카테고리들
        val unionList = dbCategoryList.union(dbCategoryList) // 중복제거
        categoryList = unionList.toMutableList() // 중복을 삭제한 배열

        for (i in 0 until categoryList!!.size) { // 디비에서 가져온 금액들 중, 카테고리가 중복된 것들은 모두 더해줌
                val priceList = helper.graphPriceOfSearchCategoryData(database, categoryList!![i])
            priceSumList.add(priceList.sum())
        }

        pie = binding.pieChart
        setData()
        setUpData()
    }

    private fun setData() {
        pie.setUsePercentValues(true) //entries
        entries = arrayListOf()
        for (i in 0 until priceSumList.size) {
            entries.add(
                PieEntry( // 파이 그래프의 비율과 범례를 PieEntry에 뿌려줌
                    priceSumList[i].toFloat(),
                    categoryList!![i]
                )
            )
        }
        for (i in 0 until priceSumList.size) {
            priceAll += priceSumList[i] // 중앙에 위치한 총 금액 계산
        }

        set = PieDataSet(entries, "") // 범례 맨 오른쪽 레이블(공백)

        // 차트에서 범례에 따라 색을 입혀주는 부분
        val colorItems = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorItems.add(c)
        for (c in ColorTemplate.MATERIAL_COLORS) colorItems.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colorItems.add(c)
        colorItems.add(ColorTemplate.getHoloBlue())

        set.colors = colorItems
        set.selectionShift = 40f
        set.isUsingSliceColorAsValueLineColor = true // 차트 레이블을 가리키는 선의 색상을 레이블과 동일하게 변경
        set.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE // 카테고리명에 해당하는 레이블을 바깥쪽에 배치
        set.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE // 퍼센테이지에 해당하는 레이블을 바깥족에 배치


        data = PieData(set)
        data.setValueFormatter(PercentFormatter()) // 데이터를 퍼센테이지 개념으로 포맷
        data.setValueTextSize(15f) // 차트의 데이터에서, 비율을 표시하는 부분

    }

    private fun setUpData() {
        pie.data = data
        pie.isDrawHoleEnabled = true
        pie.setEntryLabelColor(Color.BLACK)
        pie.setEntryLabelTextSize(12f) // 차트의 데이터(카테고리)를 표시하는 부분
        pie.description.isEnabled = false // 차트 우측 하단 레이블 제거(차트 제목)
        pie.minAngleForSlices = 10f
        pie.setUsePercentValues(true)
        pie.animateY(2000, Easing.EaseInOutQuad) // 차트가 애니메이션으로 전부 펼쳐질 때까지 걸리는 시간

        val legend = pie.legend // 범례 위치를 아래쪽 가운데로 정렬
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

        // 차트의 가운데 부분 총 금액을 텍스트로 나타내는 부분
        val decUp = DecimalFormat("#,###")
        val decUpPrice = decUp.format(priceAll)
        pie.centerText = "총 금액\n" + "${decUpPrice}원"
        pie.setCenterTextSize(18f)

        pie.invalidate()
    }
}