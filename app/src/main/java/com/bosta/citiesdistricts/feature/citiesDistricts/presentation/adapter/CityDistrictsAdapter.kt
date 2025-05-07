package com.bosta.citiesdistricts.feature.citiesDistricts.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bosta.citiesdistricts.databinding.ItemCityBinding
import com.bosta.citiesdistricts.databinding.ItemDistrictBinding
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.City
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.District

sealed class CityDistrictListItem {
    data class CityItem(val city: City, var isExpanded: Boolean = false) : CityDistrictListItem()
    data class DistrictItem(val district: District, val parentCity: String) : CityDistrictListItem()
}

class CityDistrictsAdapter :
    ListAdapter<CityDistrictListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        private const val VIEW_TYPE_CITY = 0
        private const val VIEW_TYPE_DISTRICT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CityDistrictListItem.CityItem -> VIEW_TYPE_CITY
            is CityDistrictListItem.DistrictItem -> VIEW_TYPE_DISTRICT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CITY -> {
                val binding =
                    ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CityViewHolder(binding)
            }

            VIEW_TYPE_DISTRICT -> {
                val binding =
                    ItemDistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DistrictViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CityDistrictListItem.CityItem -> (holder as CityViewHolder).bind(item, position)
            is CityDistrictListItem.DistrictItem -> (holder as DistrictViewHolder).bind(item)
        }
    }

    inner class CityViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CityDistrictListItem.CityItem, position: Int) {
            binding.tvCity.text = item.city.cityName
            binding.ivArrow.rotation = if (item.isExpanded) 180f else 0f

            binding.root.setOnClickListener {
                toggleExpandCollapse(position)
            }
        }

        private fun toggleExpandCollapse(position: Int) {
            val currentList = currentList.toMutableList()
            val cityItem = currentList[position] as CityDistrictListItem.CityItem
            val districts = cityItem.city.districts.map {
                CityDistrictListItem.DistrictItem(it, cityItem.city.cityName)
            }

            if (cityItem.isExpanded) {
                // Collapse
                val updatedList = currentList.filterNot {
                    it is CityDistrictListItem.DistrictItem && it.parentCity == cityItem.city.cityName
                }.map {
                    if (it is CityDistrictListItem.CityItem && it.city.cityName == cityItem.city.cityName) {
                        it.copy(isExpanded = false)
                    } else it
                }
                submitList(updatedList)
            } else {
                // Collapse all others
                val collapsedList =
                    currentList.filterNot { it is CityDistrictListItem.DistrictItem }
                        .map {
                            if (it is CityDistrictListItem.CityItem) it.copy(isExpanded = false) else it
                        }

                val insertIndex = position + 1
                val newList = collapsedList.toMutableList().apply {
                    addAll(insertIndex, districts)
                    this[position] = cityItem.copy(isExpanded = true)
                }

                submitList(newList)
            }
        }
    }

    inner class DistrictViewHolder(private val binding: ItemDistrictBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CityDistrictListItem.DistrictItem) {
            binding.tvDistricts.text = item.district.districtName
            binding.tvUncovered.visibility =
                if (item.district.coverage != "BOSTA") View.VISIBLE else View.GONE
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CityDistrictListItem>() {
        override fun areItemsTheSame(
            oldItem: CityDistrictListItem,
            newItem: CityDistrictListItem
        ): Boolean {
            return when {
                oldItem is CityDistrictListItem.CityItem && newItem is CityDistrictListItem.CityItem ->
                    oldItem.city.cityId == newItem.city.cityId

                oldItem is CityDistrictListItem.DistrictItem && newItem is CityDistrictListItem.DistrictItem ->
                    oldItem.district.districtId == newItem.district.districtId

                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: CityDistrictListItem,
            newItem: CityDistrictListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun submitCities(cities: List<City>) {
        val cityItems = cities.map { CityDistrictListItem.CityItem(it) }
        submitList(cityItems)
    }
}