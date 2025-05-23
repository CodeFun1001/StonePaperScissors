package com.img.rockpaperscissor

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlayWithComputer : AppCompatActivity() {

    var playerScore = 0
    var computerScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_play_with_computer)

        val stone = findViewById<ImageButton>(R.id.rockButton)
        val paper = findViewById<ImageButton>(R.id.paperButton)
        val scissor = findViewById<ImageButton>(R.id.scissorButton)

        val pic1 = findViewById<ImageView>(R.id.imageView1)
        val pic2 = findViewById<ImageView>(R.id.imageView2)

        val result1 = findViewById<TextView>(R.id.Result1)
        val result2 = findViewById<TextView>(R.id.Result2)
        val point1 = findViewById<TextView>(R.id.Point1)
        val point2 = findViewById<TextView>(R.id.Point2)

        val playerName = intent.getStringExtra("playerName")
        val avatarName = intent.getStringExtra("avatar")
        val avatar = when (avatarName) {
            "Deer" -> R.drawable.deer
            "Fox" -> R.drawable.fox
            "Rabbit" -> R.drawable.rabbit
            "Elephant" -> R.drawable.elephant
            "Panda" -> R.drawable.panda
            else -> R.drawable.monkey
        }
        val computerAvatar = R.drawable.robo
        val player1 = findViewById<TextView>(R.id.Player1)
        player1.text = playerName.toString()

        pic1.setImageResource(avatar)
        pic2.setImageResource(computerAvatar)
        result1.text = "Ready!"
        result2.text = "Ready!"

        fun showAvatars() {
            pic1.setImageResource(avatar)
            pic2.setImageResource(computerAvatar)
        }

        fun showWinnerDialog(isPlayerWinner: Boolean, winnerName: String) {
            val dialogView = layoutInflater.inflate(R.layout.winner_dialog, null)
            val winnerText = dialogView.findViewById<TextView>(R.id.message)
            val resetBtn = dialogView.findViewById<Button>(R.id.button)
            val homeBtn = dialogView.findViewById<Button>(R.id.button2)

            val dialog = android.app.AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            if (isPlayerWinner) {
                winnerText.text = "🎉 Yay $winnerName is the Winner! Congratulations! 🎊"
            } else {
                winnerText.text = "💀 Oops! You lost to the computer.\nBetter luck next time!"
            }

            resetBtn.setOnClickListener {
                dialog.dismiss()
                recreate()
            }

            homeBtn.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
        }

        fun playRound(playerChoice: Int) {
            // Disable buttons to prevent multiple clicks
            stone.isEnabled = false
            paper.isEnabled = false
            scissor.isEnabled = false

            val computerChoice = listOf(
                R.drawable.stone,
                R.drawable.paper,
                R.drawable.scissor
            ).random()

            pic1.setImageResource(playerChoice)
            pic2.setImageResource(computerChoice)

            val winner = when {
                playerChoice == computerChoice -> "Draw"
                playerChoice == R.drawable.stone && computerChoice == R.drawable.scissor -> "Player"
                playerChoice == R.drawable.paper && computerChoice == R.drawable.stone -> "Player"
                playerChoice == R.drawable.scissor && computerChoice == R.drawable.paper -> "Player"
                else -> "Computer"
            }

            when (winner) {
                "Player" -> {
                    playerScore++
                    result1.text = "Win!"
                    result2.text = "Lose!"
                }
                "Computer" -> {
                    computerScore++
                    result1.text = "Lose!"
                    result2.text = "Win!"
                }
                "Draw" -> {
                    result1.text = "Draw!"
                    result2.text = "Draw!"
                }
            }

            point1.text = playerScore.toString()
            point2.text = computerScore.toString()

            // After 5 sec, show avatars again & enable buttons
            Handler(Looper.getMainLooper()).postDelayed({
                showAvatars()

                // Re-enable buttons
                stone.isEnabled = true
                paper.isEnabled = true
                scissor.isEnabled = true

                // Reset result text
                result1.text = "Ready!"
                result2.text = "Ready!"
            }, 1_500)

            if (playerScore == 3) { // or any final score
                showWinnerDialog(true, playerName ?: "Player")
            }
            else if (computerScore == 3) {
                showWinnerDialog(false, playerName ?: "Player")
            }

        }


        Handler(Looper.getMainLooper()).postDelayed({
            showAvatars()
            Handler(Looper.getMainLooper()).postDelayed({
                stone.setOnClickListener { playRound(R.drawable.stone) }
                paper.setOnClickListener { playRound(R.drawable.paper) }
                scissor.setOnClickListener { playRound(R.drawable.scissor) }
            }, 1_000)
        }, 2_000)

    }
}
