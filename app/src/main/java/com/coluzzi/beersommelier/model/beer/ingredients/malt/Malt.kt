package com.coluzzi.beersommelier.model.beer.ingredients.malt

import com.google.gson.annotations.SerializedName
import com.coluzzi.beersommelier.model.beer.measure.Measure

data class Malt(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("amount") val amount: Measure
)