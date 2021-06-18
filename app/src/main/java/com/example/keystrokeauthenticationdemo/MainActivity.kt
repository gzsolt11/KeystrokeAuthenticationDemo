package com.example.keystrokeauthenticationdemo

import android.R.attr.delay
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.keystrokeauthentication.KeystrokeAuthenticationProvider
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var authenticateButton:Button
    private lateinit var pinEditText:EditText
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var ks: KeystrokeAuthenticationProvider
    private lateinit var goodResultTextView: TextView
    private lateinit var badPINResultTextView: TextView
    private lateinit var badRhythmResultTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authenticateButton = findViewById<Button>(R.id.authenticate)
        pinEditText = findViewById<EditText>(R.id.editTextTextPersonName)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        goodResultTextView = findViewById(R.id.goodResultTextView)
        badPINResultTextView = findViewById(R.id.badPINResultTextView)
        badRhythmResultTextView = findViewById(R.id.badRhythmResultTextView)

        //setSupportActionBar(toolbar)

        navigationView.bringToFront()
        var toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        ks = KeystrokeAuthenticationProvider(this.applicationContext, pinEditText)

    }

    override fun onStart() {
        super.onStart()

        authenticateButton.setOnClickListener{
            goodResultTextView.visibility = View.INVISIBLE
            badPINResultTextView.visibility = View.INVISIBLE
            badRhythmResultTextView.visibility = View.INVISIBLE
            val authenticationResult = ks.authenticate()
            if(authenticationResult == 1){
               // Toast.makeText(this.applicationContext, "Accepted", Toast.LENGTH_SHORT).show()
                goodResultTextView.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    goodResultTextView.visibility = View.INVISIBLE
                },5000)
            }else if(authenticationResult == -1){
                badPINResultTextView.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    badPINResultTextView.visibility = View.INVISIBLE
                },5000)
                //Toast.makeText(this.applicationContext, "Rejected", Toast.LENGTH_SHORT).show()
            }else{
                badRhythmResultTextView.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    badRhythmResultTextView.visibility = View.INVISIBLE
                },5000)
            }
            Log.v("AUTHENTICATIONPROVIDER", authenticationResult.toString())
        }
    }

    override fun onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_train -> ks.train()
            R.id.nav_color -> {
                ks.setKeyboardColor()
            }
            R.id.nav_deviation -> ks.setDeviationForThreshold()
            R.id.nav_help -> ks.Help()
        }

        return true
    }
}