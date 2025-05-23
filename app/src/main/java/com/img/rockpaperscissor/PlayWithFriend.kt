package com.img.rockpaperscissor

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PlayWithFriend : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_play_with_friend)

        val player1 = findViewById<TextView>(R.id.Player1)
        val player2 = findViewById<TextView>(R.id.Player2)
        val pic1 = findViewById<ImageView>(R.id.imageView1)
        val pic2 = findViewById<ImageView>(R.id.imageView2)

        val player1name = intent.getStringExtra("player1Name")
        val player2name = intent.getStringExtra("player2Name")
        player1.text = player1name.toString() ?: "PLAYER 1"
        player2.text = player2name.toString() ?: "PLAYER 2"
        val avatar1 = intent.getStringExtra("avatar1Name")
        val avatar2 = intent.getStringExtra("avatar2Name")

        val result1 = findViewById<TextView>(R.id.Result1)
        val result2 = findViewById<TextView>(R.id.Result2)
        val point1 = findViewById<TextView>(R.id.Point1)
        val point2 = findViewById<TextView>(R.id.Point2)

        fun getAvatarResource(avatarName: String?): Int {

            return when (avatarName?.capitalize()) {
                "Deer" -> R.drawable.deer
                "Fox" -> R.drawable.fox
                "Rabbit" -> R.drawable.rabbit
                "Elephant" -> R.drawable.elephant
                "Panda" -> R.drawable.panda
                "Monkey" -> R.drawable.monkey
                else -> R.drawable.deer
            }
        }

        fun showAvatars() {
            pic1.setImageResource(getAvatarResource(avatar1))
            pic2.setImageResource(getAvatarResource(avatar2))
            result1.text = "Ready!"
            result2.text = "Ready!"
        }

        showAvatars()

        val stone1 = findViewById<ImageButton>(R.id.rockButton1)
        val paper1 = findViewById<ImageButton>(R.id.paperButton1)
        val scissor1 = findViewById<ImageButton>(R.id.scissorButton1)
        val stone2 = findViewById<ImageButton>(R.id.rockButton2)
        val paper2 = findViewById<ImageButton>(R.id.paperButton2)
        val scissor2 = findViewById<ImageButton>(R.id.scissorButton2)
        val shoot = findViewById<ImageButton>(R.id.shoot)

        var player1Score = 0
        var player2Score = 0
        var player1Choice: Int = -1
        var player2Choice: Int = -1

        fun disableAllButtons() {
            stone1.isEnabled = false
            paper1.isEnabled = false
            scissor1.isEnabled = false
            stone2.isEnabled = false
            paper2.isEnabled = false
            scissor2.isEnabled = false
            shoot.isEnabled = false
        }

        fun enableAllButtons() {
            stone1.isEnabled = true
            paper1.isEnabled = true
            scissor1.isEnabled = true
            stone2.isEnabled = true
            paper2.isEnabled = true
            scissor2.isEnabled = true
            shoot.isEnabled = true
        }

        fun getHandGestureResource(choice: Int): Int {
            return when (choice) {
                0 -> R.drawable.stone
                1 -> R.drawable.paper
                else -> R.drawable.scissor
            }
        }

        fun onPlayerChoice(player: Int, choice: Int) {
            when (player) {
                1 -> {
                    player1Choice = choice
                }
                2 -> {
                    player2Choice = choice
                }
            }
        }

        // Player 1
        stone1.setOnClickListener { onPlayerChoice(1, 0) }
        paper1.setOnClickListener { onPlayerChoice(1, 1) }
        scissor1.setOnClickListener { onPlayerChoice(1, 2) }

        // Player 2
        stone2.setOnClickListener { onPlayerChoice(2, 0) }
        paper2.setOnClickListener { onPlayerChoice(2, 1) }
        scissor2.setOnClickListener { onPlayerChoice(2, 2) }

        fun determineWinner(p1: Int, p2: Int): String {
            return if (p1 == p2) {
                "draw"
            } else if ((p1 == 0 && p2 == 2) || (p1 == 1 && p2 == 0) || (p1 == 2 && p2 == 1)) {
                "player1"
            } else {
                "player2"
            }
        }

        fun resetGame() {
            player1Choice = -1
            player2Choice = -1
            showAvatars()
            enableAllButtons()
        }

        fun updateScoresAndResults(winner: String) {
            when (winner) {
                "player1" -> {
                    player1Score++
                    result1.text = "Win!"
                    result2.text = "Lose!"
                }
                "player2" -> {
                    player2Score++
                    result1.text = "Lose!"
                    result2.text = "Win!"
                }
                else -> {
                    result1.text = "Draw!"
                    result2.text = "Draw!"
                }
            }
            point1.text = player1Score.toString()
            point2.text = player2Score.toString()
        }

        fun showWinnerDialog(winnerName: String) {

            val dialogView = layoutInflater.inflate(R.layout.winner_dialog, null)
            val winnerText = dialogView.findViewById<TextView>(R.id.message)
            val resetBtn = dialogView.findViewById<Button>(R.id.button)
            val homeBtn = dialogView.findViewById<Button>(R.id.button2)

            val dialog = android.app.AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            winnerText.text = "🎉 Yay $winnerName is the Winner! Congratulations! 🎊"

            resetBtn.setOnClickListener {
                dialog.dismiss()
                recreate()
            }

            homeBtn.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            dialog.show()
        }

        shoot.setOnClickListener {
            if (player1Choice == -1 || player2Choice == -1) {
                Toast.makeText(this, "Both players must make a choice!", Toast.LENGTH_SHORT).show()
            } else {

                pic1.setImageResource(getHandGestureResource(player1Choice))
                pic2.setImageResource(getHandGestureResource(player2Choice))
                disableAllButtons()
                val result = determineWinner(player1Choice, player2Choice)
                updateScoresAndResults(result)
                if (player1Score == 3) { // or any final score
                    showWinnerDialog(player1.text.toString() ?: "Player 1")
                }
                else if (player2Score == 3) {
                    showWinnerDialog(player2.text.toString() ?: "Player 2")
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    resetGame()
                }, 1000)
            }
        }

        fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            outState.putInt("player1Choice", player1Choice)
            outState.putInt("player2Choice", player2Choice)
            outState.putInt("player1Score", player1Score)
            outState.putInt("player2Score", player2Score)
            outState.putString("result1", result1.text.toString())
            outState.putString("result2", result2.text.toString())
        }

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Please use in vertical mode only", Toast.LENGTH_SHORT).show()
        }

        if (savedInstanceState != null) {
            player1Choice = savedInstanceState.getInt("player1Choice", -1)
            player2Choice = savedInstanceState.getInt("player2Choice", -1)
            player1Score = savedInstanceState.getInt("player1Score", 0)
            player2Score = savedInstanceState.getInt("player2Score", 0)

            result1.text = savedInstanceState.getString("result1", "Ready!")
            result2.text = savedInstanceState.getString("result2", "Ready!")

            // Restore gesture images if chosen
            if (player1Choice != -1) pic1.setImageResource(getHandGestureResource(player1Choice))
            if (player2Choice != -1) pic2.setImageResource(getHandGestureResource(player2Choice))

            // Update score display
            point1.text = "Score: $player1Score"
            point2.text = "Score: $player2Score"
        }
    }

}