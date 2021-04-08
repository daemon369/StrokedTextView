package me.daemon.strokedtextview.demo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.daemon.strokedtextview.StrokedTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val v = findViewById<StrokedTextView>(R.id.stroked_text_view)
        v.text = "ab克己复礼可升级老骥伏枥文件垃圾分类看cdef我人有的和"
        v.textSize = 100f
        v.solidTextColor = Color.BLACK
        v.strokeTextColor = Color.GREEN
        v.strokeWidth = 2f
    }
}