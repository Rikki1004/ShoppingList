package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository{

    val shopList = mutableListOf<ShopItem>()
    var autoIncrementId = 0

    override fun addItem(item: ShopItem) {
        if (item.id == ShopItem.UNEXPECTED_ID){
            item.id = autoIncrementId++
        }
        shopList.add(item)
    }

    override fun removeItem(item: ShopItem) {
        shopList.remove(item)
    }

    override fun getItem(id: Int): ShopItem {
        return shopList.find { it.id == id }?: throw Exception("Элемент не найден")
    }

    override fun editItem(item: ShopItem) {
        val itemId = item.id
        val oldItem = getItem(itemId)
        removeItem(oldItem)
        addItem(item)
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}