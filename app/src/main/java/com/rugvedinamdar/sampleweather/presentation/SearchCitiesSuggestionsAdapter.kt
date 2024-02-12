package com.rugvedinamdar.sampleweather.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rugvedinamdar.sampleweather.R
import com.rugvedinamdar.sampleweather.data.model.City
import com.rugvedinamdar.sampleweather.databinding.ItemSearchCitySuggestionsBinding
import com.rugvedinamdar.sampleweather.domain.SearchCityListItemSelectedListener

class SearchCitiesSuggestionsAdapter(
    val dataList: List<City>,
    val itemSelectedListener: SearchCityListItemSelectedListener
) :
    RecyclerView.Adapter<SearchCitiesSuggestionsAdapter.SearchCitiesSuggestionsViewHolder>() {

    lateinit var listItemBinding: ItemSearchCitySuggestionsBinding

    class SearchCitiesSuggestionsViewHolder(itemView: ItemSearchCitySuggestionsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding: ItemSearchCitySuggestionsBinding = itemView

        fun bindData(item: City) {
            binding.tvCityName.text = item.name
            binding.tvCountryName.text = item.countryName
            binding.tvAdminAreaName.text = item.administrativeAreaName
            if (item.isFavorite) {
                binding.rightIcon.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.rightIcon.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchCitiesSuggestionsViewHolder {
        listItemBinding = ItemSearchCitySuggestionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchCitiesSuggestionsViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: SearchCitiesSuggestionsViewHolder, position: Int) {
        val item = dataList[position]
        holder.bindData(item)
        holder?.itemView?.setOnClickListener {
            itemSelectedListener.onItemSelected(position, dataList[position])
        }
        holder?.binding?.rightIcon?.setOnClickListener {
            itemSelectedListener.onFavoriteIconClicked(position, dataList[position])
        }
    }
}