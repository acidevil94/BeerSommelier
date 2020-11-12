package com.coluzzi.beersommelier.model.beer.method.mashtemp

import com.google.gson.annotations.SerializedName
import com.coluzzi.beersommelier.model.beer.measure.Measure

data class MashTemp (
    @field:SerializedName("temp") val temp: Measure,
    @field:SerializedName("duration") val duration: Int?
)