package com.img.rockpaperscissor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val playWithComputer = findViewById<Button>(R.id.play1)
        val playWithFriend = findViewById<Button>(R.id.play2)
        val instructions = findViewById<TextView>(R.id.textView)

        playWithComputer.setOnClickListener {
            val intent = Intent(this, InputPlayers::class.java)
            intent.putExtra("nextActivity", "PlayWithComputer")
            startActivity(intent)
        }

        playWithFriend.setOnClickListener {
            val intent = Intent(this, InputPlayers::class.java)
            intent.putExtra("nextActivity", "PlayWithFriend")
            startActivity(intent)
        }

        instructions.setOnClickListener{
            val intent = Intent(this, Instructions::class.java)
            startActivity(intent)
        }
    }
}