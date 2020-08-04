package com.philite.tictactoeGameGeneration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.philite.tictactoeGameGeneration.view.TicTacToeFragment

class TicTacToeActivity : AppCompatActivity() {

    companion object {
        private const val DIMENSION_BUNDLE = "dimension-bundle"

        fun newIntent(context: Context, dimension: Int): Intent {
            return Intent(context, TicTacToeActivity::class.java).apply {
                putExtra(DIMENSION_BUNDLE, dimension)
            }
        }
    }

    private var dimen: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        dimen = intent.extras?.getInt(DIMENSION_BUNDLE)
        dimen?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, TicTacToeFragment.newInstance(it))
                .commit()
        }
    }
}
