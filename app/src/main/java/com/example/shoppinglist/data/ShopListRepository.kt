package com.example.shoppinglist.data

interface ShopListRepository {
    fun addItem(item: ShopItem)
    fun removeItem(item: ShopItem)
    fun getItem(id:Int):ShopItem
    fun moveItem(Item:ShopItem)
    fun getShopList() : List<ShopItem>
}