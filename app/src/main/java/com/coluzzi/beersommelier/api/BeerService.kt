package com.coluzzi.beersommelier.api

import com.coluzzi.beersommelier.model.beer.Beer
import com.coluzzi.beersommelier.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Interface that contains the contracts for remote
 * API requests.
 */
interface BeerService {


    @GET("beers")
    /**
     * Method that retrieves JSON string from API_URL/beers, adding
     * additional parameters and transforms it to a list of
     * [Beer] instances.
     * @return list of [Beer] retrieved from remote
     */
    suspend fun searchBeers(
        @Query("beer_name") query: String?,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int,
        @Query("malt") malt: String?,
        @Query("hops") hop: String?,
        @Query("yeast") yeast: String?,
    ): List<Beer>




    companion object {

        /**
         * Method that uses [Retrofit] to build
         * the implementation of [BeerService], using an
         * [OkHttpClient] and setting [Constants.BEER_API_BASE_URL] as base URL.
         *
         * @return an instance of [BeerService]
         */
        fun create(): BeerService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(Constants.BEER_API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BeerService::class.java)
        }
    }
}