package com.texa.beersommelier.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.texa.beersommelier.api.BeerService
import com.texa.beersommelier.model.beer.Beer
import com.texa.beersommelier.util.Constants
import kotlinx.coroutines.flow.Flow

/**
 * Class that has the responsibility to launch
 * an execution of a [BeerRequest], using the [Pager]
 * library.
 */
class BeerRepository (private val service : BeerService) {

    /**
     * Search beers whose name and additional parameters  match the ones
     * specified in [BeerRequest] input, exposed as a stream of data that will emit
     * every time it gets more data from the network.
     */
    fun getSearchResultStream(beerFilter : BeerRequest): Flow<PagingData<Beer>> {
        return Pager(config = PagingConfig(pageSize = Constants.BEER_PER_PAGE_ITEM
                            , enablePlaceholders = false, initialLoadSize = Constants.MAX_DISPLAYED_BEERS_ITEM,
           maxSize = Constants.MAX_DISPLAYED_BEERS_ITEM), pagingSourceFactory =
                                    {BeerPagingSource(service, beerFilter)}).flow

    }
}