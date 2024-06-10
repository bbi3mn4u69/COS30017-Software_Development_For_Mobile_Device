package com.example.core1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.util.Log
import kotlin.random.Random



class MainActivity : AppCompatActivity() {


    private var TotalNumber = 0
    private var MaxNumber = 20
    private var isRollEnable = true
    private var isSubtractEnable = false
    private var isAddEnable = false
    private var rolledNumber = RollTheDice(Random(1))
    private var currentNumberIndex = 0
    private var currentDiceValue = 0



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton = findViewById<Button>(R.id.RollButton)
        val addButton = findViewById<Button>(R.id.AddButton)
        val subtractButton = findViewById<Button>(R.id.SubtractButton)
        val resetButton = findViewById<Button>(R.id.ResetButton)


        // Initially, disable Add, Subtract button
        addButton.isEnabled = false
        subtractButton.isEnabled = false
        rollButton.isEnabled = true

        // Initialize the result dice view image
        val resultDiceViewImage = findViewById<ImageView>(R.id.diceView)
        resultDiceViewImage.setImageResource(R.drawable.dice1) // You can set the initial image as needed

        // Initialize the total number display
        val resultViewText = findViewById<TextView>(R.id.resultView)
        resultViewText.text = "0"

//      button roll onclick
        rollButton.setOnClickListener {
            Roll()
            Log.d("MainActivity", "Roll list: $rolledNumber")
            Update()
            UpdateButtonState(false, true, true)
            if (TotalNumber == 20) {
                rollButton.isEnabled = isRollEnable
            }
        }
//        add button onclick
        addButton.setOnClickListener {
            Add()
            Log.d("MainActivity", "Total Number: $TotalNumber")
            Update()
            UpdateButtonState(true, false, false)
            if (TotalNumber == 20) {
                isRollEnable = false
                rollButton.isEnabled = isRollEnable
            }
        }
//        subtract button on click
        subtractButton.setOnClickListener {
            Subtract()
            Log.d("MainActivity", "Total Number: $TotalNumber")
            Update()
            UpdateButtonState(true, false, false)
            if (TotalNumber == 20) {
                isRollEnable = false
                rollButton.isEnabled = isRollEnable
            }
        }
//          reset button on click
        resetButton.setOnClickListener {
            isRollEnable = true
            rollButton.isEnabled = isRollEnable
            TotalNumber = 0
            currentNumberIndex = 0
            Log.d("MainActivity", "Total Number: $TotalNumber")
            Update()
        }

        //  implement save instance state when device is rotating
        if (savedInstanceState == null) {
            RollTheDice(Random(1))
        } else {
            TotalNumber = savedInstanceState.getInt("Total_Value")
            isRollEnable = savedInstanceState.getBoolean("RollState")
            isSubtractEnable = savedInstanceState.getBoolean("SubtractState")
            isAddEnable = savedInstanceState.getBoolean("AddState")
            currentDiceValue = savedInstanceState.getInt("Dice_Value")
            currentNumberIndex = savedInstanceState.getInt("CurrentIndex")
            UpdateButtonState(isRollEnable, isAddEnable, isSubtractEnable)
            DiceRolled()
            Update()
            Log.d("MainActivity", "dice current values $currentDiceValue")

        }
    }
//      update the state of button
    private fun UpdateButtonState(roll: Boolean, add: Boolean, substract: Boolean) {
        isRollEnable = roll
        isAddEnable = add
        isSubtractEnable = substract
        val rollButton = findViewById<Button>(R.id.RollButton)
        val addButton = findViewById<Button>(R.id.AddButton)
        val subtractButton = findViewById<Button>(R.id.SubtractButton)
        rollButton.isEnabled = isRollEnable
        addButton.isEnabled = isAddEnable
        subtractButton.isEnabled = isSubtractEnable
    }
//    update the color of the text
    private fun Update() {
        val resultViewText = findViewById<TextView>(R.id.resultView)
        val rollButton = findViewById<Button>(R.id.RollButton)
        if (TotalNumber < 20) {
            resultViewText.text = TotalNumber.toString()
            resultViewText.setTextColor(resources.getColor(R.color.black))
        } else if (TotalNumber > 20) {
            resultViewText.text = MaxNumber.toString()
            resultViewText.setTextColor(resources.getColor(R.color.Red))
        }else if(TotalNumber == 20) {
            resultViewText.text = TotalNumber.toString()
            resultViewText.setTextColor(resources.getColor(R.color.Green))
            rollButton.isEnabled = false
        }
    }
//    rolling
    private fun Roll(){
        if (currentNumberIndex < rolledNumber.size) {
            currentDiceValue = rolledNumber[currentNumberIndex]
            Log.d("MainActivity", "current Number: $currentDiceValue")
            currentNumberIndex++
            DiceRolled()
            Update()
//            reset the index when the list is run out of number
            if (currentNumberIndex == 10 ){
                currentNumberIndex = 0
            }
        }
    }
//
    //    function for subtract button
    private fun Subtract() {
        if (TotalNumber >= 0) {

            TotalNumber = TotalNumber - currentDiceValue
        }
        if (TotalNumber <= 0){
            TotalNumber = 0
        }
    }
    //    function for add button
    private fun Add() {

        TotalNumber = currentDiceValue + TotalNumber
    }

    //   function to roll the dice
    private fun RollTheDice(random: Random): List<Int> = List(10) {
        random.nextInt(1,7)
    }

// print out the rolled number to image
    private fun DiceRolled() {
        // condition for image rendering
        val resultDiceViewImage = findViewById<ImageView>(R.id.diceView)
        val diceResource = listOf(2
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6)

        if (currentDiceValue in 1..6) {
            resultDiceViewImage.setImageResource(diceResource[currentDiceValue - 1])
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("Total_Value", TotalNumber)
        outState.putInt("Dice_Value", currentDiceValue)
        outState.putBoolean("RollState", isRollEnable)
        outState.putBoolean("AddState", isAddEnable)
        outState.putInt("CurrentIndex", currentNumberIndex)
        outState.putBoolean("SubtractState", isSubtractEnable)
    }
}