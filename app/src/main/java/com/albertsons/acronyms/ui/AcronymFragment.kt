package com.albertsons.acronyms.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.albertsons.acronyms.R
import com.albertsons.acronyms.databinding.FragmentAcronymBinding
import com.albertsons.acronyms.exception.ExceptionType
import com.albertsons.acronyms.viewmodel.AcronymViewModel

class AcronymFragment : Fragment() {

    // Set up the viewModel for acronym definitions
    private val viewModel: AcronymViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, AcronymViewModel.Factory(activity.application))[AcronymViewModel::class.java]
    }

    private var viewModelAdapter: AcronymAdapter? = null
    private var loadingSpinner: ProgressBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observe change on the acronym definitions
        viewModel.acronymsDefinitions.observe(viewLifecycleOwner) { acronymDefinitions ->
            acronymDefinitions?.apply {
                // update the recycle view adapter when there is update on definitions
                if (this.isEmpty()) {
                    viewModelAdapter?.definitions = emptyList()
                    Toast.makeText(activity, context?.getString(R.string.noDefitionFound), Toast.LENGTH_LONG).show()
                } else {
                    if (this[0].definitions.isNullOrEmpty()) {
                        viewModelAdapter?.definitions = emptyList()
                    } else {
                        viewModelAdapter?.definitions = this[0].definitions!!
                    }
                }

                loadingSpinner?.visibility = View.GONE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAcronymBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_acronym,
            container,
            false)

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModelAdapter = AcronymAdapter()
        binding.definitionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        // Observer for errors.
        viewModel.eventError.observe(viewLifecycleOwner) { exceptionType ->
            when (exceptionType) {
                ExceptionType.NETWORK -> displayErrorMessage(context?.getString(R.string.networkError))
                ExceptionType.DATA -> displayErrorMessage(context?.getString(R.string.dataError))
                ExceptionType.UNKNOWN -> displayErrorMessage(context?.getString(R.string.unknownError))
                else -> {}
            }
        }

        loadingSpinner = binding.loadingSpinner

        binding.lookup.setOnClickListener {
            val acronym = binding.acronym.text.toString().trim()
            if (acronym.isNullOrEmpty()) {            // empty input check
                Toast.makeText(activity, context?.getString(R.string.enterAcronym), Toast.LENGTH_LONG).show()
            } else {
                loadingSpinner?.visibility = View.VISIBLE
                viewModel.lookupDefinition(acronym)  // call viewModel to lookup acronym definitions
            }
            // hide keyboard
            val inputManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(it.windowToken, 0)
        }

        return binding.root
    }

    /**
     * Display a Toast message when there is error
     */
    private fun displayErrorMessage(error : String?) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
        viewModel.onErrorShown()
    }
}