package com.JunHyeok0205.portfolio.moneystatus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.JunHyeok0205.portfolio.moneystatus.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() { // 시작화면
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 액티비티 실행 후, 2초뒤에 메인액티비티가 열림
        val handler = Handler()
        handler.postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            2000
        )
    }
}