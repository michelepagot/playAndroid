/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    /**
     * DO NOT ALTER ANY VARIABLE OR VALUE NAMES OR THEIR INITIAL VALUES.
     *
     * Anything labeled var instead of val is expected to be changed in the functions but DO NOT
     * alter their initial values declared here, this could cause the app to not function properly.
     */
    private val APP_TAG: String = "LEMONADE"
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT represents the "pick lemon" state
    private val SELECT = "select"
    // SQUEEZE represents the "squeeze lemon" state
    private val SQUEEZE = "squeeze"
    // DRINK represents the "drink lemonade" state
    private val DRINK = "drink"
    // RESTART represents the state where the lemonade has be drunk and the glass is empty
    private val RESTART = "restart"
    // Default the state to select
    private var lemonadeState = "select"
    // Default lemonSize to -1
    private var lemonSize = -1
    // Default the squeezeCount to -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === DO NOT ALTER THE CODE IN THE FOLLOWING IF STATEMENT ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        // === END IF STATEMENT ===

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        lemonImage!!.setOnClickListener {
            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {
            showSnackbar()
        }
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * This method saves the state of the app if it is put in the background.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    /**
     * Clicking will elicit a different response depending on the state.
     * This method determines the state and proceeds with the correct action.
     */
    private fun clickLemonImage() {
        Log.v(APP_TAG, "Click")
        // use a conditional statement like 'if' or 'when' to track the lemonadeState
        //  when the the image is clicked we may need to change state to the next step in the
        //  lemonade making progression (or at least make some changes to the current state in the
        //  case of squeezing the lemon). That should be done in this conditional statement

        when(this.lemonadeState){
            SELECT -> {
                // When the image is clicked in the SELECT state, the state should become SQUEEZE
                //  - The lemonSize variable needs to be set using the 'pick()' method in the LemonTree class
                //  - The squeezeCount should be 0 since we haven't squeezed any lemons just yet.
                Log.v(APP_TAG, "SELECT")
                this.lemonadeState = SQUEEZE
                this.lemonSize = this.lemonTree.pick()
                this.squeezeCount = 0
            }
            SQUEEZE -> {
                // When the image is clicked in the SQUEEZE state the squeezeCount needs to be
                //  INCREASED by 1 and lemonSize needs to be DECREASED by 1.
                //  - If the lemonSize has reached 0, it has been juiced and the state should become DRINK
                //  - Additionally, lemonSize is no longer relevant and should be set to -1
                Log.v(APP_TAG, "SQUEEZE")
                this.lemonSize--
                this.squeezeCount++
                if(this.lemonSize <= 0)
                {
                    this.lemonadeState = DRINK
                }
            }
            DRINK -> {
                // When the image is clicked in the DRINK state the state should become RESTART
                Log.v(APP_TAG, "DRINK")
                this.lemonSize = -1
                this.lemonadeState = RESTART
            }
            RESTART -> {
                // When the image is clicked in the RESTART state the state should become SELECT
                Log.v(APP_TAG, "RESTART")
                this.lemonadeState = SELECT
            }
            else -> {
                Log.w(APP_TAG, "Unknown lemonadeState:${this.lemonadeState}")
            }
        }
        // lastly, before the function terminates we need to set the view elements so that the
        //  UI can reflect the correct state
        setViewElements()
    }

    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements() {
        Log.v(APP_TAG, "Long click")
        val textAction: TextView = findViewById(R.id.text_action)
        val imageView: ImageView = findViewById(R.id.image_lemon_state)

        when(this.lemonadeState){
            SELECT -> {
                Log.v(APP_TAG, "SELECT")
                textAction.setText(R.string.lemon_select)
                imageView.setImageResource(R.drawable.lemon_tree)
            }
            SQUEEZE -> {
                Log.v(APP_TAG, "SQUEEZE")
                textAction.setText(R.string.lemon_squeeze)
                imageView.setImageResource(R.drawable.lemon_squeeze)
            }
            DRINK -> {
                Log.v(APP_TAG, "DRINK")
                textAction.setText(R.string.lemon_drink)
                imageView.setImageResource(R.drawable.lemon_drink)
            }
            RESTART -> {
                Log.v(APP_TAG, "RESTART")
                textAction.setText(R.string.lemon_empty_glass)
                imageView.setImageResource(R.drawable.lemon_restart)
            }
            else -> {
                Log.w(APP_TAG, "Unknown lemonadeState:${this.lemonadeState}")
            }
        }
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * Long clicking the lemon image will show how many times the lemon has been squeezed.
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

/**
 * A Lemon tree class with a method to "pick" a lemon. The "size" of the lemon is randomized
 * and determines how many times a lemon needs to be squeezed before you get lemonade.
 */
class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
