package com.philite.tictactoeGameGeneration.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.philite.tictactoeGameGeneration.R
import com.philite.tictactoeGameGeneration.TicTacToeActivity
import com.philite.tictactoeGameGeneration.databinding.FragmentTicTacToeBinding
import com.philite.tictactoeGameGeneration.model.ItemData
import com.philite.tictactoeGameGeneration.model.Player
import com.philite.tictactoeGameGeneration.model.TicTacToeState
import com.philite.tictactoeGameGeneration.viewmodel.BoardViewModel
import com.philite.tictactoeGameGeneration.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.view_item_tic_tac_toe.view.*

class TicTacToeFragment : Fragment() {
    companion object {
        private const val DIMENSION_BUNDLE = "dimension-bundle"
        private const val viewX: Int = 1
        private const val viewO: Int = 2

        fun newInstance(dimension: Int = 0) = TicTacToeFragment()
            .apply {
                arguments = Bundle().apply {
                    putInt(DIMENSION_BUNDLE, dimension)
                }
            }
    }

    private var binding: FragmentTicTacToeBinding? = null
    private var factory: ViewModelFactory? = null
    private lateinit var viewModel: BoardViewModel

    private var recyclerAdapter: GameAdapter? = null
    private var itemWidth: Int = 0
    private var itemHeight: Int = 0
    private var boardDimension: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicTacToeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getInt(DIMENSION_BUNDLE)?.let {
            boardDimension = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createViewModel()

        itemWidth = (resources.getDimension(R.dimen.width_board_dimen) / boardDimension).toInt()
        itemHeight =
            (resources.getDimension(R.dimen.height_board_dimen) / boardDimension).toInt()

        recyclerAdapter =
            GameAdapter(itemWidth, itemHeight, viewModel.boardItemList, OnTicTacToeClick())

        binding?.ticTacToeRecyclerView?.run {
            layoutManager = GridLayoutManager(this@TicTacToeFragment.context, boardDimension)
            adapter = recyclerAdapter
        }
    }

    private fun createViewModel() {
        factory = ViewModelFactory(boardDimension)
        factory?.let {
            viewModel = ViewModelProvider(this, it).get(BoardViewModel::class.java)
        }
        binding?.boardViewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        viewModel.gameResultLiveData.observe(viewLifecycleOwner, Observer {
            showMaterialDialog(it)
        })
    }

    private fun showMaterialDialog(message: String) {
        MaterialAlertDialogBuilder(context).setTitle(message)
            .setNeutralButton(
                resources.getString(R.string.game_finished_dialog_button_back)) { _, _ ->
                activity?.finish()
            }
            .setPositiveButton(
                resources.getString(R.string.game_finished_dialog_button_play_again)) { _, _ ->
                activity?.finish()
                context?.let {
                    startActivity(TicTacToeActivity.newIntent(it, boardDimension))
                }
            }
            .setCancelable(false)
            .show()
    }

    private inner class OnTicTacToeClick : OnItemViewClickListener() {
        override fun onClick(view: View?, item: ItemData?) {
            item?.let {
                if (!it.clicked) {
                    view?.itemViewFlipper?.run {
                        if (viewModel.getCurrentPlayer() == Player.ONE) {
                            setViewAndItemState(this, it, TicTacToeState.X)
                        } else {
                            setViewAndItemState(this, it, TicTacToeState.O)
                        }
                        it.clicked = true
                        viewModel.checkResult()
                        viewModel.nextTurn()
                    }
                }
            }
        }

        private fun setViewAndItemState(
            flipper: ViewFlipper, item: ItemData, state: TicTacToeState) {
            when (state) {
                TicTacToeState.X -> flipper.getChildAt(viewX).visibility = View.VISIBLE
                TicTacToeState.O -> flipper.getChildAt(viewO).visibility = View.VISIBLE
                else -> throw IllegalStateException()
            }
            item.state = state
        }
    }
}
