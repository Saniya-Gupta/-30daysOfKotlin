package com.heer.games.myApplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.heer.games.R
import com.heer.games.myApplication.fragment.ComicDashboardFragment

class MainActivity : AppCompatActivity() {

    lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frameLayout = findViewById(R.id.frameLayout)
        openComicDashboard()
    }

    private fun openComicDashboard() {
        title = "Trending Comics"
        val fragment = ComicDashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        title = "Trending Comics"
        super.onBackPressed()
    }
}