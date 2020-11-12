package com.texa.beersommelier.model.beer.method

import com.google.gson.annotations.SerializedName
import com.texa.beersommelier.model.beer.method.fermentation.Fermentation
import com.texa.beersommelier.model.beer.method.mashtemp.MashTemp

data class Method (
    @field:SerializedName("mash_temp") val mashTemp: List<MashTemp>,
    @field:SerializedName("fermentation") val fermentation: Fermentation,
    @field:SerializedName("twist") val twist: String?
)