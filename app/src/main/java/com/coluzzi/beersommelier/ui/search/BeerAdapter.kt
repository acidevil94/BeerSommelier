package com.texa.beersommelier.ui.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.texa.beersommelier.model.beer.Beer

/**
 * Class that contains the logic to help the
 * [RecyclerView] showing an item of the displayed list of [Beer].
 */
class BeerAdapter : PagingDataAdapter<Beer, RecyclerView.ViewHolder>(BEER_COMPARATOR)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BeerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val beerItem = getItem(position)
        if (beerItem != null) {
            (holder as BeerViewHolder).bind(beerItem)
        }
    }


    companion object {
        private val BEER_COMPARATOR = object : DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean =
                oldItem == newItem
        }
    }
}