package com.coluzzi.beersommelier.model.beer.ingredients.hop

import com.google.gson.annotations.SerializedName
import com.coluzzi.beersommelier.model.beer.measure.Measure

data class Hop (
    @field:SerializedName("name") val name: String,
    @field:SerializedName("amount") val amount: Measure,
    @field:SerializedName("add") val add: String,
    @field:SerializedName("attribute") val attribute: String
)
