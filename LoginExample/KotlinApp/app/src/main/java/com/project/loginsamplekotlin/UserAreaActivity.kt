package com.project.loginsamplekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UserAreaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_area)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

