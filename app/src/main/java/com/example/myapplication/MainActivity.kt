package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.content. Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

// initialize variable
    private var currentGame = 0
    private val COD = Game("Call of duty", "COD game", 35, false, null, null, "Shooting", "Action", "Attractive", 2.5f)
    private val CSGO = Game("Counter-Strike Global-Offensive", "CSGO game", 65, false, null, null, "Shooting", "Survival", "Attractive", 3.5f)
    private val PUBG = Game("Player Unknow Battle Ground", "PUBG game", 68, false, null, null, "Shooting", "Survival", "Guns", 5f)
    private val RR = Game ("Real Racing", "RR game", 80, false, null, null, "Racing", "Car", "Attractive", 4.5f)
//    list of games
    private val Informationlist = listOf(
        COD, CSGO, PUBG, RR
    )
    private var rentDays: Int? = Informationlist[currentGame].rentDay
    private var rentPrice: Int? = Informationlist[currentGame].rentPrice
    private var isBorrow: Boolean? = Informationlist[currentGame].isBorrow

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Update()

//    set up button
        val nextBtn =  findViewById<Button>(R.id.nextBtn)
        val borrowBtn = findViewById<Button>(R.id.borrowBtn)

// show the attribute    next button
        nextBtn.setOnClickListener{
            nextItem()
            Update()
            CheckBorrow()
            Log.d("MainActivity", "currentgame: $currentGame ")
            Log.d("MainActivity", "days: $rentDays")
            Log.d("MainActivity", "price: $rentPrice")
        }
// borrow button
        borrowBtn.setOnClickListener {
            itemIntent()
        }
        // Initialize the result launcher
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
//                get result
                rentDays = data?.getIntExtra("day", 0)
                rentPrice = data?.getIntExtra("price", 0)
                isBorrow = Informationlist[currentGame].isBorrow?.let {
                    data?.getBooleanExtra("isborrow?", it)
                }
//              passing result back
                Informationlist[currentGame].rentDay = rentDays
                Informationlist[currentGame].rentPrice = rentPrice
                Informationlist[currentGame].isBorrow = isBorrow
//                show toast
                if (isBorrow == true) {
                    Toast.makeText(this, "nice choice", Toast.LENGTH_SHORT).show()
                }
                CheckBorrow()
                Log.d("MainActivity", "is borrow $isBorrow")
                Log.d("MainActivity", "Current game $currentGame")

            }
        }
    }
//    check the item being borrow or not
    @SuppressLint("SetTextI18n")
    private fun CheckBorrow() {
        val borrowBtn = findViewById<Button>(R.id.borrowBtn)
        if ( Informationlist[currentGame].isBorrow  == true) {
                borrowBtn.isEnabled = false
                borrowBtn.text = "Rent for ${Informationlist[currentGame].rentDay.toString()} Days Price $${Informationlist[currentGame].rentPrice.toString()}"
        }else {
            borrowBtn.isEnabled = true
            borrowBtn.text = "Borrow"
        }
    }
//    put intent
    @SuppressLint("SuspiciousIndentation")
    private fun itemIntent() {
        val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("game", Informationlist[currentGame])
            resultLauncher.launch(intent)
    }
// move to next item
    private fun nextItem() {
            Informationlist[currentGame]
       if (currentGame in 0 .. 2){
           currentGame ++
       }
       else {
           currentGame = 0
       }
    }
// update though next item
    private fun Update() {
    //      attribute
        val attr1 = findViewById<TextView>(R.id.attr1)
        val attr2 = findViewById<TextView>(R.id.attr2)
        val attr3 = findViewById<TextView>(R.id.attr3)
//    set attribute tex
            attr1.text = Informationlist[currentGame].attr1
            attr2.text = Informationlist[currentGame].attr2
            attr3.text = Informationlist[currentGame].attr3
        val image = findViewById<ImageView>(R.id.imageView)
        val gameId = findViewById<TextView>(R.id.itemName)
        val gameDes = findViewById<TextView>(R.id.itemDes)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
//    set rating bar not rateable
         ratingBar.setIsIndicator(true)
//    draw image
        val itemPrice = findViewById<TextView>(R.id.priceDis)
        val gameImage = listOf(
            R.drawable.cod,
            R.drawable.csgo,
            R.drawable.pubg,
            R.drawable.rr,
        )
    if( currentGame in 0 .. 3){
        image.setImageResource(gameImage[currentGame])
    }
        when (currentGame) {
            currentGame -> {
                gameId.text = Informationlist[currentGame].name
                gameDes.text = Informationlist[currentGame].des
                itemPrice.text = "$ " + Informationlist[currentGame].price.toString()
                ratingBar.rating = Informationlist[currentGame].rate
            }
        }
    }
}


