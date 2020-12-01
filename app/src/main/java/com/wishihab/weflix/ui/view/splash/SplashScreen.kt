package com.wishihab.weflix.ui.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.wishihab.weflix.R
import com.wishihab.weflix.ui.view.home.HomeActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)

        //Handling Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}