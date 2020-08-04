package com.philite.tictactoeGameGeneration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.philite.tictactoeGameGeneration.view.InitGameFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_layout,
            InitGameFragment()
        )
            .commit()
    }
}
