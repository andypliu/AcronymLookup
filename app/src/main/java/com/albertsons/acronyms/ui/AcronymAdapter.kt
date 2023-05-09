package com.albertsons.acronyms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.albertsons.acronyms.R
import com.albertsons.acronyms.databinding.ItemDefinitionBinding
import com.albertsons.acronyms.domain.Definition

class AcronymAdapter : RecyclerView.Adapter<AcronymAdapter.AcronymDefinitionViewHolder>() {

    var definitions: List<Definition> =  emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcronymDefinitionViewHolder {
        val withDataBinding: ItemDefinitionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AcronymDefinitionViewHolder.LAYOUT,
            parent,
            false)
        return AcronymDefinitionViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AcronymDefinitionViewHolder, position: Int) {
        if (position %2 == 0) {   // alternate row background color
            holder.itemView.setBackgroundResource(R.color.lightBlue)
        } else {
            holder.itemView.setBackgroundResource(R.color.lightGreen)
        }

        holder.viewDataBinding.also {
            it.definition = definitions[position]   // assign the definition based on position
        }
    }

    override fun getItemCount(): Int {
        return definitions?.size ?: 0
    }

    class AcronymDefinitionViewHolder(val viewDataBinding: ItemDefinitionBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_definition
        }
    }
}