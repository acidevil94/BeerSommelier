package com.texa.beersommelier.data

import android.util.Log
import androidx.paging.PagingSource
import com.texa.beersommelier.api.BeerService
import com.texa.beersommelier.model.beer.Beer
import com.texa.beersommelier.util.Constants
import java.lang.Exception

/**
 * Class that has the logic to query the instance of [BeerService]
 * using the configuration of the actual page of the library [Pager].
 */
class BeerPagingSource (
            private val service: BeerService
            ,private val beerFilter: BeerRequest)
    : PagingSource<Int, Beer>() {

    /**
     * Loads a List of [Beer] using the instance of [BeerService],
     * and returns the list to the [Pager] library, that will
     * bring it to the flow.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        Log.d(Companion.LOG_TAG, "load(): Init...")
        val position = params.key ?: Constants.BEER_STARTING_PAGE_INDEX
        Log.d(Companion.LOG_TAG, "load(): actual position: $position")
        try {
            Log.d(Companion.LOG_TAG, "load(): trying to load from position: $position")
            return tryLoad(position, params)
        } catch (exception: Exception) {
            Log.e(Companion.LOG_TAG, "load(): error while loading from position: $position", exception)
            return LoadResult.Error(exception)
        }
    }

    private suspend fun tryLoad(
        position: Int,
        params: LoadParams<Int>
    ) : LoadResult<Int, Beer>{

        val beerName = if (beerFilter.beerName.isNullOrEmpty()) null else beerFilter.beerName
        val maltName = if (beerFilter.maltName.isNullOrEmpty()) null else beerFilter.maltName
        val hopName = if (beerFilter.hopsName.isNullOrEmpty()) null else beerFilter.hopsName
        val yeast = if (beerFilter.yeastName.isNullOrEmpty()) null else beerFilter.yeastName

        Log.d(Companion.LOG_TAG, "load(): executing query: ${beerName}, loadSize: ${params.loadSize}")
        val response = service.searchBeers(beerName, position, params.loadSize, maltName,hopName,yeast)
        Log.d(Companion.LOG_TAG, "load(): executed query: ${beerName}, found ${response.size} beers")
        val prevPosition = if (position == Constants.BEER_STARTING_PAGE_INDEX) null else position - 1
        val nextPosition = if (response.isEmpty()) null else position + 1
        Log.d(Companion.LOG_TAG, "load(): prevPosition: $prevPosition, nextPosition: $nextPosition")
        return LoadResult.Page(
            data = response,
            prevKey = prevPosition,
            nextKey =nextPosition
        )
    }

    companion object {
        private const val LOG_TAG = "BeerPagingSource"
    }


}