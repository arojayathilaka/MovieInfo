package com.example.movieinfo.data.ui.popular_movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.movieinfo.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val unEditText: EditText = findViewById(R.id.unEditText)
        val pwEditText: EditText = findViewById(R.id.pwEditText)
        val loginBtn: Button = findViewById(R.id.loginBtn)

        var username = ""
        var password = ""

        unEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                username = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })

        pwEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                password = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })

        loginBtn.setOnClickListener {
            if (username == "user1" && password == "user123"){
                Toast.makeText(this, "Logging in..", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MovieView::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "Invalid user", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
