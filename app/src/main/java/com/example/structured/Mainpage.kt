package com.example.structured

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.structured.databinding.ActivityMainpageBinding

class Mainpage : AppCompatActivity() {
    private lateinit var binding: ActivityMainpageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigatebtn.setOnClickListener {
            val intent = Intent(this@Mainpage, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
