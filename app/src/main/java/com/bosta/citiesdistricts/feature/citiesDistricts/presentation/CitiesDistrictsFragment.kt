package com.bosta.citiesdistricts.feature.citiesDistricts.presentation

import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosta.citiesdistricts.android.BostaApp
import com.bosta.citiesdistricts.common.data.models.state.Resource
import com.bosta.citiesdistricts.common.presentation.base.fragment.BaseFragment
import com.bosta.citiesdistricts.databinding.FragmentCitiesDistrictsBinding
import com.bosta.citiesdistricts.feature.citiesDistricts.presentation.adapter.CityDistrictsAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesDistrictsFragment :
    BaseFragment<FragmentCitiesDistrictsBinding>(FragmentCitiesDistrictsBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CitiesDistrictsViewModel> { viewModelFactory }

    private val cityDistrictsAdapter by lazy { CityDistrictsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as BostaApp).appComponent.inject(this)
    }

    override fun setupUI() {
        setupRecyclerView()
        setupObservers()
        setupSearchListener()
    }

    private fun setupSearchListener() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.setSearchQuery(text?.toString().orEmpty())
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = cityDistrictsAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            )
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is Resource.Loading -> loadingDialogDelegate.showLoading(state.loading)
                            is Resource.Success -> Log.d(
                                "CitiesDistrictsFragment",
                                "Data loaded: ${state.data}"
                            )

                            is Resource.Failure -> handleBostaException(
                                exception = state.exception,
                                onRetry = { viewModel.fetchCitiesAndDistricts() }
                            )
                        }
                    }
                }
                launch {
                    viewModel.filteredCities.collect { filteredList ->
                        cityDistrictsAdapter.submitCities(filteredList)
                    }
                }
            }
        }
    }
}