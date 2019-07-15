package com.obasoglu.nbateamviewer.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.obasoglu.nbateamviewer.R

/**
 * MainActivity is a launcher activity and it's only responsibility is the host TeamsList and TeamPage fragments
 * using NavHostFragment
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
