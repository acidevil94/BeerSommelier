package com.coluzzi.beersommelier.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.coluzzi.beersommelier.data.BeerRequest
import com.coluzzi.beersommelier.data.BeerRepository
import com.coluzzi.beersommelier.model.beer.Beer
import kotlinx.coroutines.flow.Flow

/**
 * Class that, given an instance of [BeerRepository],
 * responds to the user interaction captured in the
 * [SearchBeerActivity] executing the requests and
 * caching the results for future calls.
 */
class SearchBeerViewModel(private val repository : BeerRepository) : ViewModel() {

    private var currentQueryValue: BeerRequest? = null
    private var currentSearchResult: Flow<PagingData<Beer>>? = null

    /**
     * Uses the [BeerRepository] to execute the [BeerRequest]
     * in input, caches the results, and saves a copy of the request,
     * because if in the future calls the same request will be passed,
     * it will return the same result.
     */
    fun searchBeer(beerFilter: BeerRequest): Flow<PagingData<Beer>> {
        val lastResult = currentSearchResult
        if (beerFilter == currentQueryValue && lastResult != null) {
            return lastResult
        }
        this.currentQueryValue = beerFilter.copy()
        val newResult: Flow<PagingData<Beer>> = repository.getSearchResultStream(beerFilter)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}