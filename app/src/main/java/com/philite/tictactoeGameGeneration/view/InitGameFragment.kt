package com.philite.tictactoeGameGeneration.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.philite.tictactoeGameGeneration.R
import com.philite.tictactoeGameGeneration.TicTacToeActivity
import com.philite.tictactoeGameGeneration.databinding.FragmentInitGameBinding

class InitGameFragment : Fragment() {
    private var binding: FragmentInitGameBinding? = null
    private var dimen: Int? = null
    private lateinit var startButton: Button
    private lateinit var dimensionTextInput: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitGameBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.startButton?.let {
            this.startButton = it
        }
        binding?.inputDimension?.let {
            dimensionTextInput = it
        }

        startButton.setOnClickListener {
            dimen = getDimensionFromEditText()
            dimen?.let {
                if (it > 1) {
                    startGame(it)
                } else {
                    dimensionTextInput.error = getString(R.string.error_input)
                }
            }
        }

        dimensionTextInput.editText?.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    dimen = getDimensionFromEditText()
                    dimen?.let {
                        startGame(it)
                    }
                    true
                }
                else -> false
            }
        }

    }

    private fun getDimensionFromEditText(): Int? {
        val textFromEditText = dimensionTextInput.editText?.text.toString()
        if (textFromEditText.isNotBlank()) {
            dimen = textFromEditText.toInt()
        }
        return dimen
    }

    private fun startGame(dimen: Int) {
        context?.let {
            startActivity(TicTacToeActivity.newIntent(it, dimen))
        }
    }
}
