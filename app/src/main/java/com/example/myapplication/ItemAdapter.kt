package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.cos

class ItemAdapter(
    private val context: Context,
    private val dataset:List<Store>,
    private val listener: OnItemClickListener
)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder( itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val textView: TextView = itemView.findViewById(R.id.item_title)
        val imageView: ImageView=itemView.findViewById(R.id.item_image)
        val costView: TextView = itemView.findViewById(R.id.item_cost)
        val buttonView: Button=itemView.findViewById(R.id.add_to_cart)
        init {
            buttonView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position!= RecyclerView.NO_POSITION) {
                val item = dataset[position]
                val cost = item.costResourceId.toFloat()
                listener.onItemCLick(position, cost)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemCLick(position: Int, cost: Float)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_store_item,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item=dataset[position]
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)
        holder.costView.text = item.costResourceId.toString()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
