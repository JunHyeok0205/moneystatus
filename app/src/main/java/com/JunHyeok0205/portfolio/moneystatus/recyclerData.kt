package com.JunHyeok0205.portfolio.moneystatus

data class recyclerData(
    var id: Int,
    var today: String,            // 오늘 날짜(연월일)
    var yearandmonth: String,     // 오늘 연월
    var date: String,
    var menu: String,
    var category: String,
    var price: Int,
    var impression: String
    )