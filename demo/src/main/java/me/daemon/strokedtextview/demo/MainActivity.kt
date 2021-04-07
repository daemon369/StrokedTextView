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
        v.text = "abcdef我人有的和"
        v.textSize = 100f
        v.strokeTextColor = Color.CYAN
    }
}