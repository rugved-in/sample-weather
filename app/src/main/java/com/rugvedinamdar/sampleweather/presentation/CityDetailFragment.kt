package com.rugvedinamdar.sampleweather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rugvedinamdar.sampleweather.databinding.FragmentCityDetailBinding
import com.rugvedinamdar.sampleweather.util.Constants

class CityDetailFragment : Fragment() {
    lateinit var binding: FragmentCityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCityDetailBinding.inflate(layoutInflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.tvCityKey.text = arguments?.getString(Constants.LOCATION_KEY)
    }
}