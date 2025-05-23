package com.img.rockpaperscissor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InputPlayers : AppCompatActivity() {

    private var selectedAvatar: String = "Panda"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_players)

        val name = findViewById<EditText>(R.id.editTextText)
        val playbtn = findViewById<Button>(R.id.play2)

        val avatarButtons = listOf(
            Pair(findViewById<ImageButton>(R.id.panda), "Panda"),
            Pair(findViewById<ImageButton>(R.id.deer), "Deer"),
            Pair(findViewById<ImageButton>(R.id.fox), "Fox"),
            Pair(findViewById<ImageButton>(R.id.rabbit), "Rabbit"),
            Pair(findViewById<ImageButton>(R.id.elephant), "Elephant"),
            Pair(findViewById<ImageButton>(R.id.monkey), "Monkey")
        )

        // Set click listeners on avatar buttons
        avatarButtons.forEach { (button, avatarName) ->
            button.setOnClickListener {
                selectedAvatar = avatarName
                Toast.makeText(this, "$avatarName selected", Toast.LENGTH_SHORT).show()
            }
        }

        playbtn.setOnClickListener {
            val nextActivity = intent.getStringExtra("nextActivity")
            if(name.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val nextIntent = when (nextActivity) {
                    "PlayWithComputer" -> Intent(this, PlayWithComputer::class.java)
                    "PlayWithFriend" -> Intent(this, InputPlayers2::class.java)
                    else -> Intent(this, PlayWithComputer::class.java)
                }
                nextIntent.putExtra("playerName", name.text.toString())
                nextIntent.putExtra("avatar",selectedAvatar)
                startActivity(nextIntent)

            }
        }
    }
}