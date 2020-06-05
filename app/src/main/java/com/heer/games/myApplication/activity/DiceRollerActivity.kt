package com.heer.games.myApplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.heer.games.R

class DiceRollerActivity : AppCompatActivity() {

    private lateinit var imgDice1: ImageView
    private lateinit var imgDice2: ImageView
    private lateinit var btnRoll1 : Button
    private lateinit var btnRoll2 : Button
    private lateinit var btnClr : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_roller)

        title = "My Dice Roller"

        imgDice1 = findViewById(R.id.imgDice1)
        imgDice2 = findViewById(R.id.imgDice2)
        btnRoll1 = findViewById(R.id.btnRoll1)
        btnRoll2 = findViewById(R.id.btnRoll2)
        btnClr = findViewById(R.id.btnClr)

        btnRoll2.isEnabled = false

        btnRoll1.setOnClickListener {
            imgDice1.setImageResource(rollDice())
            btnRoll1.isEnabled = false
            btnRoll2.isEnabled = true
        }

        btnRoll2.setOnClickListener {
            imgDice2.setImageResource(rollDice())
            btnRoll1.isEnabled = true
            btnRoll2.isEnabled = false
        }

        btnClr.setOnClickListener {
            imgDice1.setImageResource(R.drawable.empty_dice)
            imgDice2.setImageResource(R.drawable.empty_dice)
            btnRoll2.isEnabled = false
            btnRoll1.isEnabled = true
        }
    }

    private fun rollDice() : Int {
        return when((1..6).random()) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_games, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_diceRoller)?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_ticTacToe -> finish()
            else -> startActivity(
                Intent(
                    this@DiceRollerActivity,
                    MainActivity::class.java
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}