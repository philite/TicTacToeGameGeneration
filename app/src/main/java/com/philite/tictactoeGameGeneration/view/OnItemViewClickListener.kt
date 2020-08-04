package com.philite.tictactoeGameGeneration.view

import android.view.View
import com.philite.tictactoeGameGeneration.model.ItemData

abstract class OnItemViewClickListener : View.OnClickListener {
    override fun onClick(v: View?) {
        onClick(v, null)
    }

    abstract fun onClick(view: View?, item: ItemData?)
}

