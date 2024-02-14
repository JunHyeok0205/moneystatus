package com.JunHyeok0205.portfolio.moneystatus

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "appDB", null, 2) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "create table if not exists statusDB (" +
                "id integer primary key autoincrement," +
                "today not null," +
                "yearandmonth not null," +
                "date not null, menu not null, category not null, price not null, impression not null)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // record update
    }

    @SuppressLint("Range", "Recycle")
    fun selectDate(db: SQLiteDatabase) : MutableList<String> { // only today schema select
        val sql = "select * from statusDB"
        val result = db.rawQuery(sql, null)
        val list = mutableListOf<String>()
        while (result!!.moveToNext()) {
            list += result.getString(result.getColumnIndex("today"))
        }
        return list
    }

    @SuppressLint("Range", "Recycle")
    fun selectPrice(db: SQLiteDatabase) : MutableList<Int> { // only price schema select
        val sql = "select * from statusDB"
        val result = db.rawQuery(sql, null)
        val list = mutableListOf<Int>()
        while (result!!.moveToNext()) {
            list += result.getInt(result.getColumnIndex("price"))
        }
        return list
    }

    @SuppressLint("Range", "Recycle")
    fun selectMonth(db: SQLiteDatabase) : MutableList<String> { // only month schema select
        val sql = "select * from statusDB"
        val result = db.rawQuery(sql, null)
        val list = mutableListOf<String>()
        while (result!!.moveToNext()) {
            list += result.getString(result.getColumnIndex("yearandmonth"))
        }
        return list
    }

    @SuppressLint("Range", "Recycle")
    fun selectDatabase(date: String) : MutableList<recyclerData> {
        val db = readableDatabase
        val datas = mutableListOf<recyclerData>()
        val cursor = db.rawQuery("select * from statusDB where yearandmonth = '${date}'", null)
        cursor.run {
            while (moveToNext()) {
                val ids = cursor.getInt(cursor.getColumnIndex("id"))
                val todays = cursor.getString(cursor.getColumnIndex("today"))
                val yearandmonths = cursor.getString(cursor.getColumnIndex("yearandmonth"))
                val dates = cursor.getString(cursor.getColumnIndex("date"))
                val menus = cursor.getString(cursor.getColumnIndex("menu"))
                val categorys = cursor.getString(cursor.getColumnIndex("category"))
                val prices = cursor.getInt(cursor.getColumnIndex("price"))
                val impressions = cursor.getString(cursor.getColumnIndex("impression"))
                datas.add(recyclerData(ids, todays, yearandmonths, dates, menus, categorys, prices, impressions))
            }
        }
        db.close()
        return datas
    }

    @SuppressLint("Range", "Recycle")
    fun selectSearchData(searchText: String): MutableList<recyclerData> {
        val db = readableDatabase
        val datas = mutableListOf<recyclerData>()
        val cursor = db.rawQuery("select * from statusDB where menu like '%${searchText}%' or category like '%${searchText}%'", null)
        cursor.run {
            while (moveToNext()) {
                val ids = cursor.getInt(cursor.getColumnIndex("id"))
                val todays = cursor.getString(cursor.getColumnIndex("today"))
                val yearandmonths = cursor.getString(cursor.getColumnIndex("yearandmonth"))
                val dates = cursor.getString(cursor.getColumnIndex("date"))
                val menus = cursor.getString(cursor.getColumnIndex("menu"))
                val categorys = cursor.getString(cursor.getColumnIndex("category"))
                val prices = cursor.getInt(cursor.getColumnIndex("price"))
                val impressions = cursor.getString(cursor.getColumnIndex("impression"))
                datas.add(recyclerData(ids, todays, yearandmonths, dates, menus, categorys, prices, impressions))
            }
        }
        db.close()
        return datas
    }

    // 디비에 있는 today를 이용하여 올해의 카테고리 값을 뽑아옴
    @SuppressLint("Range", "Recycle")
    fun graphCategorySearchData(db: SQLiteDatabase, yearData: Int): MutableList<String> {
        val sql = "select * from statusDB where today like  '%${yearData}%'"
        val result = db.rawQuery(sql, null)
        val list = mutableListOf<String>()
        while (result!!.moveToNext()) {
            list += result.getString(result.getColumnIndex("category"))
        }
        return list
    }

    @SuppressLint("Range", "Recycle")
    fun graphPriceOfSearchCategoryData(db: SQLiteDatabase, category: String): MutableList<Int> {
        val sql = "select price from statusDB where category like '${category}'"
        val result = db.rawQuery(sql, null)
        val list = mutableListOf<Int>()
        while (result!!.moveToNext()) {
            list += result.getInt(result.getColumnIndex("price"))
        }
        return list
    }

    fun insertData(inputToday: String,
                   inputYearandmonth: String,
                   inputDate: String,
                   inputMenu: String,
                   inputCategory: String,
                   inputPrice: Int,
                   inputImpression: String) {
        val db = writableDatabase
        db.execSQL(
            "insert into statusDB (today, yearandmonth, date, menu, category, price, impression) values (?,?,?,?,?,?,?)",
            arrayOf(inputToday, inputYearandmonth, inputDate, inputMenu, inputCategory, inputPrice, inputImpression)
        )
        db.close()
    }

    // primary key인 id값을 기준으로 데이터를 변경, id 값이 계속 중첩됨
    fun updateData(today: String,
                   yearandmonth: String,
                   date: String,
                   menu: String,
                   category: String,
                   price: Int,
                   impression: String,
                   id: Int) {
        val todaySql = "update statusDB set today = '${today}' where id = '${id}'"
        val todayDB = writableDatabase
        todayDB.execSQL(todaySql)

        val yearandmonthSql = "update statusDB set yearandmonth = '${yearandmonth}' where id = '${id}'"
        val yearandmonthDB = writableDatabase
        yearandmonthDB.execSQL(yearandmonthSql)

        val dateSql = "update statusDB set date = '${date}' where id = '${id}'"
        val dateDB = writableDatabase
        dateDB.execSQL(dateSql)

        val menuSql = "update statusDB set menu = '${menu}' where id = '${id}'"
        val menuDB = writableDatabase
        menuDB.execSQL(menuSql)

        val categorySql = "update statusDB set category = '${category}' where id = '${id}'"
        val categoryDB = writableDatabase
        categoryDB.execSQL(categorySql)

        val priceSql = "update statusDB set price = '${price}' where id = '${id}'"
        val priceDB = writableDatabase
        priceDB.execSQL(priceSql)

        val impressionSql = "update statusDB set impression = '${impression}' where id = '${id}'"
        val impressionDB = writableDatabase
        impressionDB.execSQL(impressionSql)
    }

    fun deleteData(id: Int) { // id를 이용하여 해당하는 id에 있는 데이터베이스 스키마를 삭제
        val deleteSql = "delete from statusDB where id = '${id}'"
        val deleteDB = writableDatabase
        deleteDB.execSQL(deleteSql)
    }
}