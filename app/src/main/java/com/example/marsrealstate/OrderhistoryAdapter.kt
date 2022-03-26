package com.example.marsrealstate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marsrealstate.database.Item
import com.example.marsrealstate.database.getCurrenncyInstance
import com.example.marsrealstate.databinding.ItemListItemBinding

class OrderhistoryAdapter(private val OnitemClciked : (Item) -> Unit): ListAdapter<Item, OrderhistoryAdapter.OrderViewHolder>(DiffCallBack) {

    companion object DiffCallBack  : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }
    }

    class OrderViewHolder (private var binding:ItemListItemBinding ) : RecyclerView.ViewHolder(binding.root){
         fun bind (item: Item){
          binding.photoorder = item

             binding.itemQuantity.text = item.getCurrenncyInstance()

         }
     }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderhistoryAdapter.OrderViewHolder {

        val layout = ItemListItemBinding.inflate((LayoutInflater.from(parent.context)))
        return  OrderViewHolder(layout)
    }

    override fun onBindViewHolder(holder: OrderhistoryAdapter.OrderViewHolder, position: Int) {

    val current = getItem(position)
    holder.bind(current)

    holder.itemView.setOnClickListener {
        OnitemClciked(current)
    }

    }




}