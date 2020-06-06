package com.example.deeptigupta.tictactoe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView

enum class Player(val value: Int) {
    Player1(0),
    Player2(1),
    None(2)
}

enum class ViewVisibility(val value: Float) {
    Visible(1.0F),
    Invisible(0.0F)
}

enum class PlayerName {
    Red,
    Yellow
}

class MainActivity : AppCompatActivity() {

    var activePlayer = Player.Player1.value

    var winner = -1

    var boardArray = mutableListOf<Int>(2,2,2,2,2,2,2,2,2)

    val winningPositions = arrayListOf(arrayListOf(0,1,2), arrayListOf<Int>(3,4,5), arrayListOf<Int>(6,7,8), arrayListOf<Int>(0,3,6), arrayListOf<Int>(1,4,7), arrayListOf<Int>(2,5,8), arrayListOf<Int>(0,4,8), arrayListOf<Int>(2,4,6))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun playDice(view: View) {

        val dice = findViewById<ImageView>(view.id)

        val boardIndex = dice.tag.toString().toInt()

        if (boardArray[boardIndex] == Player.None.value && winner == -1) {

            activePlayer = if (activePlayer == Player.Player1.value) Player.Player2.value else Player.Player1.value

            val image = if (activePlayer == Player.Player1.value) R.drawable.red else R.drawable.yellow
            dice.setImageResource(image)

            boardArray[boardIndex] = activePlayer

            for(array in winningPositions) {
                if (boardArray[array[0]] == boardArray[array[1]] && boardArray[array[1]] == boardArray[array[2]] && boardArray[array[0]] != Player.None.value) {

                    winner = activePlayer
                    val winner = findViewById<TextView>(R.id.winnerTextView)
                    val playAgainButton = findViewById<TextView>(R.id.playAgainButton)

                    winner.alpha = ViewVisibility.Visible.value
                    playAgainButton.alpha = ViewVisibility.Visible.value

                    val winnerPlayer = if (activePlayer == Player.Player1.value) PlayerName.Red else PlayerName.Yellow
                    winner.setText("Player $winnerPlayer is Winner!")
                }
            }
        }

        if (!boardArray.contains(2)) {
            val playAgainButton = findViewById<TextView>(R.id.playAgainButton)
            playAgain(playAgainButton)
        }
    }

    fun playAgain(view: View) {
        activePlayer = Player.Player1.value
        winner = -1
        boardArray = mutableListOf<Int>(2,2,2,2,2,2,2,2,2)

        val winner = findViewById<TextView>(R.id.winnerTextView)
        val playAgainButton = findViewById<TextView>(R.id.playAgainButton)
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        winner.alpha = ViewVisibility.Invisible.value
        playAgainButton.alpha = ViewVisibility.Invisible.value

        for(index in 0..gridLayout.childCount) {
            val child = gridLayout.getChildAt(index)
            if (child is ImageView) {
                child.setImageDrawable(null)
            }
        }
    }
}
