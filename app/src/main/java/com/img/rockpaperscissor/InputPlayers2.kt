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

class InputPlayers2 : AppCompatActivity() {

    private var selectedAvatar: String = "Panda"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_players2)

        val name = findViewById<EditText>(R.id.editTextText)

        val playbtn = findViewById<Button>(R.id.play2)

        val player1name = intent.getStringExtra("playerName")
        val avatar1name = intent.getStringExtra("avatar")

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
            val intent = Intent(this, InputPlayers::class.java)
            if(name.toString().isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent(this, PlayWithFriend::class.java)
                intent.putExtra("player1Name", player1name)
                intent.putExtra("avatar1Name",avatar1name)
                intent.putExtra("player2Name", name.text.toString())
                intent.putExtra("avatar2Name",selectedAvatar)
                startActivity(intent)

            }
        }
    }
}