package com.example.shoppinglist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addItem(item: ShopItem){
        shopListRepository.addItem(item)
    }
}