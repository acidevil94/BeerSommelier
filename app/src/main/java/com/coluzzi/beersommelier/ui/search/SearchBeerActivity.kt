package com.coluzzi.beersommelier.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.coluzzi.beersommelier.api.BeerService
import com.coluzzi.beersommelier.data.BeerRequest
import com.coluzzi.beersommelier.data.BeerRepository
import com.coluzzi.beersommelier.databinding.ActivitySearchBeerBinding
import com.coluzzi.beersommelier.ui.filter.BeerFilterDialogFragment
import com.coluzzi.beersommelier.util.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


/**
 * Main Activity of the application, that has the responsibility
 * to show the results of the requests executed versus the beer API.
 */
class SearchBeerActivity : AppCompatActivity() , BeerFilterDialogFragment.BeerFilterDialogFragmentListener{

    // binding for view
    private lateinit var binding: ActivitySearchBeerBinding
    private lateinit var viewModel: SearchBeerViewModel
    private val adapter = BeerAdapter()
    private lateinit var actualAppliedFilter : BeerRequest
    private var searchJob : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBeerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = SearchBeerViewModel(BeerRepository(BeerService.create()))

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.beerList.addItemDecoration(decoration)

        initAdapter()

        // retry button listener that refresh paging data
        binding.retryButton.setOnClickListener { adapter.retry() }

        binding.buttonFilter.setOnClickListener{
            this.openFilterDialog()
        }

        this.actualAppliedFilter = savedInstanceState?.getParcelable(Constants.LAST_APPLIED_FILTER) ?: BeerRequest.empty()

        search()
        initSearch()
    }

    private fun openFilterDialog() {
        val fragment = BeerFilterDialogFragment.getInstance(this.actualAppliedFilter)
        fragment.show(supportFragmentManager, Constants.FILTER_DIALOG_TAG)
    }

    override fun onNewFilter(filter: BeerRequest) {
        this.actualAppliedFilter = filter
        this.search()
    }


    private fun search() {
        // Cancel the previous job
        searchJob?.cancel()
        // Launch a new one
        searchJob = lifecycleScope.launch {
            viewModel.searchBeer(actualAppliedFilter).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.LAST_SEARCH_QUERY, binding.searchBeer.text.trim().toString())
        outState.putParcelable(Constants.LAST_APPLIED_FILTER, this.actualAppliedFilter)
    }


    private fun initAdapter() {
        binding.beerList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = BeerLoadStateAdapter { adapter.retry() },
            footer = BeerLoadStateAdapter { adapter.retry() }
        )

        // This callback notifies us every time there's a change in the load state via a CombinedLoadStates object
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.beerList.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initSearch() {
        binding.searchBeer.setText(this.actualAppliedFilter.beerName)

        binding.searchBeer.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateBeerListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchBeer.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateBeerListFromInput()
                true
            } else {
                false
            }
        }


        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter {
                    Log.d(LOG_TAG, "initSearch(): changed refreshed property of flow: ${it.refresh}")
                    it.refresh is LoadState.NotLoading
                }
                .collect {
                    Log.d(LOG_TAG, "initSearch(): scrolling list to start...")
                    binding.beerList.scrollToPosition(0)
                }
        }
    }

    private fun updateBeerListFromInput() {
        val newQuery = binding.searchBeer.text.toString().trim()
        this.actualAppliedFilter.beerName = newQuery
        this.search()
    }




    companion object {
        private const val LOG_TAG = "SearchBeerActivity"
    }



}