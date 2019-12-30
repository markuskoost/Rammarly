package com.example.rammarly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class AddInfoActivity : AppCompatActivity() {
    private var btnContinue: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        btnContinue = findViewById<View>(R.id.btn_continue) as Button
        btnContinue!!
            .setOnClickListener {
                startActivity(
                    Intent(
                        this@AddInfoActivity,
                        AddMarkerActivity::class.java
                    )
                )
            }
    }
}
