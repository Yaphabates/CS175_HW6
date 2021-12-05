package com.example.cs175_hw6

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButton()
    }

    private fun initButton() {
        open(R.id.permission, PermissionActivity::class.java)
        open(R.id.image, ImageActivity::class.java)
        open(R.id.videoView, VideoActivity::class.java)
    }

    private fun open(buttonId: Int, clz: Class<*>) {
        findViewById<View>(buttonId).setOnClickListener { startActivity(Intent(this@MainActivity, clz)) }
    }
}