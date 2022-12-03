package com.tregub.acromine.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tregub.acromine.databinding.ItemAcronymDefinitionBinding
import com.tregub.acromine.screen.data.AcronymDefinitionViewState

/**
 * An intermediary between the data source and the UI implementation
 *  to display a list of definitions
 */
class DefinitionsAdapter :
    ListAdapter<AcronymDefinitionViewState, DefinitionsAdapter.ViewHolder>(this) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemAcronymDefinitionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = getItem(position)
    }

    /**
     * Wrapper used by [ListAdapter] to display definitions
     *
     * @param binding actual view holder
     */
    inner class ViewHolder internal constructor(
        val binding: ItemAcronymDefinitionBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    private companion object : DiffUtil.ItemCallback<AcronymDefinitionViewState>() {

        override fun areItemsTheSame(
            old: AcronymDefinitionViewState,
            new: AcronymDefinitionViewState,
        ): Boolean = old == new

        override fun areContentsTheSame(
            old: AcronymDefinitionViewState,
            new: AcronymDefinitionViewState,
        ): Boolean = true
    }
}
