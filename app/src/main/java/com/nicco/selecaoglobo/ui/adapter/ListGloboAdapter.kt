package com.nicco.selecaoglobo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicco.business.model.ProductModelUi
import com.nicco.selecaoglobo.R

object DiffUtilArch : DiffUtil.ItemCallback<ProductModelUi>() {
    override fun areItemsTheSame(
        oldItem: ProductModelUi,
        newItem: ProductModelUi
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ProductModelUi,
        newItem: ProductModelUi
    ): Boolean = oldItem == newItem
}

interface Interaction {
    fun onClick(item: ProductModelUi)
}

class ListGloboAdapter(interaction: Interaction) :
    ListAdapter<ProductModelUi, ListGloboAdapter.ViewHolder>(
        DiffUtilArch
    ) {
    private var interaction: Interaction? = interaction

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false), interaction
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val tvId by lazy { itemView.findViewById<TextView>(R.id.id) }
        val createdAt by lazy { itemView.findViewById<TextView>(R.id.createdAt) }
        val name by lazy { itemView.findViewById<TextView>(R.id.name) }
        val image by lazy { itemView.findViewById<TextView>(R.id.image) }
        val description by lazy { itemView.findViewById<TextView>(R.id.description) }
        val price by lazy { itemView.findViewById<TextView>(R.id.price) }
        val color by lazy { itemView.findViewById<TextView>(R.id.color) }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)
            interaction?.onClick(clicked)
        }

        fun bind(item: ProductModelUi) = with(itemView) {
            tvId.text = "id: " + item.id.toString()

            if (item.createdAt.isNotEmpty()) {
                createdAt.text = "createdAt: " + item.createdAt
                createdAt.visibility = VISIBLE
            } else {
                createdAt.visibility = GONE
            }

            name.text = "name: " + item.name

            if (item.image.isNotEmpty()) {
                image.text = "image: " + item.image
                image.visibility = VISIBLE
            } else {
                image.visibility = GONE
            }

            description.text = "description: " + item.description
            price.text = "price: " + item.price

            if (item.color.isNotEmpty()) {
                color.text = "color: " + item.color
                color.visibility = VISIBLE
            } else {
                color.visibility = GONE
            }
        }
    }
}