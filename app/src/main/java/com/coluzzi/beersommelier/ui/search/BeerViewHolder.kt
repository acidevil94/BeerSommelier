package com.texa.beersommelier.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.texa.beersommelier.R
import com.texa.beersommelier.model.beer.Beer
import com.texa.beersommelier.ui.detail.BeerDetailActivity
import com.texa.beersommelier.util.Constants


/**
 * Class that has the responsibility to convert
 * an instance of [Beer] to a view to be inserted in the
 * searched beers' list.
 */
class BeerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.beer_name)
    private val description: TextView = view.findViewById(R.id.beer_description)
    private val abv: TextView = view.findViewById(R.id.beer_abv)
    private val tagline: TextView = view.findViewById(R.id.beer_tagline)
    private val image : ImageView = view.findViewById(R.id.beer_image)

    private var beer: Beer? = null

    init {
        view.setOnClickListener {
            this.openBeerDetail(it)
        }
    }

    private fun openBeerDetail(view: View) {
        this.beer?.let { beer ->
            val intent = Intent(view.context, BeerDetailActivity::class.java).apply {
                putExtra(Constants.BEER_KEY, Gson().toJson(beer))
            }
            view.context.startActivity(intent)
        }
    }

    fun bind(beer: Beer?) {
        if (beer == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            tagline.visibility = View.GONE
            abv.text = resources.getString(R.string.unknown)
        } else {
            showBeerData(beer)
        }
    }

    private fun showBeerData(beer: Beer) {
        this.beer = beer

        description.visibility = View.VISIBLE
        tagline.visibility = View.VISIBLE

        Picasso.get().load(beer.imageUrl).into(image);

        name.text = beer.name
        description.text = beer.description
        abv.text = beer.abv.toString()
        tagline.text = beer.tagline
    }

    companion object {

        /**
         * Builds an instance of [BeerViewHolder]
         * based on parent view.
         */
        fun create(parent: ViewGroup): BeerViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.beer_view_item, parent, false)
            return BeerViewHolder(view)
        }
    }
}