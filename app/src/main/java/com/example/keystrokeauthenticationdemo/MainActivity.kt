package com.example.keystrokeauthenticationdemo

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authenticateButton = findViewById<Button>(R.id.authenticate)
        pinEditText = findViewById<EditText>(R.id.editTextTextPersonName)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)

        //setSupportActionBar(toolbar)

        navigationView.bringToFront()
        var toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        ks = KeystrokeAuthenticationProvider(this.applicationContext, pinEditText)

    }

    override fun onStart() {
        super.onStart()

        authenticateButton.setOnClickListener{
            val authenticationResult = ks.authenticate()
            if(authenticationResult){
                Toast.makeText(this.applicationContext, "Accepted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this.applicationContext, "Rejected", Toast.LENGTH_SHORT).show()
            }
            Log.v("AUTHENTICATIONPROVIDER",authenticationResult.toString())
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