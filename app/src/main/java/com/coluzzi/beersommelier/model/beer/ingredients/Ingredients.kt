package com.coluzzi.beersommelier.model.beer.ingredients

import com.google.gson.annotations.SerializedName
import com.coluzzi.beersommelier.model.beer.ingredients.hop.Hop
import com.coluzzi.beersommelier.model.beer.ingredients.malt.Malt

data class Ingredients (
    @field:SerializedName("malt") val malt: List<Malt>,
    @field:SerializedName("hops") val hops: List<Hop>,
    @field:SerializedName("yeast") val yeast: String
)