package com.texa.beersommelier.model.beer.measure

import com.google.gson.annotations.SerializedName

data class Measure (
    @field:SerializedName("value") val value: Double,
    @field:SerializedName("unit") val unit: String
) {
    fun format(): String {
        return this.value.toString() + " " +  this.unit
    }
}