package com.example.templatesampleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.templatesampleapp.R
import com.example.templatesampleapp.databinding.CurrentItemModelBinding
import com.example.templatesampleapp.model.ui.CurrencyUiModel
import com.example.templatesampleapp.utils.showLog

/**
 * @author Umer Bilal
 * Created 11/9/2023 at 2:32 AM
 */

// Mark it implemented the adapter List Adatper
class ExchangeRvAdapter : ListAdapter<CurrencyUiModel, ExchangeRvAdapter.ExchangeViewHolder>(CurrencyModelDiffCallback()) {



    inner class ExchangeViewHolder(val currentItemModeBinding: CurrentItemModelBinding) : ViewHolder(currentItemModeBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder {
        val view=DataBindingUtil.inflate<CurrentItemModelBinding>(
            LayoutInflater.from(parent.context),
            R.layout.current_item_model,
            parent,
            false
        )
        return ExchangeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.currentItemModeBinding.model=getItem(position)
    }
}



class CurrencyModelDiffCallback : DiffUtil.ItemCallback<CurrencyUiModel>() {

    override fun areItemsTheSame(oldItem: CurrencyUiModel, newItem: CurrencyUiModel): Boolean {
        return oldItem.currentCurrency == newItem.currentCurrency
    }

    override fun areContentsTheSame(oldItem: CurrencyUiModel, newItem: CurrencyUiModel): Boolean {
        return oldItem.currentCurrencyValue == newItem.currentCurrencyValue
    }
}