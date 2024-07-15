package com.grafocrate.photoeditor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grafocrate.photoeditor.databinding.ItemFilterBinding
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class FilterAdapter :RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    private val titles = arrayOf("One","Two","Three", "Four","Five")
    private val images = intArrayOf(R.drawable.btn1,
        R.drawable.btn2, R.drawable.btn3,
        R.drawable.btn4, R.drawable.btn5)
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        init {
            itemImage = itemView.findViewById(R.id.imageButton)
            itemTitle = itemView.findViewById(R.id.textView)
            itemView.setOnClickListener {
//                Toast.makeText(this, itemTitle!!.text.toString() + "Its a toast!", Toast.LENGTH_SHORT).show()
                Log.i("itemview", itemTitle.text.toString()+ "clicked")
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filter, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}



