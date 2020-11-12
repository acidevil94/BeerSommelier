package com.texa.beersommelier.ui.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.texa.beersommelier.R
import com.texa.beersommelier.data.BeerRequest
import com.texa.beersommelier.databinding.DialogFilterBinding
import com.texa.beersommelier.util.Constants


/**
 * Fragment that has the responsibility to get the [BeerRequest]
 * filter specification from the user and bring it to
 * the Activity that has called that fragment.
 */
class BeerFilterDialogFragment()  : DialogFragment() {

    /**
     * Interface that represents the listener of
     * that fragment.
     */
    interface BeerFilterDialogFragmentListener {
        fun onNewFilter(filter : BeerRequest)
    }

    private lateinit var listener: BeerFilterDialogFragmentListener
    private lateinit var binding: DialogFilterBinding


    private lateinit var prevFilter : BeerRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)

       this.prevFilter = arguments?.getParcelable(Constants.ACTUAL_FILTER)?: BeerRequest.empty()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFilterBinding.inflate(layoutInflater,container,false)
        showActualFilter(binding)
        setClickListener(binding)
        return binding.root
    }

    private fun setClickListener(binding: DialogFilterBinding) {
        binding.buttonApplyFilter.setOnClickListener{
            prevFilter.maltName = binding.maltValue.text.toString()
            prevFilter.hopsName = binding.hopsValue.text.toString()
            prevFilter.yeastName = binding.yeastValue.text.toString()
            this.listener.onNewFilter(prevFilter)
            this.dismiss()
        }
        binding.buttonResetFilters.setOnClickListener{
            prevFilter.resetAdditional()
            this.listener.onNewFilter(prevFilter)
            this.dismiss()
        }
    }

    private fun showActualFilter(binding: DialogFilterBinding) {
        binding.maltValue.setText(this.prevFilter.maltName, TextView.BufferType.EDITABLE)
        binding.hopsValue.setText(this.prevFilter.hopsName, TextView.BufferType.EDITABLE)
        binding.yeastValue.setText(this.prevFilter.yeastName, TextView.BufferType.EDITABLE)

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.listener = activity as BeerFilterDialogFragmentListener
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        requireDialog().window?.setWindowAnimations(
            R.style.DialogAnimation
        )
    }



    companion object {

        /**
         * Builds an instance of [BeerFilterDialogFragment] passing it the
         * [BeerRequest] in input.
         */
        fun getInstance(prevFilter: BeerRequest) : BeerFilterDialogFragment{
            return BeerFilterDialogFragment()
                            .apply {
                                arguments = Bundle()
                                                .apply  {
                                                     putParcelable(Constants.ACTUAL_FILTER, prevFilter)
                                                }
                            }
        }
    }
}