package com.ap88.yg.fruittole.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ap88.yg.fruittole.R
import com.ap88.yg.fruittole.domain.Person
import com.ap88.yg.fruittole.ui.adapters.ForecastListAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastList = findViewById<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)

        val items: MutableList<String> = mutableListOf()
        for (i in 1..100) {
            items.add(i.toString() + "-----------------------duanlei-----------------------")
        }

        forecastList.adapter = ForecastListAdapter(items)
    }

    fun test(view: View) {
        Person("hello").niceToast(this, "message")
    }

}
