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

class ItemAdapter(
    private val context: Context,
    private val dataset:List<Store>
)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView=view.findViewById(R.id.item_image)
        private val buttonView: Button=view.findViewById(R.id.add_to_cart)
        init {
            buttonView.setOnClickListener {v: View ->
                val position: Int = adapterPosition
                Toast.makeText(view.context, "Hello world", Toast.LENGTH_SHORT).show()
                //val total: TextView?= StoreActivity().findViewById<TextView>(R.id.total)
                //total?.text = "20"
                //val view: TextView = StoreActivity().findViewById(R.id.total)
                //view.text = "40"
                StoreActivity().total!!.text  = "50"
            }
        }
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
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
