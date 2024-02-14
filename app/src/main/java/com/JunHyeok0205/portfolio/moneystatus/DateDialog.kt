package com.JunHyeok0205.portfolio.moneystatus

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class DateDialog(context: Context) {

    private val dialog = Dialog(context)

    @SuppressLint("SetTextI18n")
    fun myDig(value: String) {
        dialog.setContentView(R.layout.date_dialog)
        dialog.setCanceledOnTouchOutside(true) // 다이얼로그의 바깥(어두운 부분)을 터치했을 떄, 다이얼로그를 종료
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 백그라운드의 컬러를 투명하게 변경

        val year = dialog.findViewById<TextView>(R.id.dialogYearTextView)
        val leftButton = dialog.findViewById<ImageView>(R.id.dialogLeftImageView)
        val rightButton = dialog.findViewById<ImageView>(R.id.dialogRightImageView)
        var headYear = 0 // 다이얼로그가 띄워질 때 나오는 연도(초기값)

        headYear = value.toInt()

        leftButton.setOnClickListener {
            headYear--
            year.text = "${headYear}년"
        }

        rightButton.setOnClickListener {
            headYear++
            year.text = "${headYear}년"
        }
        year.text = "${headYear}년"

        val january = dialog.findViewById<AppCompatButton>(R.id.january)
        val february = dialog.findViewById<AppCompatButton>(R.id.february)
        val march = dialog.findViewById<AppCompatButton>(R.id.march)
        val april = dialog.findViewById<AppCompatButton>(R.id.april)
        val may = dialog.findViewById<AppCompatButton>(R.id.may)
        val june = dialog.findViewById<AppCompatButton>(R.id.june)
        val july = dialog.findViewById<AppCompatButton>(R.id.july)
        val august = dialog.findViewById<AppCompatButton>(R.id.august)
        val september = dialog.findViewById<AppCompatButton>(R.id.september)
        val october = dialog.findViewById<AppCompatButton>(R.id.october)
        val november = dialog.findViewById<AppCompatButton>(R.id.november)
        val december = dialog.findViewById<AppCompatButton>(R.id.december)

        january.setOnClickListener {
            onClickListener.onClicked(january.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        february.setOnClickListener {
            onClickListener.onClicked(february.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        march.setOnClickListener {
            onClickListener.onClicked(march.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        april.setOnClickListener {
            onClickListener.onClicked(april.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        may.setOnClickListener {
            onClickListener.onClicked(may.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        june.setOnClickListener {
            onClickListener.onClicked(june.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        july.setOnClickListener {
            onClickListener.onClicked(july.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        august.setOnClickListener {
            onClickListener.onClicked(august.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        september.setOnClickListener {
            onClickListener.onClicked(september.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        october.setOnClickListener {
            onClickListener.onClicked(october.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        november.setOnClickListener {
            onClickListener.onClicked(november.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        december.setOnClickListener {
            onClickListener.onClicked(december.text.toString(), year.text.toString())
            dialog.dismiss()
        }

        dialog.show()
    }

    interface ButtonClickListener {
        fun onClicked(content: String, value: String)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickListener = listener
    }

}