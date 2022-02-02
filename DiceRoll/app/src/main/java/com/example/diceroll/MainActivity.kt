package com.example.diceroll

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener { rollDice() }
    }

    /**
     * Roll the dice and update the screen with the result.
     */
    private fun rollDice() {
        // Create new Dice object with 6 sides ...
        val dice = Dice(6)
        val rolled_dice = dice.roll()
        // ... and roll it
        val resultTextView: TextView = findViewById(R.id.textView)
        resultTextView.text = rolled_dice.toString()

        val diceImage: ImageView = findViewById(R.id.imageView)
        val drawDice = when(rolled_dice) {
            1 -> R.drawable.my_dice_1
            2 -> R.drawable.my_dice_2
            3 -> R.drawable.my_dice_3
            4 -> R.drawable.my_dice_4
            5 -> R.drawable.my_dice_5
            6 -> R.drawable.my_dice_6
            else -> {0}
        }
        diceImage.setImageResource(drawDice)
    }
}

/**
 * This class implement a Dice behavior, initialized with number of face
 */
class Dice(private val numSides: Int) {

    fun roll(): Int {
        return (1..numSides).random()
    }
}
