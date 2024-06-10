package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.content.Intent
import android.widget.Toast
import kotlin.math.log

class MainActivity2 : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



        var sliderCurrentNumber = 0
        val game = intent.getParcelableExtra<Game>("game")

        game?.let{
            val price = it.price
            var isborrow = it.isBorrow
            var rentDays = it.rentDay
            var rentPrice = it.rentPrice

            val save = findViewById<Button>(R.id.saveBtn)
            val priceValue = findViewById<TextView>(R.id.priceview)
            val seekbar = findViewById<SeekBar>(R.id.seekBar3);
            val days = findViewById<TextView>(R.id.days)

            days.text = "days"
            priceValue.text ="$"+ price.toString()

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress > 1) {

                    days.text = progress.toString() + " days"
                } else{

                    days.text = progress.toString() + " day"
                }
                sliderCurrentNumber = seekbar.progress
                rentDays = sliderCurrentNumber
                rentPrice = (price * sliderCurrentNumber)
                Log.d("mans", "Rentdays $rentDays")
                priceValue.text = "$" + rentPrice.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (sliderCurrentNumber == 0) {
                    days.text = seekbar.progress.toString() + " day"
                    rentPrice = price
                    priceValue.text = "$" + rentPrice.toString()
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                sliderCurrentNumber = seekbar.progress
                rentDays = sliderCurrentNumber
                rentPrice = (price * sliderCurrentNumber)
                priceValue.text = "$" + rentPrice.toString()
                Log.d("MainActivity", "currentprice: $price ")
            }
        })
            save.setOnClickListener {
                if (sliderCurrentNumber == 0) {
                    Toast.makeText(this, "please select the days", Toast.LENGTH_SHORT).show()
                }else {
                    isborrow = true
                    val intent = Intent()
                    intent.putExtra("day", rentDays)
                    intent.putExtra("price", rentPrice)
                    intent.putExtra("isborrow?", isborrow)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }
    // Override onBackPressed to show a toast message
    override fun onBackPressed() {
        finish()
        Toast.makeText(this, "this game not suite you ?", Toast.LENGTH_SHORT).show()
    }
}



