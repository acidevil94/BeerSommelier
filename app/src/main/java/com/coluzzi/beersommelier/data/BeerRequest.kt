package com.coluzzi.beersommelier.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * Class that represents the request to execute versus the
 * remote API.
 */
data class BeerRequest (
            var beerName : String?,
            var maltName: String?,
            var hopsName: String?,
            var yeastName: String?,
) : Parcelable{

    /**
     * Reset the request's additional parameters.
     * Following are the additional:
     *  - malt name
     *  - hop name
     *  - yeast name
     */
    fun resetAdditional() {
        this.maltName = null
        this.hopsName = null
        this.yeastName = null
    }


    companion object {
        /**
         * Creates an instance of [BeerRequest] that does
         * not specify any value for parameters.
         */
        fun empty() : BeerRequest {
            return BeerRequest(null, null, null, null)
        }
    }
}