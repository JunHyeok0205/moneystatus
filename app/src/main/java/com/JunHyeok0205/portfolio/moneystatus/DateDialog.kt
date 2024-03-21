package com.JunHyeok0205.portfolio.moneystatus

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.JunHyeok0205.portfolio.moneystatus.databinding.DateDialogBinding

class DateDialog(context: AppCompatActivity) {

    private val dialog = Dialog(context)
    private lateinit var binding: DateDialogBinding

    @SuppressLint("SetTextI18n")
    fun myDig(value: String) {
        binding = DateDialogBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(true) // 다이얼로그의 바깥(어두운 부분)을 터치했을 떄, 다이얼로그를 종료
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 백그라운드의 컬러를 투명하게 변경

        var dialogYear = 0 // 다이얼로그가 띄워질 때 나오는 연도(초기값)

        dialogYear = value.toInt()

        binding.dialogLeftImageView.setOnClickListener {
            dialogYear--
            binding.dialogYearTextView.text = "${dialogYear}년"
        }

        binding.dialogRightImageView.setOnClickListener {
            dialogYear++
            binding.dialogYearTextView.text = "${dialogYear}년"
        }
        binding.dialogYearTextView.text = "${dialogYear}년"

        val monthButtonArray = ArrayList<AppCompatButton>() // 위의 날짜 버튼들의 클릭 이벤트 처리를 위해 만들어준 배열

        monthButtonClicked(binding.january, binding.dialogYearTextView)
        monthButtonClicked(binding.february, binding.dialogYearTextView)
        monthButtonClicked(binding.march, binding.dialogYearTextView)
        monthButtonClicked(binding.april, binding.dialogYearTextView)
        monthButtonClicked(binding.may, binding.dialogYearTextView)
        monthButtonClicked(binding.june, binding.dialogYearTextView)
        monthButtonClicked(binding.july, binding.dialogYearTextView)
        monthButtonClicked(binding.august, binding.dialogYearTextView)
        monthButtonClicked(binding.september, binding.dialogYearTextView)
        monthButtonClicked(binding.october, binding.dialogYearTextView)
        monthButtonClicked(binding.november, binding.dialogYearTextView)
        monthButtonClicked(binding.december, binding.dialogYearTextView)
        // TODO LIST : monthButtonClicked() 함수 호출 1회로 모든 달 반복

        dialog.show()
    }

    private fun monthButtonClicked(monthButton: AppCompatButton, yearText: TextView) {
        monthButton.setOnClickListener {
            onClickListener.onClicked(monthButton.text.toString(), yearText.text.toString())
            dialog.dismiss()
        }
    }

    interface ButtonClickListener {
        fun onClicked(content: String, value: String)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickListener = listener
    }

}