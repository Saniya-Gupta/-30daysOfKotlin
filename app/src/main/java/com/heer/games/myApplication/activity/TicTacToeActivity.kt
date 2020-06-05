package com.heer.games.myApplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.heer.games.R

class TicTacToeActivity : AppCompatActivity() {

    lateinit var txtStatus: TextView // Displays current status of the game
    lateinit var btnReplay: Button // reset the game

    private var currentPlayer = 1   // 1 -> X   0 -> 0

    var gameState = Array(9) { 2 }    // 2 -> null/empty state

    var gameActive = true // Becomes inactive in case of win or draw

    private var a = 0 // For tie

    private val winningPositions = arrayOf(
        arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8),
        arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8),
        arrayOf(0, 4, 8), arrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)

        title = "Tic-Tac-Toe"
        txtStatus = findViewById(R.id.txtStatus)
        btnReplay = findViewById(R.id.btnReplay)
        btnReplay.isEnabled = false
    }

    fun tap(view: View) {
        val imgTapped: ImageView = view as ImageView
        val tappedImgIndex = Integer.parseInt(imgTapped.tag.toString())

        // Change the gameStates
        if (gameState[tappedImgIndex] == 2 && gameActive) {
            gameState[tappedImgIndex] = currentPlayer
            imgTapped.translationY = -1000f
            a++
            if (currentPlayer == 1) {
                imgTapped.setImageResource(R.drawable.x)
                txtStatus.text = getString(R.string.statusY)
                txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorY))
                currentPlayer = 0
            } else {
                imgTapped.setImageResource(R.drawable.zero)
                currentPlayer = 1
                txtStatus.text = getString(R.string.statusX)
                txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorX))
            }
            imgTapped.animate().translationYBy(1000f).setDuration(300)
        }

        // Check for draw
        if(a==9) {
            txtStatus.text = getString(R.string.tie)
            txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            gameActive = false
        }

        // Check if any player has won
        for (i in winningPositions) {
            if (gameState[i[0]] == gameState[i[1]] && gameState[i[1]] == gameState[i[2]]
                && gameState[i[0]] != 2
            ) {
                if(gameState[i[0]] == 1) {
                    txtStatus.text = getString(R.string.x_has_won)
                    txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorX))
                }
                else {
                    txtStatus.text = getString(R.string.y_has_won)
                    txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorY))
                }
                gameActive = false
            }
        }

        if(!gameActive) {
            btnReplay.isEnabled = true
            btnReplay.setOnClickListener {
                reset(it)
            }
        }
    }

    private fun reset(view : View) {
        a = 0
        txtStatus.text = getString(R.string.statusX)
        txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorX))
        gameActive = true
        currentPlayer = 1
        for(i in gameState.indices)
            gameState[i] = 2
        findViewById<ImageView>(R.id.img0).setImageResource(0)
        findViewById<ImageView>(R.id.img1).setImageResource(0)
        findViewById<ImageView>(R.id.img2).setImageResource(0)
        findViewById<ImageView>(R.id.img3).setImageResource(0)
        findViewById<ImageView>(R.id.img4).setImageResource(0)
        findViewById<ImageView>(R.id.img5).setImageResource(0)
        findViewById<ImageView>(R.id.img6).setImageResource(0)
        findViewById<ImageView>(R.id.img7).setImageResource(0)
        findViewById<ImageView>(R.id.img8).setImageResource(0)
        view.isEnabled = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_games, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_ticTacToe)?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_diceRoller -> startActivity(
                Intent(
                    this@TicTacToeActivity,
                    DiceRollerActivity::class.java
                )
            )
            else -> startActivity(
                Intent(
                    this@TicTacToeActivity,
                    MainActivity::class.java
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }
}