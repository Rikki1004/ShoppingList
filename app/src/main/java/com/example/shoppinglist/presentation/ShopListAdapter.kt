package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import kotlin.io.path.fileVisitor

class ShopListAdapter: ListAdapter<ShopItem, ShopListAdapter.ItemShopViewHolder>(ShopItemDiffCallback()){

   var onShopItemLongClickListener : ((ShopItem) -> Unit)? = null
   var onShopItemClickListener : ((ShopItem) -> Unit)? = null

   companion object{
      const val ACTIVE_ITEM = 1
      const val INACTIVE_ITEM = 0
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemShopViewHolder {
      val resId = if (viewType == ACTIVE_ITEM)
         R.layout.active_shop_item
      else
         R.layout.disable_shop_item

      val view = LayoutInflater.from(parent.context).inflate(
         resId,
         parent,
         false)
      return ItemShopViewHolder(view)
   }

   override fun getItemViewType(position: Int): Int {
      return if (getItem(position).is_active)
         ACTIVE_ITEM
      else
         INACTIVE_ITEM
   }

   override fun onBindViewHolder(holder: ItemShopViewHolder, position: Int) {
      val item = getItem(position)
      holder.tv_text.text = item.name
      holder.tv_count.text = item.count.toString()

      holder.itemView.setOnLongClickListener {
         onShopItemLongClickListener?.invoke(item)
         true
      }
      holder.itemView.setOnClickListener {
         onShopItemClickListener?.invoke(item)
      }
   }

   class ItemShopViewHolder(view: View) : ViewHolder(view){
      val tv_text = view.findViewById<TextView>(R.id.tv_name)
      val tv_count = view.findViewById<TextView>(R.id.tv_count)
   }
}