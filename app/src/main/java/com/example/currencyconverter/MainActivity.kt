package com.example.currencyconverter

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.view.WindowInsetsAnimation
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.ValCurs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import util.CurrencyAdapter
import util.RetrofitClient
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private lateinit var currencyAdapter: CurrencyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        currencyAdapter = CurrencyAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = currencyAdapter

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_NETWORK_STATE
            ) != PackageManager.PERMISSION_GRANTED
        )

        updateData()

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    updateData()
                }
            }
        }, 0, 30000)
    }

    fun updateData() {
        var progressBar : ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        RetrofitClient.createApi().getValCurs().enqueue(object : Callback<ValCurs> {
            override fun onResponse(call: Call<ValCurs>, response: Response<ValCurs>) {
                var textView : TextView = findViewById(R.id.textView)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        textView.text = "Последнее обновление: ${data.Date} ${
                            DateUtils.formatDateTime(
                                this@MainActivity,
                                System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE
                            )
                        }"
                        currencyAdapter.updateData(data.Valute)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Ошибка получения данных!", Toast.LENGTH_SHORT)
                        .show()
                }

                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<ValCurs>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Ошибка получения данных!", Toast.LENGTH_SHORT)
                    .show()

                progressBar.visibility = View.GONE
            }
        })

    }

}