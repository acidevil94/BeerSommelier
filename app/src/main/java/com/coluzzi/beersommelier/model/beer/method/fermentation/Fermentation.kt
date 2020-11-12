package com.texa.beersommelier.model.beer.method.fermentation

import com.google.gson.annotations.SerializedName
import com.texa.beersommelier.model.beer.measure.Measure

data class Fermentation(
    @field:SerializedName("temp") val temp: Measure
)