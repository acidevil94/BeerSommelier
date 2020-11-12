package com.texa.beersommelier.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.texa.beersommelier.R
import com.texa.beersommelier.databinding.BeerLoadStateFooterViewItemBinding


/**
 * Class that handles the visibility and the text on the
 * loading control that appears over the list of beer.
 */
class BeerLoadStateViewHolder(
    private val binding : BeerLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        /**
         * Buils an instance of [BeerLoadStateViewHolder] based on
         * the view and the retry function in input.
         */
        fun create(parent: ViewGroup, retry: () -> Unit): BeerLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.beer_load_state_footer_view_item, parent, false)
            val binding = BeerLoadStateFooterViewItemBinding.bind(view)
            return BeerLoadStateViewHolder(binding, retry)
        }
    }
}