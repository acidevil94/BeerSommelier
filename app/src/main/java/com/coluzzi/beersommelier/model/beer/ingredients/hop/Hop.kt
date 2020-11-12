package com.texa.beersommelier.model.beer.ingredients.hop

import com.google.gson.annotations.SerializedName
import com.texa.beersommelier.model.beer.ingredients.malt.Malt
import com.texa.beersommelier.model.beer.measure.Measure

data class Hop (
    @field:SerializedName("name") val name: String,
    @field:SerializedName("amount") val amount: Measure,
    @field:SerializedName("add") val add: String,
    @field:SerializedName("attribute") val attribute: String
)
