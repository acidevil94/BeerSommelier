package com.coluzzi.beersommelier.ui.search

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * Adapter for the loading control at the end of
 * the list of beers.
 */
class BeerLoadStateAdapter (private val retry: () -> Unit) : LoadStateAdapter<BeerLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: BeerLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BeerLoadStateViewHolder {
        return BeerLoadStateViewHolder.create(parent, retry)
    }
}