package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getItem(id:Int): LiveData<ShopItem> {
        return shopListRepository.getItem(id)
    }
}