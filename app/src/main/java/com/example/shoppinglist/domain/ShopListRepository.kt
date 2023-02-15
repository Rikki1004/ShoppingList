package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun addItem(item: ShopItem)
    fun removeItem(item: ShopItem)
    fun getItem(id:Int): ShopItem
    fun editItem(item: ShopItem)
    fun getShopList() : LiveData<List<ShopItem>>
}