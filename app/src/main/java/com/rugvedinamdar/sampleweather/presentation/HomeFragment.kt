package com.rugvedinamdar.sampleweather.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rugvedinamdar.sampleweather.R
import com.rugvedinamdar.sampleweather.data.model.City
import com.rugvedinamdar.sampleweather.data.repository.local.WeatherRepositoryLocalImpl
import com.rugvedinamdar.sampleweather.data.repository.remote.WeatherRepositoryRemote
import com.rugvedinamdar.sampleweather.databinding.FragmentHomeBinding
import com.rugvedinamdar.sampleweather.domain.SearchCityListItemSelectedListener
import com.rugvedinamdar.sampleweather.domain.WeatherViewModel
import com.rugvedinamdar.sampleweather.domain.WeatherViewModelFactory
import com.rugvedinamdar.sampleweather.util.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(), SearchCityListItemSelectedListener {

    private val TAG = SearchCitiesFragment::class.simpleName
    lateinit var binding: FragmentHomeBinding
    lateinit var weatherViewModel: WeatherViewModel
    lateinit var weatherRepositoryRemote: WeatherRepositoryRemote
    lateinit var weatherRepositoryLocal: WeatherRepositoryLocalImpl
    lateinit var searchCitiesSuggestionsAdapter: SearchCitiesSuggestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val citiesSearchService = RetrofitHelper.getInstance()
        weatherRepositoryRemote = WeatherRepositoryRemote(citiesSearchService)
        weatherRepositoryLocal = WeatherRepositoryLocalImpl(requireContext())
        weatherViewModel = ViewModelProvider(
            this, WeatherViewModelFactory(weatherRepositoryRemote, weatherRepositoryLocal)
        )[WeatherViewModel::class.java]
        initViews()
        initClickListeners()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        handleCitiesList()
    }

    private fun initViews() {
        binding.homeAppbar.changeToolbarFont()
        /*binding.homeAppbarLayout.setStatusBarForegroundColor(
            MaterialColors.getColor(
                binding.homeAppbarLayout,
                com.google.android.material.R.attr.colorSurface
            )
        )*/
    }

    private fun handleCitiesList() {
        Log.d(TAG, "handleCitiesList: ")
        GlobalScope.launch(Dispatchers.IO) {
            weatherRepositoryLocal.getAllFavoriteCities()
        }
        weatherViewModel.favoriteCities.observe(viewLifecycleOwner) {
            Log.d(TAG, "handleCitiesList: $it")
            if (it.isNotEmpty()) {
                binding.rlNoCities.visibility = View.GONE
                binding.rvFavoriteCities.visibility = View.VISIBLE
                searchCitiesSuggestionsAdapter =
                    SearchCitiesSuggestionsAdapter(it, this)
                binding.rvFavoriteCities.layoutManager = LinearLayoutManager(requireContext())
                binding.rvFavoriteCities.adapter = searchCitiesSuggestionsAdapter
            } else {
                binding.rvFavoriteCities.visibility = View.GONE
                binding.rlNoCities.visibility = View.VISIBLE
            }
        }
    }

    private fun initClickListeners() {
        binding.fabAddCities.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchCitiesFragment)
        }
        /*binding.homeAppbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }*/
    }

    override fun onItemSelected(position: Int, city: City) {
        Log.d(TAG, "onItemSelected: city: ${city.name}")
    }

    override fun onFavoriteIconClicked(position: Int, city: City) {
        Log.d(TAG, "onFavoriteIconClicked: city: ${city.name}")
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
            weatherRepositoryLocal.getAllFavoriteCities()
        }
    }

    fun Toolbar.changeToolbarFont() {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == title) {
                view.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.sacramento_regular);
//                    Typeface.createFromAsset(view.context.assets, "fonts/sacramento_regular.ttf")
                break
            }
        }
    }
}