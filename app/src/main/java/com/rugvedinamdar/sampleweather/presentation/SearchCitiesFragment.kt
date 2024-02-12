package com.rugvedinamdar.sampleweather.presentation

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rugvedinamdar.sampleweather.R
import com.rugvedinamdar.sampleweather.data.model.City
import com.rugvedinamdar.sampleweather.data.repository.local.WeatherRepositoryLocalImpl
import com.rugvedinamdar.sampleweather.data.repository.remote.WeatherRepositoryRemote
import com.rugvedinamdar.sampleweather.databinding.FragmentSearchCitiesBinding
import com.rugvedinamdar.sampleweather.domain.SearchCityListItemSelectedListener
import com.rugvedinamdar.sampleweather.domain.WeatherViewModel
import com.rugvedinamdar.sampleweather.domain.WeatherViewModelFactory
import com.rugvedinamdar.sampleweather.util.Constants
import com.rugvedinamdar.sampleweather.util.DTOMapper
import com.rugvedinamdar.sampleweather.util.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchCitiesFragment : Fragment(), SearchCityListItemSelectedListener {
    private val TAG = SearchCitiesFragment::class.simpleName
    lateinit var binding: FragmentSearchCitiesBinding
    lateinit var weatherViewModel: WeatherViewModel
    lateinit var weatherRepositoryRemote: WeatherRepositoryRemote
    lateinit var weatherRepositoryLocal: WeatherRepositoryLocalImpl
    lateinit var searchCitiesSuggestionsAdapter: SearchCitiesSuggestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchCitiesBinding.inflate(inflater, container, false)
        val citiesSearchService = RetrofitHelper.getInstance()
        weatherRepositoryRemote = WeatherRepositoryRemote(citiesSearchService)
        weatherRepositoryLocal = WeatherRepositoryLocalImpl(requireContext())
        weatherViewModel = ViewModelProvider(
            this, WeatherViewModelFactory(weatherRepositoryRemote, weatherRepositoryLocal)
        )[WeatherViewModel::class.java]
        initViews()
        return binding.root
    }

    private fun initViews() {
        //hide searchview after search button is clicked on keyboard
        binding.searchView.editText.setOnEditorActionListener { v, actionId, event ->
            Log.d(TAG, "init: setOnEditorActionListener: actionId: $actionId event: ${event}")
            binding.searchBar.setText(binding.searchView.getText())
            binding.searchView.hide()
            if (event != null) {
                if (event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                    if (!binding.searchView.getText().isEmpty()) {
                        GlobalScope.launch(Dispatchers.IO) {
                            weatherRepositoryRemote.getCitySearch(binding.searchView.text.toString())
                        }
                    }
                }
            }
            false
        }
        binding.searchView.editText.doOnTextChanged { text, start, before, count ->
            Log.d(TAG, "doOnTextChanged: text: $text")/*if (text != null) {
                    if (text.isNotEmpty()) {
                        GlobalScope.launch(Dispatchers.IO) {
                            repository.getAutocompleteSearchCities(text.toString())
                        }
                    } else if (text.isEmpty()) {
                        if (this::searchCitiesSuggestionsAdapter.isInitialized) {
                            searchCitiesSuggestionsAdapter.dataList.clear()
                            searchCitiesSuggestionsAdapter.notifyDataSetChanged()
                        }
                    }
                }*/
        }/* weatherViewModel.autocompleteSearchList.observe(viewLifecycleOwner) {
             Log.d(TAG, "initViews: autocompleteSearchList: $it")
             searchCitiesSuggestionsAdapter = SearchCitiesSuggestionsAdapter(it, this)
             binding.rvCitySuggestions.layoutManager = LinearLayoutManager(requireContext())
             binding.rvCitySuggestions.adapter = searchCitiesSuggestionsAdapter
         }*/

        weatherViewModel.citySearchList.observe(viewLifecycleOwner) {
            Log.d(TAG, "initViews: citySearchList $it")

            searchCitiesSuggestionsAdapter =
                SearchCitiesSuggestionsAdapter(DTOMapper.citySearchListToSearchCityList(it), this)
            binding.rvCitySuggestions.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCitySuggestions.adapter = searchCitiesSuggestionsAdapter
        }


    }

    override fun onItemSelected(position: Int, city: City) {
        Log.d(TAG, "onItemSelected: position: $position city: ${city.name}")
        val bundle = bundleOf(Pair(Constants.LOCATION_KEY, city.key))
        findNavController().popBackStack()
        findNavController().navigate(R.id.action_homeFragment_to_cityDetailFragment, bundle)
    }

    override fun onFavoriteIconClicked(position: Int, city: City) {
        Log.d(TAG, "onFavoriteIconClicked: position: $position city: ${city.name}")
        GlobalScope.launch(Dispatchers.IO) {
            if (city.isFavorite) {
                withContext(Dispatchers.Main) {
                    searchCitiesSuggestionsAdapter.dataList[position].isFavorite = false
                    searchCitiesSuggestionsAdapter.notifyItemChanged(position)
                }
                city.isFavorite = false
                weatherRepositoryLocal.upsertCity(city)
            } else {
                withContext(Dispatchers.Main) {
                    searchCitiesSuggestionsAdapter.dataList[position].isFavorite = true
                    searchCitiesSuggestionsAdapter.notifyItemChanged(position)
                }
                city.isFavorite = true
                weatherRepositoryLocal.upsertCity(city)
            }
        }

    }
}