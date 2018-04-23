package com.steelkiwi.simpletimehistogram

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.steelkiwi.simplehistogram.TimeData
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val random = Random()

    fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var items: MutableList<TimeData> = mutableListOf()
        for (i in 0..23) {
            items.add(TimeData(i, rand(0, 100), "#AB${rand(0, 9)}${rand(0, 9)}${rand(0, 9)}${rand(0, 9)}"))
        }
        histoView.setItems(items)

    }

}
