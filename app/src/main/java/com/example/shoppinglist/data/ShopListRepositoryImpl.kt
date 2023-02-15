package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository{

    private val shopList = mutableListOf<ShopItem>()
    private val shopListLD = MutableLiveData<List<ShopItem>>()
    var autoIncrementId = 0

    init {
        addItem(ShopItem(1,"abc",true))
        addItem(ShopItem(2,"abcd",true))
        addItem(ShopItem(3,"abcde",true))
    }

    override fun addItem(item: ShopItem) {
        if (item.id == ShopItem.UNEXPECTED_ID){
            item.id = autoIncrementId++
        }
        shopList.add(item)
        updateShopList()
    }

    override fun removeItem(item: ShopItem) {
        shopList.remove(item)
        updateShopList()
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

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateShopList(){
        shopListLD.value = shopList.toList()
    }
}