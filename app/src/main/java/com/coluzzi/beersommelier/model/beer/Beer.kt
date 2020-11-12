package com.texa.beersommelier.model.beer

import com.google.gson.annotations.SerializedName
import com.texa.beersommelier.model.beer.ingredients.Ingredients
import com.texa.beersommelier.model.beer.method.Method
import com.texa.beersommelier.model.beer.measure.Measure

/**
 * Class that represents the model of a beer type, containing all informations about hops, malts,
 *  ABV (Alcohol by Volume), IBU (International Bitterness Units), FG (Final Gravity), OG (Original Gravity),
 *  EBC and SRM (European Brewery Convention & Standard Reference Method, both used for beer colour),
 *  PH, Attenuation Level, volume, boil volume, method, ingredients, food pairing, brewer tips.
 */
data class Beer  (
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("tagline") val tagline: String,
    @field:SerializedName("first_brewed") val firstBrewed: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("image_url") val imageUrl: String,
    @field:SerializedName("abv") val abv: Double,
    @field:SerializedName("ibu") val ibu: Double,
    @field:SerializedName("target_fg") val targetFG: Double,
    @field:SerializedName("target_og") val targetOG: Double,
    @field:SerializedName("ebc") val ebc: Double,
    @field:SerializedName("srm") val srm: Double,
    @field:SerializedName("ph") val ph: Double,
    @field:SerializedName("attenuation_level") val attenuationLevel: Double,
    @field:SerializedName("volume") val volume: Measure,
    @field:SerializedName("boil_volume") val boilVolume: Measure,
    @field:SerializedName("method") val method: Method,
    @field:SerializedName("ingredients") val ingredients: Ingredients,
    @field:SerializedName("food_pairing") val foodPairing: List<String>,
    @field:SerializedName("brewers_tips") val brewersTips: String,
    @field:SerializedName("contributed_by") val contributedBy: String
)