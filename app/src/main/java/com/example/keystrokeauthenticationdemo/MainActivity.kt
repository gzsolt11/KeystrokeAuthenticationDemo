package com.example.keystrokeauthenticationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.keystrokeauthentication.KeystrokeAuthenticationProvider

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var authenticateButton: Button
    private lateinit var pinEditText: EditText
    private lateinit var trainButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopButton = findViewById<Button>(R.id.stopButton)
        authenticateButton = findViewById<Button>(R.id.authenticate)
        pinEditText = findViewById<EditText>(R.id.editTextTextPersonName)
        trainButton = findViewById<Button>(R.id.trainButton)


        var ks = KeystrokeAuthenticationProvider(this, pinEditText)


        stopButton.setOnClickListener{
            ks.stopBackgroundProcess()
        }

        authenticateButton.setOnClickListener{
            val authenticationResult = ks.authenticate()
            if(authenticationResult){
                Toast.makeText(this.applicationContext, "Accepted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this.applicationContext, "Rejected", Toast.LENGTH_SHORT).show()
            }
            Log.v("AUTHENTICATIONPROVIDER",authenticationResult.toString())
        }

        trainButton.setOnClickListener{
            ks.train()
        }
    }


}