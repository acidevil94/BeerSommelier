package com.texa.beersommelier.util

/**
 * Class that contains application Constants.
 */
class Constants {

    companion object {

        const val ACTUAL_FILTER = "actual_filter"
        const val BEER_KEY = "beer"
        const val LAST_SEARCH_QUERY: String = "last_search_query"
        const val LAST_APPLIED_FILTER: String = "last_applied_filter"
        const val BEER_API_BASE_URL = "https://api.punkapi.com/v2/"
        const val FILTER_DIALOG_TAG = "BeerFilter"
        const val BEER_STARTING_PAGE_INDEX = 1
        const val BEER_PER_PAGE_ITEM = 5
        const val MAX_DISPLAYED_BEERS_ITEM = 20
    }
}